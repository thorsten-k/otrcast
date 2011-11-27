package net.sf.otrcutmp4.test;

import java.util.Date;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.ns.NsPrefixMapperInterface;
import net.sf.otrcutmp4.model.xml.ns.OtrCutNsPrefixMapper;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractClientTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractClientTest.class);
	
	protected static NsPrefixMapperInterface nsPrefixMapper;
	
	@BeforeClass
	public static void initPrefixMapper()
	{
		nsPrefixMapper = new OtrCutNsPrefixMapper();
	}
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
		loggerInit.addAltPath("otrcutmp4-client.test");
		loggerInit.init();
    }
	
	protected void assertJaxbEquals(Object expected, Object actual)
	{
		Assert.assertEquals(JaxbUtil.toString(expected),JaxbUtil.toString(actual));
	}
	
	protected static Date getDefaultDate()
	{
		return DateUtil.getDateFromInt(2011, 11, 11, 11, 11, 11);
	}
}