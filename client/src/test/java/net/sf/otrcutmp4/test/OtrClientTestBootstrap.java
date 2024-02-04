package net.sf.otrcutmp4.test;

import net.sf.exlp.exception.ExlpConfigurationException;

import org.apache.commons.configuration.Configuration;
import org.exlp.controller.handler.system.property.ConfigLoader;
import org.exlp.util.io.config.ExlpCentralConfigPointer;
import org.exlp.util.io.log.LoggerInit;
import org.exlp.util.jx.JaxbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.OtrCastBootstrap;
import de.kisner.otrcast.model.xml.OtrCastNsPrefixMapper;
import de.kisner.otrcast.util.OtrBootstrap;
import de.kisner.otrcast.util.OtrConfig;

public class OtrClientTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(OtrClientTestBootstrap.class);
	
	public static Configuration init() throws ExlpConfigurationException
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.path("otrcutmp4-client.test");
			loggerInit.init();
		JaxbUtil.setNsPrefixMapper(new OtrCastNsPrefixMapper());
		
		ExlpCentralConfigPointer ccp = ExlpCentralConfigPointer.instance(OtrBootstrap.appCode).jaxb(JaxbUtil.instance());
		ConfigLoader.addFile(ccp.toFile(OtrBootstrap.confCode));
		ConfigLoader.addString(OtrCastBootstrap.xmlConfig);
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
