package de.kisner.otrcast.test;

import java.time.LocalDateTime;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import org.exlp.util.io.log.LoggerInit;
import org.exlp.util.jx.JaxbUtil;
import org.exlp.util.system.DateUtil;
import org.jeesl.test.AbstractJeeslXmlTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.OtrCastNsPrefixMapper;

public abstract class AbstractOtrXmlTest <T extends Object> extends AbstractJeeslXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOtrXmlTest.class);
	
	public AbstractOtrXmlTest(Class<T> cXml,String xmlDirSuffix)
	{
		super(cXml,"otrcast-entities.test/data/xml",xmlDirSuffix);
	}
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
		loggerInit.path("otrcast-entities.test/config");
		loggerInit.init();
    }
	
	@BeforeClass
	public static void initXml()
	{
		JaxbUtil.setNsPrefixMapper(new OtrCastNsPrefixMapper());
	}
	
	protected void assertJaxbEquals(Object expected, Object actual)
	{
		Assert.assertEquals("XML-ref differes from XML-test",JaxbUtil.toString(expected),JaxbUtil.toString(actual));
	}
	
	protected static XMLGregorianCalendar getDefaultXmlDate()
	{
		return DateUtil.toXmlGc(getDefaultDate());
	}
	
	protected static Date getDefaultDate()
	{
		LocalDateTime ldt = LocalDateTime.of(2011,11,11,11,11,11);
		return DateUtil.toDate(ldt);
	}
}