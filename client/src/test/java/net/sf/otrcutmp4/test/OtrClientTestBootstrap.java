package net.sf.otrcutmp4.test;

import org.apache.commons.configuration.Configuration;
import org.exlp.controller.handler.io.log.LoggerBootstrap;
import org.exlp.controller.handler.system.property.ConfigLoader;
import org.exlp.util.io.config.ExlpCentralConfigPointer;
import org.exlp.util.jx.JaxbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.OtrCastBootstrap;
import de.kisner.otrcast.model.xml.OtrCastNsPrefixMapper;
import de.kisner.otrcast.util.OtrBootstrap;
import de.kisner.otrcast.util.OtrConfig;
import net.sf.exlp.exception.ExlpConfigurationException;

public class OtrClientTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(OtrClientTestBootstrap.class);
	
	public static Configuration init() throws ExlpConfigurationException
	{
		LoggerBootstrap.instance("otr.log4j2.xml").path("otr/system/io/log").init();
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
