package de.kisner.otrcast.test;

import org.exlp.controller.handler.io.log.LoggerBootstrap;
import org.exlp.util.jx.JaxbUtil;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.OtrCastNsPrefixMapper;
import net.sf.ahtutils.test.AbstractJeeslTest;

public abstract class AbstractOtrcastTest extends AbstractJeeslTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOtrcastTest.class);
	
	@BeforeClass
	public static void initPrefixMapper()
	{
		JaxbUtil.setNsPrefixMapper(new OtrCastNsPrefixMapper());
	}
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerBootstrap.instance("otr.log4j2.xml").path("otr/system/io/log").init();
    }
}