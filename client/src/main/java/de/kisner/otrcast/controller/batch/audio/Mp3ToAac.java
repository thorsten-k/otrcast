package de.kisner.otrcast.controller.batch.audio;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.app.AviToMp4;
import de.kisner.otrcast.controller.batch.AbstactBatchGenerator;
import de.kisner.otrcast.controller.exception.OtrInternalErrorException;
import de.kisner.otrcast.factory.txt.TxtFileNameFactoy;
import de.kisner.otrcast.factory.xml.otr.XmlOtrIdFactory;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.series.Video;
import de.kisner.otrcast.util.OtrConfig;
import de.kisner.otrcast.util.OtrConfig.Audio;
import de.kisner.otrcast.util.OtrConfig.Dir;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;

public class Mp3ToAac extends AbstactBatchGenerator
{
	final static Logger logger = LoggerFactory.getLogger(Mp3ToAac.class);
	
	public Mp3ToAac(OtrConfig otrConfig,AviToMp4.Profile profile)
	{
		super(otrConfig,profile);
	}
	
	public List<String> extract(Video video) throws UtilsProcessingException, OtrInternalErrorException
	{
		List<String> result = new ArrayList<String>();
		int index=1;
		for(VideoFile vf : video.getVideoFiles().getVideoFile())
		{
			result.add(extract(index,vf));
			index++;
		}
		return result;
	}
	
	private String extract(int index, VideoFile vf) throws UtilsProcessingException, OtrInternalErrorException
	{
		String inAvi = rpf.relativate(new File(cfg.getDir(Dir.AVI),TxtFileNameFactoy.build(vf.getOtrId())));
		String outMp3 = rpf.relativate(new File(cfg.getDir(Dir.TMP), "raw-"+index+".mp3"));
		
		StringBuffer sb = new StringBuffer();
		sb.append(cmdMp4Box).append(" ");
		
		switch(XmlOtrIdFactory.getType(vf.getOtrId().getFormat().getType()))
		{
			case hd: sb.append("-fps 50 ");break;
			case hq: logger.trace("No FPS Handling required here");break;
			default: throw new OtrInternalErrorException("No Default Handling available");
		}
		
		sb.append("-aviraw audio ").append(inAvi).append(" -out "+outMp3);
		
		return sb.toString();
	}
	
	public List<String> transcode(Video video) throws UtilsProcessingException, OtrInternalErrorException
	{
		List<String> result = new ArrayList<String>();
		int index=1;
		for(VideoFile vf : video.getVideoFiles().getVideoFile())
		{
			result.add(transcode(index,vf));
			index++;
		}
		return result;
	}
	
	private String transcode(int index, VideoFile vf)
	{
		String sMp3 = rpf.relativate(new File(cfg.getDir(Dir.TMP), "raw-"+index+"_audio.mp3"));
		String sAac = rpf.relativate(new File(cfg.getDir(Dir.TMP), "aac-"+index+".aac"));
		
		StringBuffer sb = new StringBuffer();
		sb.append(cmdLame);
		sb.append(" --decode ").append(sMp3);
		sb.append(" - | ");
		sb.append(cmdFaac+" --mpeg-vers 4 -b "+cfg.getAudio(Audio.FAAC));
		sb.append(" -o "+sAac+" -");
		
		return sb.toString();
	}
}
