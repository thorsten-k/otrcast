package net.sf.otrcutmp4.test;

import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.exlp.util.io.ConfigLoader;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;
import net.sf.exlp.util.io.LoggerInit;

import org.apache.commons.configuration.Configuration;

public class OtrClientTstBootstrap
{
	private static final String appCode="otrcutmp4-client";
	private static final String confCode="test";
	
	public static Configuration init() throws ExlpConfigurationException
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/otrcutmp4-client");
			loggerInit.init();
		
		ConfigLoader.add(ExlpCentralConfigPointer.getFile(appCode,confCode).getAbsolutePath());
		Configuration config = ConfigLoader.init();
		return config;
	}
}
