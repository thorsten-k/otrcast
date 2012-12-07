package net.sf.otrcutmp4.test;

import java.io.File;

import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.model.xml.OtrCutNsPrefixMapper;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OtrUtilTstBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(OtrUtilTstBootstrap.class);
	
	public static Configuration init() throws ExlpConfigurationException
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("otrcutmp4-util.test");
			loggerInit.addAltPath("src/test/resources/otrcutmp4-util.test");
			loggerInit.init();
		
		JaxbUtil.setNsPrefixMapper(new OtrCutNsPrefixMapper());	
			
		File fConfig = ExlpCentralConfigPointer.getFile("otr","util.test");
		ConfigLoader.add(fConfig.getAbsolutePath());
		Configuration config = ConfigLoader.init();
		return config;
	}
}
