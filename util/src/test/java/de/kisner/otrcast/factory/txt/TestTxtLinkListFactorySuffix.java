package de.kisner.otrcast.factory.txt;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.exception.OtrProcessingException;
import de.kisner.otrcast.factory.txt.TxtLinkListFactory;
import de.kisner.otrcast.factory.xml.XmlQualityFactory;
import de.kisner.otrcast.model.xml.otr.Format;
import de.kisner.otrcast.model.xml.otr.Quality;
import de.kisner.otrcast.test.AbstractOtrcastTest;

public class TestTxtLinkListFactorySuffix extends AbstractOtrcastTest
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