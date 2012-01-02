package net.sf.otrcutmp4.controller.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.sf.exlp.event.handler.EhResultContainer;
import net.sf.exlp.event.impl.JaxbEvent;
import net.sf.exlp.listener.LogListener;
import net.sf.exlp.listener.impl.LogListenerHttp;
import net.sf.exlp.parser.LogParser;
import net.sf.otrcutmp4.controller.AbstractCutlistChooserController;
import net.sf.otrcutmp4.controller.processor.exlp.parser.CutlistParser;
import net.sf.otrcutmp4.interfaces.controller.CutlistChooser;
import net.sf.otrcutmp4.interfaces.view.ViewCutlistChooser;
import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.cut.CutListsAvailable;
import net.sf.otrcutmp4.model.xml.cut.CutListsSelected;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliCutlistChooserController extends AbstractCutlistChooserController implements CutlistChooser
{
	final static Logger logger = LoggerFactory.getLogger(CliCutlistChooserController.class);
	
	public CliCutlistChooserController(ViewCutlistChooser view)
	{
		super(view);
	}
	
	@Override
	public VideoFiles chooseCutlists(VideoFiles vFiles)
	{
		view.welcome(vFiles);
		for(int i=0;i<vFiles.getVideoFile().size();i++)
		{
			VideoFile vf = vFiles.getVideoFile().get(i);
			if(vf.isSetCutListsAvailable() && vf.getCutListsAvailable().isSetCutList())
			{
				chooseCutlist(i,vf,true);
			}
			
		}
		loadCurlists(vFiles);
		
		return vFiles;
	}
	
	private void chooseCutlist(int index, VideoFile vf,boolean loadCutlist)
	{
		view.showFileInfo(index,vf);
		for(int i=0;i<vf.getCutListsAvailable().getCutList().size();i++)
		{
			CutList cl = vf.getCutListsAvailable().getCutList().get(i);
			view.showCutlistInfo(i, cl,true,true,true,true);
		}
		
		vf.setCutListsSelected(select(vf.getCutListsAvailable(), loadCutlist));
	}
	
	@Override
	public CutListsSelected select(CutListsAvailable clAvailable, boolean loadCutlist)
	{
		CutListsSelected clSelected = new CutListsSelected();
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		String[] tokens = line.split( "," );
		
		if(line.length()>0)
		{
			List<Integer> selectedIndexes = new ArrayList<Integer>();
			for(String token : tokens)
			{
				selectedIndexes.add(Integer.parseInt(token));
			}	
			
			if(loadCutlist)
			{
				for(Integer id : selectedIndexes)
				{
					CutList cl = new CutList();
					cl.setId(clAvailable.getCutList().get(id).getId());
					clSelected.getCutList().add(cl);
				}
			}
			else
			{
				if(selectedIndexes.size()==1)
				{
					clSelected.getCutList().add(clAvailable.getCutList().get(selectedIndexes.get(0)));
				}
				else
				{
					logger.warn("You have to chosse only one cutlist");
				}
			}
		}
		return clSelected;
	}

	@Override
	public void loadCurlists(VideoFiles vFiles)
	{
		for(VideoFile vf : vFiles.getVideoFile())
		{
			if(vf.isSetCutListsSelected())
			{
				vf.setCutListsSelected(loadCurlists(vf.getCutListsSelected()));
			}
		}
	}
	
	private CutListsSelected loadCurlists(CutListsSelected clSelected)
	{
		CutListsSelected clLoaded = new CutListsSelected();
		for(CutList cl : clSelected.getCutList())
		{		
			clLoaded.getCutList().add(loadCutlist(cl.getId()));
		}
		return clLoaded;
	}
	
	private CutList loadCutlist(String id)
	{
		String http = "http://cutlist.at/getfile.php?id="+id;
		
		logger.info("Trying to download cutlists");
		logger.debug("\t"+http);
	
		EhResultContainer leh = new EhResultContainer();
		LogParser lp = new CutlistParser(leh);
		
		LogListener ll = new LogListenerHttp(lp);
		ll.processSingle(http);
		
		JaxbEvent event = (JaxbEvent)leh.getSingleResult();
		return (CutList)event.getObject();
	}
}