package de.kisner.otrcast.factory.txt;

import java.io.FileNotFoundException;
import java.util.List;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.exception.OtrProcessingException;
import de.kisner.otrcast.factory.txt.TxtLinkListFactory;
import de.kisner.otrcast.factory.xml.XmlQualityFactory;
import de.kisner.otrcast.model.xml.cut.CutList;
import de.kisner.otrcast.model.xml.otr.Format;
import de.kisner.otrcast.model.xml.otr.Link;
import de.kisner.otrcast.model.xml.otr.Linklist;
import de.kisner.otrcast.model.xml.otr.OtrId;
import de.kisner.otrcast.model.xml.otr.Quality;
import de.kisner.otrcast.model.xml.otr.Recording;
import de.kisner.otrcast.test.AbstractUtilTest;

public class TestTxtLinkListFactory extends AbstractUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestTxtLinkListFactorySuffix.class);
	
	private static final String refUrl = "http://81.95.11.21/download/1234567/1/5465768/424fb8a0c689832dc60379f9b89787ff";
	private static final String refKey = "Hangover_11.09.04_20-15_pro7_135_TVOON_DE";
			
	private Recording recording;
	private TxtLinkListFactory txtLinkFactory;
	
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
		txtLinkFactory = new TxtLinkListFactory();
	}
	
	@Test
	public void hqAvi() throws OtrProcessingException
    {
		StringBuffer expected = new StringBuffer();
		expected.append(refUrl).append("/");
		expected.append(refKey);
		expected.append(".mpg.HQ.avi");
		
    	String test = txtLinkFactory.createOtrDownload(recording);
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
		
    	String test = txtLinkFactory.createOtrDownload(recording);
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
		
    	String test = txtLinkFactory.createOtrDownload(recording);
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
    	
    	CutList cl = new CutList();
    	cl.setId("123");
    	
    	Recording recording = new Recording();
    	recording.setFormat(format);
    	recording.setLink(link);
    	recording.setOtrId(otrId);
    	recording.setCutList(cl);
    	
    	return recording;
    }
    
    @Test
    public void testXml() throws FileNotFoundException, OtrProcessingException
    {
    	Linklist xml = JaxbUtil.loadJAXB("xml/recordings.xml", Linklist.class);
    	List<String> actual = txtLinkFactory.create(xml);
    	JaxbUtil.debug(xml);
    	for(String s : actual){logger.debug(s);}
    }
 }