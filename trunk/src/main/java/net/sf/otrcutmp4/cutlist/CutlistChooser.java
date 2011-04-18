package net.sf.otrcutmp4.cutlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.sf.exlp.event.handler.EhResultContainer;
import net.sf.exlp.event.impl.JaxbEvent;
import net.sf.exlp.listener.LogListener;
import net.sf.exlp.listener.impl.LogListenerHttp;
import net.sf.exlp.parser.LogParser;
import net.sf.otrcutmp4.data.jaxb.CutList;
import net.sf.otrcutmp4.data.jaxb.CutListsAvailable;
import net.sf.otrcutmp4.data.jaxb.CutListsSelected;
import net.sf.otrcutmp4.data.jaxb.VideoFile;
import net.sf.otrcutmp4.data.jaxb.VideoFiles;
import net.sf.otrcutmp4.exlp.parser.CutlistParser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class CutlistChooser
{
	static Log logger = LogFactory.getLog(CutlistChooser.class);
		
	public CutlistChooser()
	{

	}

	public VideoFiles chooseCutlists(VideoFiles vFiles)
	{
		logger.info("");
		logger.info("Choose cutlist for "+vFiles.getVideoFile().size()+" files");
		logger.info("\tYou can select a single cutlist e.g. with '1'");
		logger.info("\tMultiple selections (e.g. '1,3') result in multiple output files");
		for(VideoFile vf : vFiles.getVideoFile())
		{
			if(vf.isSetCutListsAvailable() && vf.getCutListsAvailable().isSetCutList())
			{
				chooseCutlist(vf);
			}
		}
		return vFiles;
	}
	
	private void chooseCutlist(VideoFile vf)
	{
		logger.info("");
		logger.info(vf.getFileId().getValue());
		for(int i=0;i<vf.getCutListsAvailable().getCutList().size();i++)
		{
			CutList cl = vf.getCutListsAvailable().getCutList().get(i);
			askCutlist(i, vf, cl);
		}
		
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		String[] tokens = line.split( "," );
		
		List<Integer> selectedIndexes = new ArrayList<Integer>();
		for(String token : tokens)
		{
			selectedIndexes.add(Integer.parseInt(token));
		}	
		
		vf.setCutListsSelected(loadCutlists(selectedIndexes, vf.getCutListsAvailable()));
	}
	
	private void askCutlist(int i, VideoFile vf, CutList cl)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(i).append("\t");
		sb.append("Author: ").append(cl.getAuthor().getValue());
		
		sb.append(", Rating: ");
		if(cl.isSetRating()){sb.append(cl.getRating());}
		else {sb.append("n/a");}
		
		sb.append(" (rated ").append(cl.getRatingCount()).append(" times)");
		logger.info(sb.toString());
		if(cl.isSetComment()){logger.info("\tComment: "+cl.getComment().getValue());}
		if(cl.isSetFileName()){logger.info("\tFile: "+cl.getFileName().getValue());}
	}
	
	private CutListsSelected loadCutlists(List<Integer> selected, CutListsAvailable clAvailable)
	{
		CutListsSelected clSelected = new CutListsSelected();
		
		for(int i : selected)
		{		
			clSelected.getCutList().add(loadCutlist(i, clAvailable.getCutList().get(i)));
		}
		
		return clSelected;
	}
	
	private CutList loadCutlist(int i, CutList clChosen)
	{
		String http = "http://cutlist.at/getfile.php?id="+clChosen.getId();
		
		logger.info("Trying to download selection "+i);
		logger.debug("\t"+http);
		
//		EhResultContainer leh = new EhResultContainer();
//		LogParser lp = new XmlParser(leh);
	
		EhResultContainer leh = new EhResultContainer();
		LogParser lp = new CutlistParser(leh);
		
		LogListener ll = new LogListenerHttp(lp);
		ll.processSingle(http);
		
		JaxbEvent event = (JaxbEvent)leh.getSingleResult();
		CutList cl = (CutList)event.getObject();
		
		return cl;
	}
}