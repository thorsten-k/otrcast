package net.sf.otrcutmp4.controller.batch.audio;

import java.io.File;

import net.sf.otrcutmp4.controller.batch.AbstactBatchGenerator;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.OtrConfig.Audio;
import net.sf.otrcutmp4.util.OtrConfig.Dir;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mp3ToAac extends AbstactBatchGenerator
{
	final static Logger logger = LoggerFactory.getLogger(Mp3ToAac.class);
	
	public Mp3ToAac(OtrConfig otrConfig)
	{
		super(otrConfig);
	}
	
	public String create()
	{
		String sMp3 = rpf.relativate(new File(cfg.getDir(Dir.TMP), "raw_audio.mp3"));
		String sAac = rpf.relativate(new File(cfg.getDir(Dir.TMP), "aac.aac"));
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(cmdLame);
		sb.append(" --decode ").append(sMp3);
		sb.append(" - | ");
		sb.append(cmdFaac+" --mpeg-vers 4 -b "+cfg.getAudio(Audio.FAAC));
		sb.append(" -o "+sAac+" -");
		
		return sb.toString();
	}
}
