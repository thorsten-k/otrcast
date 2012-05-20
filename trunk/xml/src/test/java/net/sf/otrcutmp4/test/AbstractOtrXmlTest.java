package net.sf.otrcutmp4.test;

import java.io.File;
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

public abstract class AbstractOtrXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOtrXmlTest.class);
	
	protected NsPrefixMapperInterface nsPrefixMapper;
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
		loggerInit.addAltPath("config.otrcutmp4-xml.test");
		loggerInit.init();
    }
	
	protected NsPrefixMapperInterface getPrefixMapper()
	{
		if(nsPrefixMapper==null){nsPrefixMapper = new OtrCutNsPrefixMapper();}
		return nsPrefixMapper;
	}
	
	protected void assertJaxbEquals(Object expected, Object actual)
	{
		Assert.assertEquals("XML-ref differes from XML-test",JaxbUtil.toString(expected),JaxbUtil.toString(actual));
	}
	
	protected void save(Object xml, File f)
	{
		logger.debug("Saving Reference XML");
		JaxbUtil.debug(xml, getPrefixMapper());
    	JaxbUtil.save(f, xml, getPrefixMapper(), true);
	}
	
	protected static Date getDefaultDate()
	{
		return DateUtil.getDateFromInt(2011, 11, 11, 11, 11, 11);
	}
}