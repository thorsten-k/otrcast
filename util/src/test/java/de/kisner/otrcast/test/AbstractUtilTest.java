package de.kisner.otrcast.test;

import net.sf.ahtutils.test.AbstractAhtUtilsTest;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.OtrCutNsPrefixMapper;

public abstract class AbstractUtilTest extends AbstractAhtUtilsTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractUtilTest.class);
	
	@BeforeClass
	public static void initPrefixMapper()
	{
		JaxbUtil.setNsPrefixMapper(new OtrCutNsPrefixMapper());
	}
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
		loggerInit.addAltPath("config.otrcutmp4-util.test");
		loggerInit.init();
    }
}