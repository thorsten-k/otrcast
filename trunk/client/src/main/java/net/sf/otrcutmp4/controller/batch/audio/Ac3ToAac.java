package net.sf.otrcutmp4.controller.batch.audio;

import java.io.File;

import net.sf.otrcutmp4.controller.batch.AbstactBatchGenerator;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.OtrConfig.Dir;

public class Ac3ToAac extends AbstactBatchGenerator
{	
	public Ac3ToAac(OtrConfig otrConfig)
	{
		super(otrConfig);
	}
	
	public String create(String vName)
	{
		String sAc3 = rpf.relativate(new File(otrConfig.getDir(Dir.AC3),vName+".ac3"));
		String sAac = rpf.relativate(new File(otrConfig.getDir(Dir.TMP),"aac.aac"));
		
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
