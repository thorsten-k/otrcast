package net.sf.otrcutmp4.controller.factory.txt;

import net.sf.otrcutmp4.controller.exception.OtrProcessingException;
import net.sf.otrcutmp4.controller.factory.xml.XmlQualityFactory;
import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.otr.Format;
import net.sf.otrcutmp4.model.xml.otr.Link;
import net.sf.otrcutmp4.model.xml.otr.OtrId;
import net.sf.otrcutmp4.model.xml.otr.Quality;
import net.sf.otrcutmp4.model.xml.otr.Recording;
import net.sf.otrcutmp4.test.AbstractUtilTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTxtLinkListFactory extends AbstractUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestTxtLinkListFactorySuffix.class);
	
	private static final String refUrl = "http://81.95.11.21/download/1234567/1/5465768/424fb8a0c689832dc60379f9b89787ff";
	private static final String refKey = "Hangover_11.09.04_20-15_pro7_135_TVOON_DE";
			
	private Recording recording;
	
	public static TestTxtLinkListFactory factory()
    {
		TestTxtLinkListFactory factory = new TestTxtLinkListFactory();
		TestTxtLinkListFactory.initPrefixMapper();
    	return factory;
    }
	
	@Before
	public void init()
	{
		recording = createXml();
	}
	
	@Test
	public void hqAvi() throws OtrProcessingException
    {
		StringBuffer expected = new StringBuffer();
		expected.append(refUrl).append("/");
		expected.append(refKey);
		expected.append(".mpg.HQ.avi");
		
    	String test = TxtLinkListFactory.createOtrDownload(recording);
    	Assert.assertEquals(expected.toString(),test);
    }
	
	@Test
	public void hqMp4Cut() throws OtrProcessingException
    {
		CutList cl = new CutList();cl.setId("12345");
		
		StringBuffer expected = new StringBuffer();
		expected.append(refUrl).append("/");
		expected.append(cl.getId()).append("_");
		expected.append(refKey);
		expected.append(".mpg.HQ.cut.mp4");
		
    	recording.getFormat().setCut(true);recording.getFormat().setType("mp4");
    	recording.setCutList(cl);
		
    	String test = TxtLinkListFactory.createOtrDownload(recording);
    	Assert.assertEquals(expected.toString(),test);
    }
	
	@Test
	public void hqhqAviKeyAvi() throws OtrProcessingException
    {
		StringBuffer expected = new StringBuffer();
		expected.append(refUrl).append("/");
		expected.append(refKey);
		expected.append(".mpg.HQ.avi.otrkey");
		
		recording.getFormat().setOtrkey(true);
		
    	String test = TxtLinkListFactory.createOtrDownload(recording);
    	Assert.assertEquals(expected.toString(),test);
    }
 
    public Recording createXml()
    {
    	OtrId otrId = new OtrId();
    	otrId.setKey(refKey);
    	
    	Link link = new Link();
    	link.setUrl(refUrl);
    	
    	Quality quality = new Quality();
    	quality.setType(XmlQualityFactory.qHQ);
    	
    	Format format = new Format();
    	format.setType("avi");
    	format.setQuality(quality);
    	format.setCut(false);
    	format.setOtrkey(false);
    	
    	Recording recording = new Recording();
    	recording.setFormat(format);
    	recording.setLink(link);
    	recording.setOtrId(otrId);
    	
    	return recording;
    }
 }