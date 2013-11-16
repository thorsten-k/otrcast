package net.sf.otrcutmp4.controller.hotfolder;

import java.io.File;

import net.sf.otrcutmp4.util.OtrConfig;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class McTargetFactory
{	
	final static Logger logger = LoggerFactory.getLogger(McTargetFactory.class);
	
	private OtrConfig config;
	
	public McTargetFactory(OtrConfig config)
	{
		this.config=config;
	}
	
	public File getTarget()
	{
		
		File f = new File(".");
		logger.info("Free "+f.getFreeSpace());
		
		return f;
	}
}
