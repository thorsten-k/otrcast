package de.kisner.otrcast.test;

import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.OtrCastNsPrefixMapper;
import net.sf.ahtutils.test.AbstractJeeslTest;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

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
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
		loggerInit.addAltPath("otrcast-util.test/config");
		loggerInit.init();
    }
}