package net.sf.otrcutmp4.util;

import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.exlp.util.io.ConfigLoader;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;
import net.sf.exlp.util.io.LoggerInit;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OtrBootstrap
{
	static Log logger = LogFactory.getLog(OtrBootstrap.class);
	
	public static final String appCode="otrcutmp4-client";
	private static final String confCode="test";
	
	public static final String cfgUrlGae = "url.otrseries";
	public static final String cfgXmlCategories = "xml.categories";
	public static final String cfgXmlSeries = "xml.series";
	public static final String cfgXmlEpisodes = "xml.episodes";
	public static final String cfgXmlFormats = "xml.formats";
	
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
