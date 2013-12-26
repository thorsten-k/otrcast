package net.sf.otrcutmp4.controller.processor;

import net.sf.otrcutmp4.test.AbstractUtilTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		String test = "http://otr.datenkeller.at/?getFile="+expected;
		String actual = kpp.guess(test);
    	Assert.assertEquals(expected,actual);

        test = "http://otr.datenkeller.at/?getFile="+expected+".otrkey";
        actual = kpp.guess(test);
        Assert.assertEquals(expected,actual);

        test = "http://otr.datenkeller.at//?getFile="+expected;
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
	
 }