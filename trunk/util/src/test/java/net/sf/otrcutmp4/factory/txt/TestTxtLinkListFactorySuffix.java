package net.sf.otrcutmp4.factory.txt;

import net.sf.otrcutmp4.controller.exception.OtrProcessingException;
import net.sf.otrcutmp4.factory.xml.XmlQualityFactory;
import net.sf.otrcutmp4.model.xml.otr.Format;
import net.sf.otrcutmp4.model.xml.otr.Quality;
import net.sf.otrcutmp4.test.AbstractUtilTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTxtLinkListFactorySuffix extends AbstractUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestTxtLinkListFactorySuffix.class);
	
	private Format format;
	private Quality quality;
	
	@Before
	public void init()
	{
		format = new Format();
		format.setCut(false);
		format.setOtrkey(false);
		
		quality = new Quality();
	}
	
	@Test
	public void hqAvi() throws OtrProcessingException
    {
		quality.setType(XmlQualityFactory.qHQ);
		format.setType("avi");
		String actual = TxtLinkListFactory.createSuffix(format, quality);
		String expected = ".mpg.HQ.avi";
		Assert.assertEquals(expected, actual);
    }
	
	@Test
	public void hqMp4Cut() throws OtrProcessingException
    {
		quality.setType(XmlQualityFactory.qHQ);
		format.setType("mp4");format.setCut(true);
		String actual = TxtLinkListFactory.createSuffix(format, quality);
		String expected = ".mpg.HQ.cut.mp4";
		Assert.assertEquals(expected, actual);
    }
	
	@Test
	public void hqAviKey() throws OtrProcessingException
    {
		quality.setType(XmlQualityFactory.qHQ);
		format.setType("avi");format.setOtrkey(true);
		String actual = TxtLinkListFactory.createSuffix(format, quality);
		String expected = ".mpg.HQ.avi.otrkey";
		Assert.assertEquals(expected, actual);
    }
 }