package de.kisner.otrcast.test;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.OtrCastNsPrefixMapper;

public class OtrXmlTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(OtrXmlTestBootstrap.class);
		
	public static void init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
		loggerInit.addAltPath("otrcast-entities.test/config");
		loggerInit.init();
		JaxbUtil.setNsPrefixMapper(new OtrCastNsPrefixMapper());
	}
}