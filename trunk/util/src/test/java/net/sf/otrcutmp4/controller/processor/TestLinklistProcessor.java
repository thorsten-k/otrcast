package net.sf.otrcutmp4.controller.processor;

import junit.framework.Assert;
import net.sf.otrcutmp4.test.AbstractUtilTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

public class TestLinklistProcessor extends AbstractUtilTest
{
	static Log logger = LogFactory.getLog(TestLinklistProcessor.class);
	
	private LinklistProcessor llP;
	
	@Before
	public void initFiles()
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
    	boolean test = llP.getProcessSelector().get(otrKey).get(accessKey);
    	
    	Assert.assertEquals(ref, test);
    }
 
}