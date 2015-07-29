package de.kisner.otrcast.controller.processor;

import net.sf.exlp.exception.ExlpConfigurationException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.exception.OtrProcessingException;
import de.kisner.otrcast.controller.processor.OtrKeyPreProcessor;
import de.kisner.otrcast.factory.xml.TestXmlVideoFileFactory;
import de.kisner.otrcast.test.AbstractUtilTest;
import de.kisner.otrcast.test.OtrUtilTestBootstrap;

public class TestOtrKeyPreProcessor extends AbstractUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestOtrKeyPreProcessor.class);
	
	private static final String expected = "Dr__House_10.10.05_21-15_rtl_60_TVOON_DE.mpg.HQ.avi";
	
	private OtrKeyPreProcessor kpp;
	
	@Before
	public void init()
	{
		kpp = new OtrKeyPreProcessor();
	}
	
	@Test
	public void testOtrkey() 
    {
		String test = expected+".otrkey";
		String actual = kpp.guess(test);	
    	Assert.assertEquals(expected,actual);
    }

    @Test
	public void testDatenkeller() 
    {
		String test = OtrKeyPreProcessor.prefixDatenkeller+OtrKeyPreProcessor.getFile+expected;
		String actual = kpp.guess(test);
    	Assert.assertEquals(expected,actual);
    	
    	test = OtrKeyPreProcessor.prefixDatenkellerS+OtrKeyPreProcessor.getFile+expected;
		actual = kpp.guess(test);
    	Assert.assertEquals(expected,actual);

        test = OtrKeyPreProcessor.prefixDatenkeller+OtrKeyPreProcessor.getFile+expected+".otrkey";
        actual = kpp.guess(test);
        Assert.assertEquals(expected,actual);

        test = OtrKeyPreProcessor.prefixDatenkeller+OtrKeyPreProcessor.getFile+expected;
        actual = kpp.guess(test);
        Assert.assertEquals(expected,actual);
        
        test = OtrKeyPreProcessor.prefixDatenkeller+OtrKeyPreProcessor.getFile+expected;
        actual = kpp.guess(test);
        Assert.assertEquals(expected,actual);
    }

	
	@Test
	public void testOtrDownload()
	{
		String test = "http://81.95.11.3/download/2094360/1/7524402/e0a155c7b9653defa2f52726148661fe/de/"+expected;
		String actual = kpp.guess(test);
    	Assert.assertEquals(expected,actual);
	}
	
	@Test
	public void testDatenkeller2()
	{
		String x = "https://otr.datenkeller.net/?getFile=S02_E07_The_Blacklist_15.03.20_00-30_rtl_55_TVOON_DE.mpg.HQ.avi.otrkey";
		String actual = kpp.guess(x);
		logger.info(actual);
	}
	
	public static void main(String[] args) throws ExlpConfigurationException, OtrProcessingException
    {
		OtrUtilTestBootstrap.init();		
			
		TestXmlVideoFileFactory.initPrefixMapper();
	
		TestOtrKeyPreProcessor cli = new TestOtrKeyPreProcessor();
		cli.init();
		cli.testDatenkeller2();
    }
	
 }