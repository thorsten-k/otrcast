package net.sf.otrcutmp4.controller.factory.xml.otr;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.exception.OtrProcessingException;
import net.sf.otrcutmp4.model.xml.otr.Tv;
import net.sf.otrcutmp4.test.AbstractUtilTest;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    
    @Test(expected=OtrProcessingException.class)
    public void noMatch() throws OtrProcessingException
    {
    	fileName = "abc";
    	Tv xml = XmlTvFactory.createForFileName(fileName);
    	JaxbUtil.info(xml);
   
    	
    }
 }