package de.kisner.otrcast.controller.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.exlp.util.xml.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.AbstractCutlistChooserController;
import de.kisner.otrcast.interfaces.controller.CutlistChooser;
import de.kisner.otrcast.interfaces.view.ViewCutlistChooser;
import de.kisner.otrcast.model.xml.cut.CutList;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.cut.VideoFiles;
import de.kisner.otrcast.model.xml.series.Movie;
import de.kisner.otrcast.model.xml.series.Video;
import de.kisner.otrcast.model.xml.series.Videos;

public class CliCutlistChooserController extends AbstractCutlistChooserController implements CutlistChooser
{
	final static Logger logger = LoggerFactory.getLogger(CliCutlistChooserController.class);
	
	private Pattern pSingle,pMulti;
	private VideoFiles vFiles;
	
	public CliCutlistChooserController(ViewCutlistChooser view)
	{
		super(view);
		pMulti = Pattern.compile("(\\d)\\+");
		pSingle = Pattern.compile("(\\d)");
	}
	
	@Override
	public Videos chooseCutlists(VideoFiles vFiles)
	{
		this.vFiles=vFiles;
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
			Matcher mSingle = pSingle.matcher(token);
			Matcher mMulti = pMulti.matcher(token);
			
			boolean bSingle = mSingle.matches();
			boolean bMulti = mMulti.matches();
			
			logger.debug("Token "+token+" single:"+bSingle+" multi:"+bMulti);
			
			if(!bSingle && !bMulti)
			{
				logger.warn("Unknown input");
			}
			
			if(bSingle){listVideos.add(buildSingle(vf,Integer.parseInt(mSingle.group(1))));}
			if(bMulti){listVideos.add(buildMulti(vf,Integer.parseInt(mMulti.group(1))));}
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
		vf.setOtrId(vfInput.getOtrId());
		
		VideoFiles vfs = new VideoFiles();
		vfs.getVideoFile().add(vf);
		
		Video video = new Video();
		video.setVideoFiles(vfs);
		
		Movie movie = new Movie();
		if(vf.getCutList().isSetFileName() && vf.getCutList().getFileName().isSetValue() && vf.getCutList().getFileName().getValue().length()>0)
		{
			movie.setName(vf.getCutList().getFileName().getValue());
		}
		else{movie.setName(vfInput.getOtrId().getTv().getName());}
		video.setMovie(movie);
		
		JaxbUtil.info(video);
		return video;
	}
	
	private Video buildMulti(VideoFile vfInput, int index)
	{
		Video video = buildSingle(vfInput, index);
		view.additionalFile(vFiles);
		Scanner sc = new Scanner(System.in);
		
		String[] tokens = sc.nextLine().split(",");
		
		int vfIndex = Integer.parseInt(tokens[0])-1;
		int clIndex = Integer.parseInt(tokens[1])-1;
		
		VideoFile vf2 = vFiles.getVideoFile().get(vfIndex);
		VideoFile vf = new VideoFile();
		vf.setCutList(vf2.getCutLists().getCutList().get(clIndex));
		vf.setOtrId(vf2.getOtrId());
		video.getVideoFiles().getVideoFile().add(vf);
		
		return video;
	}
}