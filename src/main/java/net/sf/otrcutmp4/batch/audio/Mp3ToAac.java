package net.sf.otrcutmp4.batch.audio;

import java.io.File;

import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.otrcutmp4.util.OtrConfig;

import org.apache.commons.configuration.Configuration;

public class Mp3ToAac
{
	private RelativePathFactory rpf;
	private File dirBat,dirTmp;
	
	private String cmdLame, cmdFaac;
	
	public Mp3ToAac(Configuration config, File dirTools, File dirBat, File dirTmp)
	{
		this.dirBat=dirBat;
		this.dirTmp=dirTmp;
		rpf = new RelativePathFactory();
		
		cmdLame = rpf.relativate(dirBat, new File(dirTools,config.getString(OtrConfig.toolLame)));
		cmdFaac = rpf.relativate(dirBat, new File(dirTools,config.getString(OtrConfig.toolFaac)));
	}
	
	public String convert()
	{
		String sMp3 = rpf.relativate(dirBat, new File(dirTmp,"raw_audio.mp3"));
		String sAac = rpf.relativate(dirBat, new File(dirTmp,"aac.aac"));
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(cmdLame);
		sb.append(" --decode ").append(sMp3);
		sb.append(" - | ");
		sb.append(cmdFaac+" --mpeg-vers 4 -b 192");
		sb.append("-o "+sAac+" -");
		
		return sb.toString();
	}
}
