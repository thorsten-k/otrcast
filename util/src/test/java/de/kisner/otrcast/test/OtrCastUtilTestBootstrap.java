package de.kisner.otrcast.test;

import org.apache.commons.configuration.Configuration;
import org.exlp.controller.handler.io.log.LoggerBootstrap;
import org.exlp.controller.handler.system.property.ConfigLoader;
import org.exlp.util.io.config.ExlpCentralConfigPointer;
import org.exlp.util.jx.JaxbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.OtrCastNsPrefixMapper;
import net.sf.exlp.exception.ExlpConfigurationException;

public class OtrCastUtilTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(OtrCastUtilTestBootstrap.class);
	
	public static Configuration init() throws ExlpConfigurationException
	{
		LoggerBootstrap.instance("otr.log4j2.xml").path("otr/system/io/log").init();
		
		JaxbUtil.setNsPrefixMapper(new OtrCastNsPrefixMapper());	
		
		ExlpCentralConfigPointer ccp = ExlpCentralConfigPointer.instance("otr").jaxb(JaxbUtil.instance());
		ConfigLoader.addFile(ccp.toFile("util"));
		Configuration config = ConfigLoader.init();
		return config;
	}
}
