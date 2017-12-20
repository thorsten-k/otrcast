package de.kisner.otrcast.controller.batch.audio;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.batch.AbstactBatchGenerator;
import de.kisner.otrcast.factory.xml.otr.XmlOtrIdFactory;
import de.kisner.otrcast.interfaces.OtrCastInterface;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.util.OtrConfig;
import de.kisner.otrcast.util.OtrConfig.Dir;

public class Ac3ToAac extends AbstactBatchGenerator
{	
	final static Logger logger = LoggerFactory.getLogger(Ac3ToAac.class);
	
	public Ac3ToAac(OtrConfig otrConfig, OtrCastInterface.Profile profile)
	{
		super(otrConfig,profile);
	}
	
	public String extract(VideoFile vf)
	{
		String sAc3 = rpf.relativate(new File(cfg.getDir(Dir.AVI),vf.getOtrId().getKey()+"."+XmlOtrIdFactory.typeAc3Hd));
		String sAac = rpf.relativate(new File(cfg.getDir(Dir.TMP), "aac.m4a"));
		
		StringBuffer sb = new StringBuffer();
		sb.append(cmdEac3to);
		sb.append(" ").append(sAc3);
		sb.append(" 1: stdout.wav");
		sb.append(" | ").append(cmdNero);
		sb.append(" -q 0.35");
		sb.append(" -ignorelength");
		sb.append(" -if -");
		sb.append(" -of ").append(sAac);
		return sb.toString();
	}
}
