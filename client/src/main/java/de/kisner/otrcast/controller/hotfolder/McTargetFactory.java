package de.kisner.otrcast.controller.hotfolder;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.util.OtrConfig;

public class McTargetFactory
{	
	final static Logger logger = LoggerFactory.getLogger(McTargetFactory.class);
	
	private OtrConfig config;
	
	public McTargetFactory(OtrConfig config)
	{
		this.config=config;
		logger.info("Target ..."+this.config.toString());
	}
	
	public File getTarget()
	{
		
		File f = new File(".");
		logger.info("Free "+f.getFreeSpace());
		
		return f;
	}
}
