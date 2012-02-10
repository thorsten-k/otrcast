package net.sf.otrcutmp4.controller.factory.xml;

import junit.framework.Assert;
import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.exception.OtrProcessingException;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.otr.Format;
import net.sf.otrcutmp4.test.AbstractUtilTest;
import net.sf.otrcutmp4.test.OtrUtilTstBootstrap;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlVideoFileFactory extends AbstractUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlVideoFileFactory.class);
	
	private String fileName;
	
//	@Rule public IgnoreOtherRule test = new IgnoreOtherRule("testHdAc3Key");
	
    @Test public void testAvi() throws OtrProcessingException
    {
    	fileName = "Hawaii_Five_0_11.08.25_21-15_sat1_60_TVOON_DE.mpg.HQ.avi";
    	VideoFile xml = XmlVideoFileFactory.create(fileName);
    	JaxbUtil.debug(xml);
    	Assert.assertTrue(xml.isSetOtrId());
    	Assert.assertTrue(xml.isSetFileName());
    	Assert.assertTrue(xml.getFileName().isSetValue());
    	Assert.assertEquals(fileName, xml.getFileName().getValue());
    	
    }
    
    public static void main(String[] args) throws ExlpConfigurationException, OtrProcessingException
    {
		OtrUtilTstBootstrap.init();		
			
		TestXmlVideoFileFactory.initPrefixMapper();
	
		TestXmlVideoFileFactory test = new TestXmlVideoFileFactory();
		test.testAvi();
    }
 }