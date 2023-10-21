package de.kisner.otrcast.util;

import org.apache.commons.configuration.Configuration;
import org.exlp.controller.handler.system.property.ConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.OtrCastNsPrefixMapper;
import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

public class OtrBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(OtrBootstrap.class);
	
	public enum AppCode {otr};
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
		loggerInit.path("otrcast/config");
		loggerInit.init();
			
		JaxbUtil.setNsPrefixMapper(new OtrCastNsPrefixMapper());
		
		ExlpCentralConfigPointer ccp = ExlpCentralConfigPointer.instance(OtrBootstrap.AppCode.otr).jaxb(JaxbUtil.instance());
		
		ConfigLoader.addFile(ccp.toFile(confCode));
		Configuration config = ConfigLoader.init();
		return config;
	}
}
