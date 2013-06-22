package net.sf.otrcutmp4.test;

import java.util.Random;

import net.sf.ahtutils.test.AbstractAhtUtilsTest;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.model.xml.OtrCutNsPrefixMapper;

import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractUtilTest extends AbstractAhtUtilsTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractUtilTest.class);
	
	protected static Random rnd;
	
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