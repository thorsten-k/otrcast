package net.sf.otrcutmp4.controller.factory.xml.otr;

import junit.framework.Assert;
import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.exception.OtrProcessingException;
import net.sf.otrcutmp4.model.xml.otr.OtrId;
import net.sf.otrcutmp4.test.AbstractUtilTest;
import net.sf.otrcutmp4.test.OtrUtilTstBootstrap;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlOtrIdFactory extends AbstractUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlOtrIdFactory.class);
	
	private String fileName;
	
//	@Rule public IgnoreOtherRule test = new IgnoreOtherRule("testHdAc3Key");
	
    @Test public void testAvi() throws OtrProcessingException
    {
    	fileName = "Hawaii_Five_0_11.08.25_21-15_sat1_60_TVOON_DE.mpg.HQ.avi";
    	OtrId xml = XmlOtrIdFactory.createForFileName(fileName);
    	JaxbUtil.debug(xml);
    	Assert.assertTrue(xml.isSetFormat());
    	Assert.assertTrue(xml.getFormat().isSetType());
    	Assert.assertEquals("mpg.HQ.avi", xml.getFormat().getType());
    	
    	Assert.assertTrue(xml.isSetKey());
    	Assert.assertEquals("Hawaii_Five_0_11.08.25_21-15_sat1_60_TVOON_DE", xml.getKey());
    	
    }
    
    public static void main(String[] args) throws ExlpConfigurationException, OtrProcessingException
    {
		OtrUtilTstBootstrap.init();		
			
		TestXmlOtrIdFactory.initPrefixMapper();
	
		TestXmlOtrIdFactory test = new TestXmlOtrIdFactory();
		test.testAvi();
    }
 }