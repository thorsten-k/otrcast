package net.sf.otrcutmp4.controller.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.AbstractCutlistChooserController;
import net.sf.otrcutmp4.interfaces.controller.CutlistChooser;
import net.sf.otrcutmp4.interfaces.view.ViewCutlistChooser;
import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.model.xml.series.Video;
import net.sf.otrcutmp4.model.xml.series.Videos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliCutlistChooserController extends AbstractCutlistChooserController implements CutlistChooser
{
	final static Logger logger = LoggerFactory.getLogger(CliCutlistChooserController.class);
	
	private static enum Type {single}
	
	private Pattern pSingle;
	
	public CliCutlistChooserController(ViewCutlistChooser view)
	{
		super(view);
		pSingle = Pattern.compile("(\\d)");
	}
	
	@Override
	public Videos chooseCutlists(VideoFiles vFiles)
	{
		view.welcome(vFiles);
		Videos videos = new Videos();
		
		for(int i=0;i<vFiles.getVideoFile().size();i++)
		{
			VideoFile vf = vFiles.getVideoFile().get(i);
			if(vf.isSetCutLists() && vf.getCutLists().isSetCutList())
			{
				List<Video> list = chooseCutlist(i,vf);
				videos.getVideo().addAll(list);
			}
		}		
		
		return videos;
	}
	
	private List<Video> chooseCutlist(int index, VideoFile vf)
	{
		view.showFileInfo(index,vf);
		for(int i=0;i<vf.getCutLists().getCutList().size();i++)
		{	// This shows infos
			CutList cl = vf.getCutLists().getCutList().get(i);
			view.showCutlistInfo(i, cl,true,true,true,true);
		}
		return select(vf);
	}
	
	@Override
	public List<Video> select(VideoFile vf)
	{
		List<Video> listVideos = new ArrayList<Video>();
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		if(line.length()==0){return listVideos;}
		
		for(String token : line.split(","))
		{
			logger.debug("Token "+token);
			
			Matcher mSingle=pSingle.matcher(token);
			
			if(!mSingle.matches())
			{
				logger.warn("Unknown input");
			}
			
			if(mSingle.matches()){listVideos.add(buildSingle(vf,Integer.parseInt(mSingle.group(1))));}
		}
/*		if(line.length()>0)
		{
			List<Integer> selectedIndexes = new ArrayList<Integer>();
			for(String token : tokens)
			{
				selectedIndexes.add(Integer.parseInt(token));
			}	
			
			for(Integer id : selectedIndexes)
			{
				clSelected.getCutList().add(vf.getCutLists().getCutList().get(id));
			}
		}
*/		return listVideos;
	}
	
	private Video buildSingle(VideoFile vfInput, int index)
	{
		VideoFile vf = new VideoFile();
		vf.setCutList(vfInput.getCutLists().getCutList().get(index));
		
		VideoFiles vfs = new VideoFiles();
		vfs.getVideoFile().add(vf);
		
		Video video = new Video();
		video.setVideoFiles(vfs);
		
		JaxbUtil.info(video);
		return video;
	}
}