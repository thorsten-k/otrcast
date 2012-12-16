package net.sf.otrcutmp4.test;

import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.model.xml.OtrCutNsPrefixMapper;
import net.sf.otrcutmp4.util.OtrBootstrap;
import net.sf.otrcutmp4.util.OtrConfig;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OtrClientTstBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(OtrClientTstBootstrap.class);
	
	
	
	public static Configuration init() throws ExlpConfigurationException
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("otrcutmp4-client.test");
			loggerInit.init();
		JaxbUtil.setNsPrefixMapper(new OtrCutNsPrefixMapper());
		
		ConfigLoader.add(ExlpCentralConfigPointer.getFile(OtrBootstrap.appCode,OtrBootstrap.confCode).getAbsolutePath());
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
