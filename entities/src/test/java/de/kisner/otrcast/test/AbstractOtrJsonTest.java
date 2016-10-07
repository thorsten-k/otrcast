package de.kisner.otrcast.test;

import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import net.sf.ahtutils.test.AbstractAhtUtilsXmlTest;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.kisner.otrcast.model.xml.OtrCastNsPrefixMapper;

public abstract class AbstractOtrJsonTest extends AbstractAhtUtilsXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOtrJsonTest.class);
	
	protected static ObjectMapper jom;
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
		loggerInit.addAltPath("config.otrcutmp4-xml.test");
		loggerInit.init();
    }
	
	@BeforeClass
	public static void initJson()
	{
		jom = new ObjectMapper();
	}
	
	@BeforeClass
	public static void initXml()
	{
		JaxbUtil.setNsPrefixMapper(new OtrCastNsPrefixMapper());
		DateUtil.ignoreTimeZone=true;
	}
	
	protected void assertJaxbEquals(Object expected, Object actual)
	{
		Assert.assertEquals("XML-ref differes from XML-test",JaxbUtil.toString(expected),JaxbUtil.toString(actual));
	}
	
	protected static XMLGregorianCalendar getDefaultXmlDate()
	{
		return DateUtil.getXmlGc4D(getDefaultDate());
	}
	
	protected static Date getDefaultDate()
	{
		return DateUtil.getDateFromInt(2011, 11, 11, 11, 11, 11);
	}
}