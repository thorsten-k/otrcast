package de.kisner.otrcast.app;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.LoggerInit;

public class Bootstrap
{
	final static Logger logger = LoggerFactory.getLogger(Bootstrap.class);
	public final static String xmlConfig = "jetty/config/jetty.xml";
	
	public static Configuration init()
	{
		return init(xmlConfig);
	}
	
	public static Configuration init(String configFile)
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.addAltPath("otrcast/dav/config");
			loggerInit.init();
			
		return null;
	}
}