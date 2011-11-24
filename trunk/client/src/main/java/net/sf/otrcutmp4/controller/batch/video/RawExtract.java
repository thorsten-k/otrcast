package net.sf.otrcutmp4.controller.batch.video;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.otrcutmp4.AviToMp4;
import net.sf.otrcutmp4.controller.batch.AbstactBatchGenerator;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.OtrConfig.Dir;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RawExtract extends AbstactBatchGenerator
{	
	static Log logger = LogFactory.getLog(RawExtract.class);
	
	public RawExtract(OtrConfig otrConfig)
	{
		super(otrConfig);
	}
	
	public List<String> rawExtract(VideoFile vf,AviToMp4.Quality quality, AviToMp4.Audio audio)
	{
		List<String> result = new ArrayList<String>();
		
		String sIn = rpf.relativate(new File(dirAvi,vf.getFileName().getValue()));
		String sH264 = rpf.relativate(new File(otrConfig.getDir(Dir.TMP), "raw.h264"));
		String sMp3 = rpf.relativate(new File(otrConfig.getDir(Dir.TMP), "raw.mp3"));
		
		logger.debug(sH264);
		logger.debug(sMp3);
		
		StringBuffer sbVideo = new StringBuffer();
		StringBuffer sbAudio = new StringBuffer();
		
		sbVideo.append(cmdMp4Box).append(" ");
		sbAudio.append(cmdMp4Box).append(" ");
		
		switch(quality)
		{
			case HD: sbVideo.append("-fps 50 ");sbAudio.append("-fps 50 ");break;
		}
		
		sbVideo.append("-aviraw video "+sIn+" -out "+sH264);
		sbAudio.append("-aviraw audio ").append(sIn).append(" -out "+sMp3);
			
		result.add(sbVideo.toString());
		
		switch(audio)
		{
			case Mp3: result.add(sbAudio.toString());;break;
		}

		return result;
	}
}
