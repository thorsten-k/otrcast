package net.sf.otrcutmp4.controller.batch;

import java.io.File;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.shell.cmd.ShellCmdRm;
import net.sf.exlp.util.io.txt.ExlpTxtWriter;
import net.sf.otrcutmp4.AviToMp4;
import net.sf.otrcutmp4.controller.batch.audio.Ac3ToAac;
import net.sf.otrcutmp4.controller.batch.audio.Mp3ToAac;
import net.sf.otrcutmp4.controller.batch.video.AviExtract;
import net.sf.otrcutmp4.controller.batch.video.H264Transcode;
import net.sf.otrcutmp4.controller.batch.video.Mp4Merger;
import net.sf.otrcutmp4.controller.batch.video.TagGenerator;
import net.sf.otrcutmp4.controller.batch.video.VideoCutter;
import net.sf.otrcutmp4.controller.exception.OtrInternalErrorException;
import net.sf.otrcutmp4.model.xml.series.Video;
import net.sf.otrcutmp4.model.xml.series.Videos;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.OtrConfig.Dir;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BatchGenerator extends AbstactBatchGenerator
{
	final static Logger logger = LoggerFactory.getLogger(BatchGenerator.class);
	
	private AviExtract aviExtract;
	private VideoCutter videoCutter;
	
	private Mp3ToAac mp3ToAac;
	private Ac3ToAac ac3ToAac;
	private H264Transcode h264Transcode;
	private Mp4Merger mp4Merger;
	private TagGenerator tagGenerator;
	
	private ExlpTxtWriter txt;
	
	public BatchGenerator(OtrConfig cfg,AviToMp4.Profile profile,boolean tagMp4) throws OtrInternalErrorException
	{
		super(cfg,profile);
		
		mp3ToAac = new Mp3ToAac(cfg,profile);
		ac3ToAac = new Ac3ToAac(cfg,profile);
		h264Transcode = new H264Transcode(cfg,profile);
		aviExtract = new AviExtract(cfg,profile);
		videoCutter = new VideoCutter(cfg,profile);
		mp4Merger = new Mp4Merger(cfg,profile);
		tagGenerator = new TagGenerator(cfg,profile,tagMp4);
		
		logger.debug("");
		logger.debug("Creating Batch in "+cfg.getDir(Dir.BAT).getAbsolutePath());
		
		txt = new ExlpTxtWriter();
	}
	
	public void build(Videos videos) throws OtrInternalErrorException
	{
		for(Video video : videos.getVideo())
		{
			try {build(video);}
			catch (UtilsProcessingException e) {e.printStackTrace();}
		}
		
		txt.debug();
		File f = new File(cfg.getDir(Dir.BAT),"cut.bat");
		txt.writeFile(f);
		logger.info("");
		logger.info("Batch file written to: "+rpf.relativate(new File("."), f));
	}
	
	private void build(Video video) throws OtrInternalErrorException, UtilsProcessingException
	{
		if(!video.isSetVideoFiles() || !video.getVideoFiles().isSetVideoFile())
		{
			 txt.add("echo No Cutlist available for video: "+video);
			 txt.add("");
		}
		crateForVideo(video,profile);
	}
	
	private void crateForVideo(Video video, AviToMp4.Profile profile) throws OtrInternalErrorException, UtilsProcessingException
	{
		txt.add("echo Processing: "+video);
		txt.add("");
		
		try {txt.add(ShellCmdRm.rmDir(rpf.relativate(cfg.getDir(Dir.TMP)), true));}
		catch (ExlpUnsupportedOsException e) {logger.error("",e);}
	
		extract(video);
		transcode(video);
		txt.add(videoCutter.cut(video));
		txt.add(mp4Merger.merge(video));
		txt.add(tagGenerator.mvToMp4(video));
		
		txt.add("");
		txt.add("");
	}
	
	private void extract(Video video) throws OtrInternalErrorException, UtilsProcessingException
	{
		switch(profile)
		{
			case P0: txt.add(aviExtract.rawExtract(video));break;
			default: logger.warn("Profile "+profile+" NYI");
		}	
		
		logger.warn("No AC3 processing available!");
/*		if(vf.getOtrId().getFormat().isAc3())
		{
			txt.add(ac3ToAac.extract(vf));
		}
		else
*/		{
			txt.add(mp3ToAac.extract(video));
		}
	}
	
	private void transcode(Video video) throws UtilsProcessingException, OtrInternalErrorException
	{
		logger.warn("No AC3 handling");
		txt.add(mp3ToAac.transcode(video));
		txt.add(h264Transcode.transcode(video));
	}	
}