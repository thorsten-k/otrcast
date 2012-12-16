package net.sf.otrcutmp4.controller.batch.video;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.otrcutmp4.AviToMp4;
import net.sf.otrcutmp4.controller.batch.AbstactBatchGenerator;
import net.sf.otrcutmp4.controller.exception.OtrInternalErrorException;
import net.sf.otrcutmp4.model.xml.cut.Cut;
import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.series.Video;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.OtrConfig.Dir;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VideoCutter extends AbstactBatchGenerator
{	
	final static Logger logger = LoggerFactory.getLogger(VideoCutter.class);
	
	private static DecimalFormat df;
	
	public VideoCutter(OtrConfig cfg, AviToMp4.Profile profile)
	{
		super(cfg, profile);
	}
	
	public List<String> cut(Video video) throws OtrInternalErrorException, UtilsProcessingException
	{
		List<String> result = new ArrayList<String>();
		
		int indexVf=1;
		for(VideoFile vf : video.getVideoFiles().getVideoFile())
		{
			String inVideo=null;
			switch(profile)
			{
				case P0: inVideo = rpf.relativate(new File(cfg.getDir(Dir.TMP),"mp4-"+indexVf+".mp4"));break;
//				case P1: inVideo = rpf.relativate(new File(cfg.getDir(Dir.AVI),vf.getFileName().getValue()));break;
			}
			result.addAll(applyCutList(indexVf,vf.getCutList(),inVideo,profile));
			
//			result.add(transcode(index,vf));
			indexVf++;
		}
		
		return result;
	}
	
	private List<String> applyCutList(int indexVf,CutList cutList, String inVideo, AviToMp4.Profile profile)
	{
		List<String> result = new ArrayList<String>();
		result.addAll(cutList(indexVf,cutList, inVideo, profile));
		return result;
	}
	
	private List<String> cutList(int indexVf, CutList cutList, String inVideo,AviToMp4.Profile profile)
	{
		List<String> result = new ArrayList<String>();
		int counter = 1;
		for(Cut cut : cutList.getCut())
		{
			if(cut.isSetInclude())
			{
				switch(profile)
				{
					case P0:	result.add(getP0cut(indexVf, counter, cut, inVideo));break;
					case P1:	result.add(getP1cut(indexVf, counter, cut, inVideo));break;
				}
				
				counter++;
			}
		}
		return result;
	}
	
	private String getP0cut(int indexVf, int counter, Cut cut, String inVideo)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(cmdFfmpeg);
		sb.append(" -ss ").append(getSecond(cut.getStart()));
		sb.append(" -t ").append(getSecond(cut.getDuration()));
		sb.append(" -i "+inVideo);
		sb.append(" -vcodec copy");
		sb.append(" -acodec copy");
		sb.append(" ").append(rpf.relativate(new File(cfg.getDir(Dir.TMP), indexVf+"-"+counter+".mp4")));
		return sb.toString();
	}
	
	private String getP1cut(int indexVf, int counter, Cut cut, String inVideo)
	{		
		StringBuffer sb = new StringBuffer();
		sb.append(cmdFfmpeg);
		sb.append(" -ss ").append(getSecond(cut.getStart()));
		sb.append(" -t ").append(getSecond(cut.getDuration()));
		sb.append(" -fflags genpts -y");
		sb.append(" -i "+inVideo);
		sb.append(" -vcodec copy");
		sb.append(" -acodec libvo_aacenc");
		sb.append(" ").append(rpf.relativate(new File(cfg.getDir(Dir.TMP), indexVf+"."+counter+".mp4")));
		return sb.toString();
	}
	
	public static synchronized String getSecond(double d)
	{
		if(df==null)
		{
			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator('.');
			df = new DecimalFormat("0.00",dfs);
		}
		return df.format(d);
	}
}
