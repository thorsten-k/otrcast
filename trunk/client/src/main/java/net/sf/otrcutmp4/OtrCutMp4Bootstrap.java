package net.sf.otrcutmp4;

import net.sf.exlp.util.io.ConfigLoader;
import net.sf.exlp.util.io.LoggerInit;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OtrCutMp4Bootstrap
{
	static Log logger = LogFactory.getLog(OtrCutMp4Bootstrap.class);
	
	public static Configuration init()
	{
		String configFile = "src/main/resources/otrcutmp4-client/tg.xml";
		return init(configFile);
	}
	
	public static Configuration init(String configFile)
	{
		initLogger();
		Configuration config = 	initConfig(configFile);
							
		logger.debug("Config and Logger initialized");
		return config;
	}
	
	public static Configuration initConfig(String configFile)
	{
		ConfigLoader.add(configFile);
		return ConfigLoader.init();
	}
	
	public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
		loggerInit.addAltPath("otrcutmp4-client");
		loggerInit.init();
	}
}