package net.sf.otrcutmp4.controller.batch.audio;

import net.sf.otrcutmp4.controller.batch.AbstactBatchGenerator;
import net.sf.otrcutmp4.controller.batch.RenameGenerator;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.OtrConfig.Audio;
import net.sf.otrcutmp4.util.OtrConfig.Dir;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Mp3ToAac extends AbstactBatchGenerator
{
	static Log logger = LogFactory.getLog(RenameGenerator.class);
	
	public Mp3ToAac(OtrConfig otrConfig)
	{
		super(otrConfig);
	}
	
	public String create()
	{
		String sMp3 = getRalative(otrConfig.getDir(Dir.TMP), "raw_audio.mp3");
		String sAac = getRalative(otrConfig.getDir(Dir.TMP), "aac.aac");
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(cmdLame);
		sb.append(" --decode ").append(sMp3);
		sb.append(" - | ");
		sb.append(cmdFaac+" --mpeg-vers 4 -b "+otrConfig.getAudio(Audio.FAAC));
		sb.append(" -o "+sAac+" -");
		
		return sb.toString();
	}
}
