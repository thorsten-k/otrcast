package de.kisner.otrcast.test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import org.exlp.controller.handler.io.log.LoggerBootstrap;
import org.exlp.test.AbstractXmlTest;
import org.exlp.util.jx.JaxbUtil;
import org.exlp.util.system.DateUtil;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.OtrCastNsPrefixMapper;

public abstract class AbstractOtrXmlTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOtrXmlTest.class);
	
	public AbstractOtrXmlTest(Class<T> cXml, Path pSuffix)
	{
		super(cXml,Paths.get("otrcast-entities.test","data","xml"),pSuffix);
	}
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerBootstrap.instance("otr.log4j2.xml").path("ofx/system/io/log").init();
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