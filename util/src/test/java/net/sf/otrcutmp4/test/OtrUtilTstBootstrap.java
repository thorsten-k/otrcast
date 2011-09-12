package net.sf.otrcutmp4.test;

import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.exlp.util.io.ConfigLoader;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.otrcutmp4.util.OtrBootstrap;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OtrUtilTstBootstrap
{
	static Log logger = LogFactory.getLog(OtrUtilTstBootstrap.class);
	
	private static final String confCode="test";
	
	public static Configuration init() throws ExlpConfigurationException
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/otrcutmp4-util");
			loggerInit.init();
		
		ConfigLoader.add(ExlpCentralConfigPointer.getFile(OtrBootstrap.appCode,confCode).getAbsolutePath());
		Configuration config = ConfigLoader.init();
		return config;
	}
}
