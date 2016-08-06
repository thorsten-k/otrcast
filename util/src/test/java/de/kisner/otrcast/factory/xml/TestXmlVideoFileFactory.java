package de.kisner.otrcast.factory.xml;

import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.AbstractOtrcastTest;
import de.kisner.otrcast.OtrUtilTestBootstrap;
import de.kisner.otrcast.controller.exception.OtrProcessingException;
import de.kisner.otrcast.factory.xml.XmlVideoFileFactory;
import de.kisner.otrcast.model.xml.cut.VideoFile;

public class TestXmlVideoFileFactory extends AbstractOtrcastTest
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
    
    @Test public void testAvi2() throws OtrProcessingException
    {
    	fileName = "S02_E07_The_Blacklist_15.03.20_00-30_rtl_55_TVOON_DE.mpg.HQ.avi";
    	VideoFile xml = XmlVideoFileFactory.create(fileName);
    	JaxbUtil.debug(xml);
    	Assert.assertTrue(xml.isSetOtrId());
    	Assert.assertTrue(xml.isSetFileName());
    	Assert.assertTrue(xml.getFileName().isSetValue());
    	Assert.assertEquals(fileName, xml.getFileName().getValue());
    }
    
    public static void main(String[] args) throws ExlpConfigurationException, OtrProcessingException
    {
		OtrUtilTestBootstrap.init();		
			
		TestXmlVideoFileFactory.initPrefixMapper();
	
		TestXmlVideoFileFactory test = new TestXmlVideoFileFactory();
		test.testAvi();
		test.testAvi2();
    }
 }