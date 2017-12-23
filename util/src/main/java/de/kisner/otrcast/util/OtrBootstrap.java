package de.kisner.otrcast.util;

import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.OtrCastNsPrefixMapper;

public class OtrBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(OtrBootstrap.class);
	
	public static final String appCode="otr";
	public static final String confCode="cast.client";
	
	public static final String cfgXmlCategories = "xml.categories";
	public static final String cfgXmlSeries = "xml.series";
	public static final String cfgXmlEpisodes = "xml.episodes";
	public static final String cfgXmlFormats = "xml.formats";
	public static final String cfgXmlQuality = "xml.quality";
	
	public static Configuration init() throws ExlpConfigurationException
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
		loggerInit.addAltPath("otrcast/config");
		loggerInit.init();
			
		JaxbUtil.setNsPrefixMapper(new OtrCastNsPrefixMapper());
		
		ConfigLoader.add(ExlpCentralConfigPointer.getFile(appCode,confCode).getAbsolutePath());
		Configuration config = ConfigLoader.init();
		return config;
	}
}
