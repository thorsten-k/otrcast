package net.sf.otrcutmp4.controller.batch.audio;

import java.io.File;

import net.sf.otrcutmp4.controller.batch.AbstactBatchGenerator;
import net.sf.otrcutmp4.controller.batch.RenameGenerator;
import net.sf.otrcutmp4.util.OtrConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Mp3ToAac extends AbstactBatchGenerator
{
	static Log logger = LogFactory.getLog(RenameGenerator.class);
	
	private String cmdLame, cmdFaac;
	
	public Mp3ToAac(OtrConfig otrConfig, File dirTools)
	{
		super(otrConfig);
		
		cmdLame = rpf.relativate(new File(dirTools,config.getString(OtrConfig.toolLame)));
		cmdFaac = rpf.relativate(new File(dirTools,config.getString(OtrConfig.toolFaac)));
	}
	
	public String create()
	{
		
		String sMp3 = rpf.relativate(new File(fTmp,"raw_audio.mp3"));
		String sAac = rpf.relativate(new File(fTmp,"aac.aac"));
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(cmdLame);
		sb.append(" --decode ").append(sMp3);
		sb.append(" - | ");
		sb.append(cmdFaac+" --mpeg-vers 4 -b 192 ");
		sb.append("-o "+sAac+" -");
		
		return sb.toString();
	}
}
