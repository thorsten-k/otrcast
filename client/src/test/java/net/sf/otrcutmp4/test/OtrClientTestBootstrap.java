package net.sf.otrcutmp4.test;

import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.OtrCutMp4Bootstrap;
import de.kisner.otrcast.model.xml.OtrCutNsPrefixMapper;
import de.kisner.otrcast.util.OtrBootstrap;
import de.kisner.otrcast.util.OtrConfig;

public class OtrClientTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(OtrClientTestBootstrap.class);
	
	public static Configuration init() throws ExlpConfigurationException
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("otrcutmp4-client.test");
			loggerInit.init();
		JaxbUtil.setNsPrefixMapper(new OtrCutNsPrefixMapper());
		
		ConfigLoader.add(ExlpCentralConfigPointer.getFile(OtrBootstrap.appCode,OtrBootstrap.confCode).getAbsolutePath());
		ConfigLoader.add(OtrCutMp4Bootstrap.xmlConfig);
		Configuration config = ConfigLoader.init();
		return config;
	}
	
	public static OtrConfig initOtr() throws ExlpConfigurationException
	{
		Configuration config = init();
		OtrConfig otrConfig = new OtrConfig(config);
		return otrConfig;
	}
}
