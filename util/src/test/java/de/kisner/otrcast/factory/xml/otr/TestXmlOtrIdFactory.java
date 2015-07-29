package de.kisner.otrcast.factory.xml.otr;

import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.exception.OtrProcessingException;
import de.kisner.otrcast.factory.xml.otr.XmlOtrIdFactory;
import de.kisner.otrcast.model.xml.otr.OtrId;
import de.kisner.otrcast.test.AbstractUtilTest;
import de.kisner.otrcast.test.OtrUtilTestBootstrap;

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
		OtrUtilTestBootstrap.init();		
			
		TestXmlOtrIdFactory.initPrefixMapper();
	
		TestXmlOtrIdFactory test = new TestXmlOtrIdFactory();
		test.testAvi();
    }
 }