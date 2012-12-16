package net.sf.otrcutmp4.controller.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.AbstractCutlistChooserController;
import net.sf.otrcutmp4.interfaces.controller.CutlistChooser;
import net.sf.otrcutmp4.interfaces.view.ViewCutlistChooser;
import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.cut.CutListsAvailable;
import net.sf.otrcutmp4.model.xml.cut.CutListsSelected;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.model.xml.series.Video;
import net.sf.otrcutmp4.model.xml.series.Videos;
import net.sf.otrcutmp4.web.WebCutListLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliCutlistChooserController extends AbstractCutlistChooserController implements CutlistChooser
{
	final static Logger logger = LoggerFactory.getLogger(CliCutlistChooserController.class);
	
	private WebCutListLoader wcll;
	
	public CliCutlistChooserController(ViewCutlistChooser view)
	{
		super(view);
		wcll = new WebCutListLoader();
	}
	
	@Override
	public Videos chooseCutlists(VideoFiles vFiles)
	{
		view.welcome(vFiles);
		Videos videos = new Videos();
		
		for(int i=0;i<vFiles.getVideoFile().size();i++)
		{
			VideoFile vf = vFiles.getVideoFile().get(i);
			if(vf.isSetCutListsAvailable() && vf.getCutListsAvailable().isSetCutList())
			{
				Video video = chooseCutlist(i,vf,true);
				if(video!=null){videos.getVideo().add(video);}
			}
		}		
		
		return videos;
	}
	
	private Video chooseCutlist(int index, VideoFile vf,boolean loadCutlist)
	{
		view.showFileInfo(index,vf);
		for(int i=0;i<vf.getCutListsAvailable().getCutList().size();i++)
		{
			CutList cl = vf.getCutListsAvailable().getCutList().get(i);
			view.showCutlistInfo(i, cl,true,true,true,true);
		}
		
		vf.setCutListsSelected(select(vf.getCutListsAvailable(), loadCutlist));
		vf.setCutListsAvailable(null);
		Video video = new Video();
		video.setVideoFiles(new VideoFiles());
		video.getVideoFiles().getVideoFile().add(vf);
		
		return video;
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
					clSelected.getCutList().add(clAvailable.getCutList().get(id));
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
	public void loadCutlists(Videos videos)
	{
		for(Video video : videos.getVideo())
		{
			JaxbUtil.info(video);
		}
	}
	
	@Override
	public void loadCurlists(VideoFiles vFiles)
	{
		for(VideoFile vf : vFiles.getVideoFile())
		{
			if(vf.isSetCutListsSelected())
			{
				loadCurlists(vf.getCutListsSelected());
			}
		}
	}
	
	private void loadCurlists(CutListsSelected clSelected)
	{
		for(CutList cl : clSelected.getCutList())
		{		
			CutList loaded = wcll.loadCutlist(cl.getId());
			cl.getCut().addAll(loaded.getCut());
		}
	}
}