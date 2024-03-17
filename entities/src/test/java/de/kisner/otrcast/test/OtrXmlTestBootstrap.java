package de.kisner.otrcast.test;

import org.exlp.controller.handler.io.log.LoggerBootstrap;
import org.exlp.util.jx.JaxbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.OtrCastNsPrefixMapper;

public class OtrXmlTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(OtrXmlTestBootstrap.class);
		
	public static void init()
	{
		LoggerBootstrap.instance("otr.log4j2.xml").path("ofx/system/io/log").init();
		JaxbUtil.setNsPrefixMapper(new OtrCastNsPrefixMapper());
	}
}