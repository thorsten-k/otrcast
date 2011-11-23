package net.sf.otrcutmp4.controller.batch.audio;

import java.io.File;

import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.otrcutmp4.util.OtrConfig;

import org.apache.commons.configuration.Configuration;

public class Ac3ToAac
{
	private RelativePathFactory rpf;
	private File dirBat,dirTmp,dirTools,dirAc3;
	
	private String cmdFfmpeg;
	
	public Ac3ToAac(Configuration config)
	{
		dirTmp = new File(config.getString(OtrConfig.dirTmp));
		dirTools = new File(config.getString(OtrConfig.dirTools));
		dirAc3 = new File(config.getString(OtrConfig.dirHdAc3));
		dirBat = new File(config.getString(OtrConfig.dirBat));
		
		rpf = new RelativePathFactory(true,false);
		
		cmdFfmpeg = rpf.relativate(dirBat, new File(dirTools,config.getString(OtrConfig.toolFfmpeg)));
	}
	
	public String convert(String vName)
	{
		String sAc3 = rpf.relativate(dirBat, new File(dirAc3,vName+".ac3"));
		String sAac = rpf.relativate(dirBat, new File(dirTmp,"aac.aac"));
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(cmdFfmpeg);
		sb.append(" -i ").append(sAc3);
		sb.append(" -vn -r 30000/1001");
		sb.append(" -acodec aac -strict experimental");
		sb.append(" -ac 6 -ar 48000 -ab 448k");
		sb.append(" ").append(sAac);
		
		return sb.toString();
	}
}
