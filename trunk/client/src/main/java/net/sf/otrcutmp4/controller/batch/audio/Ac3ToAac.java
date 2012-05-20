package net.sf.otrcutmp4.controller.batch.audio;

import java.io.File;

import net.sf.otrcutmp4.AviToMp4;
import net.sf.otrcutmp4.controller.batch.AbstactBatchGenerator;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.OtrConfig.Dir;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ac3ToAac extends AbstactBatchGenerator
{	
	final static Logger logger = LoggerFactory.getLogger(Ac3ToAac.class);
	
	public Ac3ToAac(OtrConfig otrConfig,AviToMp4.Profile profile)
	{
		super(otrConfig,profile);
	}
	
	public String extract()
	{
		return null;
	}
	
	public String create(String vName)
	{
		String sAc3 = rpf.relativate(new File(cfg.getDir(Dir.AC3),vName+".ac3"));
		String sAac = rpf.relativate(new File(cfg.getDir(Dir.TMP),"aac.aac"));
		
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
