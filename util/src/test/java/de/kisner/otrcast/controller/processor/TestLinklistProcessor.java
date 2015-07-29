package de.kisner.otrcast.controller.processor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.processor.LinklistProcessor;
import de.kisner.otrcast.test.AbstractUtilTest;

public class TestLinklistProcessor extends AbstractUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestLinklistProcessor.class);
	
	private LinklistProcessor llP;
	
	@Before
	public void init()
	{
		llP = new LinklistProcessor();
	}
    
    @Test
    public void test()
    {
    	for(int i=0;i<100;i++)
    	{
    		testRnd();
    	}
    }
    
    private void testRnd()
    {
    	boolean ref = rnd.nextBoolean();
    	boolean cut = rnd.nextBoolean();
    	boolean key = rnd.nextBoolean();
    	String otrKey = ""+rnd.nextLong();
    	String qualityType = ""+rnd.nextLong();
    	String formatType = ""+rnd.nextLong();
    	llP.process(ref, otrKey, qualityType, formatType, cut, key);
    	
    	String accessKey = LinklistProcessor.createAccessKey(qualityType, formatType, cut, key);
    	boolean actual = llP.getProcessSelector().get(otrKey).get(accessKey);
    	
    	Assert.assertEquals(ref, actual);
    }
 
}