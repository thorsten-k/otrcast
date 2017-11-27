package de.kisner.otrcast.test;

import java.io.File;

import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.OtrCastNsPrefixMapper;

public class OtrCastUtilTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(OtrCastUtilTestBootstrap.class);
	
	public static Configuration init() throws ExlpConfigurationException
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
		loggerInit.addAltPath("otrcast-util.test/config");
		loggerInit.addAltPath("src/test/resources/config.otrcutmp4-util.test");
		loggerInit.init();
		
		JaxbUtil.setNsPrefixMapper(new OtrCastNsPrefixMapper());	
			
		File fConfig = ExlpCentralConfigPointer.getFile("otr","util");
		ConfigLoader.add(fConfig.getAbsolutePath());
		Configuration config = ConfigLoader.init();
		return config;
	}
}
