package de.kisner.otrcast.factory.xml.otr;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.exception.OtrProcessingException;
import de.kisner.otrcast.factory.xml.otr.XmlTvFactory;
import de.kisner.otrcast.model.xml.otr.Tv;
import de.kisner.otrcast.test.AbstractUtilTest;

public class TestXmlTvFactory extends AbstractUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTvFactory.class);
	
	private String fileName;
	
//	@Rule public IgnoreOtherRule test = new IgnoreOtherRule("testHdAc3Key");
	
    @Test
    public void testAvi() throws OtrProcessingException
    {
    	fileName = "Hawaii_Five_0_11.08.25_21-15_sat1_60_TVOON_DE.mpg.HQ.avi";
    	Tv xml = XmlTvFactory.createForFileName(fileName);
    	JaxbUtil.info(xml);
    }
    
    @Test
    public void testDay31() throws OtrProcessingException
    {
    	fileName = "Stargate_12.08.31_20-15_rtl_60_TVOON_DE.mpg.HQ.avi";
    	Tv xml = XmlTvFactory.createForFileName(fileName);
    	JaxbUtil.info(xml);
    }
    
    @Test(expected=OtrProcessingException.class)
    public void noMatch() throws OtrProcessingException
    {
    	fileName = "abc";
    	Tv xml = XmlTvFactory.createForFileName(fileName);
    	JaxbUtil.info(xml);
    }
 }