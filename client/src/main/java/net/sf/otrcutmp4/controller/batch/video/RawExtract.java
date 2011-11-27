package net.sf.otrcutmp4.controller.batch.video;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.otrcutmp4.AviToMp4;
import net.sf.otrcutmp4.controller.batch.AbstactBatchGenerator;
import net.sf.otrcutmp4.controller.exception.OtrInternalErrorException;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.OtrConfig.Dir;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RawExtract extends AbstactBatchGenerator
{	
	final static Logger logger = LoggerFactory.getLogger(RawExtract.class);
	
	public RawExtract(OtrConfig cfg)
	{
		super(cfg);
	}
	
	public List<String> rawExtract(VideoFile vf,AviToMp4.Quality quality, AviToMp4.Audio audio) throws OtrInternalErrorException
	{
		List<String> result = new ArrayList<String>();
		
		String inAvi = rpf.relativate(new File(getAviDir(quality),vf.getFileName().getValue()));		
		String outH264 = rpf.relativate(new File(cfg.getDir(Dir.TMP), "raw.h264"));
		String outMp3 = rpf.relativate(new File(cfg.getDir(Dir.TMP), "raw.mp3"));
		
		StringBuffer sbVideo = new StringBuffer();
		StringBuffer sbAudio = new StringBuffer();
		
		sbVideo.append(cmdMp4Box).append(" ");
		sbAudio.append(cmdMp4Box).append(" ");
		
		switch(quality)
		{
			case HD: sbVideo.append("-fps 50 ");sbAudio.append("-fps 50 ");break;
			case HQ: logger.trace("No FPS Handling required here");break;
			default: throw new OtrInternalErrorException("No Default Handling available");
		}
		
		sbVideo.append("-aviraw video "+inAvi+" -out "+outH264);
		sbAudio.append("-aviraw audio ").append(inAvi).append(" -out "+outMp3);
			
		result.add(sbVideo.toString());
		
		switch(audio)
		{
			case Mp3: result.add(sbAudio.toString());break;
			case Ac3: logger.trace("No Handling required here for AC3");break;
			default: throw new OtrInternalErrorException("No Default Handling available");
		}

		return result;
	}
}
