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

import de.kisner.otrcast.model.xml.OtrCutNsPrefixMapper;

public class OtrUtilTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(OtrUtilTestBootstrap.class);
	
	public static String mp4TestDst = "test.mp4Tagger.dst";
	
	
	public static Configuration init() throws ExlpConfigurationException
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("config.otrcutmp4-util.test");
			loggerInit.addAltPath("src/test/resources/config.otrcutmp4-util.test");
			loggerInit.init();
		
		JaxbUtil.setNsPrefixMapper(new OtrCutNsPrefixMapper());	
			
		File fConfig = ExlpCentralConfigPointer.getFile("otr","util.test");
		ConfigLoader.add(fConfig.getAbsolutePath());
		Configuration config = ConfigLoader.init();
		return config;
	}
}
