package net.sf.otrcutmp4.test;

import java.util.Date;
import java.util.Random;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.ns.NsPrefixMapperInterface;
import net.sf.otrcutmp4.model.xml.ns.OtrCutNsPrefixMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.BeforeClass;

public abstract class AbstractUtilTest
{
	static Log logger = LogFactory.getLog(AbstractUtilTest.class);	
	
	protected static NsPrefixMapperInterface nsPrefixMapper;
	protected static Random rnd;
	
	@BeforeClass
	public static void initPrefixMapper()
	{
		nsPrefixMapper = new OtrCutNsPrefixMapper();
		rnd = new Random();
	}
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
		loggerInit.addAltPath("src/test/resources/otrcutmp4-util");
		loggerInit.init();
    }
	
	protected void assertJaxbEquals(Object ref, Object test)
	{
		Assert.assertEquals(JaxbUtil.toString(ref),JaxbUtil.toString(test));
	}
	
	protected static Date getDefaultDate()
	{
		return DateUtil.getDateFromInt(2011, 11, 11, 11, 11, 11);
	}
}