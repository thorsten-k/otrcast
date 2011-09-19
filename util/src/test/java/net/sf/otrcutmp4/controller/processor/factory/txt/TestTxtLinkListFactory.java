package net.sf.otrcutmp4.controller.processor.factory.txt;

import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.otrcutmp4.controller.exception.OtrProcessingException;
import net.sf.otrcutmp4.model.xml.otr.Format;
import net.sf.otrcutmp4.model.xml.otr.Link;
import net.sf.otrcutmp4.model.xml.otr.OtrId;
import net.sf.otrcutmp4.model.xml.otr.Quality;
import net.sf.otrcutmp4.model.xml.otr.Recording;
import net.sf.otrcutmp4.test.AbstractUtilTest;
import net.sf.otrcutmp4.test.OtrUtilTstBootstrap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

public class TestTxtLinkListFactory extends AbstractUtilTest
{
	static Log logger = LogFactory.getLog(TestTxtLinkListFactory.class);
	
	private static final String url = "http://myUrl";
	
	@Test
	public void noXmlErrors() throws OtrProcessingException
    {
    	Recording test = createXml();
    	TxtLinkListFactory.createOtrDownload(test);
    }
	
	@Test
	public void txtLink() throws OtrProcessingException
    {
		StringBuffer sb = new StringBuffer();
		sb.append(url);
		
    	Recording xml = createXml();
    	String test = TxtLinkListFactory.createOtrDownload(xml);
    	Assert.assertEquals(sb.toString(),test);
    } 
	
    @Test (expected=OtrProcessingException.class)
    public void missingFormat() throws OtrProcessingException
    {
    	Recording test = createXml();
    	test.setFormat(null);
    	TxtLinkListFactory.createOtrDownload(test);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingFormatType() throws OtrProcessingException
    {
    	Recording test = createXml();
    	test.getFormat().setType(null);
    	TxtLinkListFactory.createOtrDownload(test);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingFormatQuality() throws OtrProcessingException
    {
    	Recording test = createXml();
    	test.getFormat().setQuality(null);
    	TxtLinkListFactory.createOtrDownload(test);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingFormatQualityType() throws OtrProcessingException
    {
    	Recording test = createXml();
    	test.getFormat().getQuality().setType(null);
    	TxtLinkListFactory.createOtrDownload(test);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingLink() throws OtrProcessingException
    {
    	Recording test = createXml();
    	test.setLink(null);
    	TxtLinkListFactory.createOtrDownload(test);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingLinkUrl() throws OtrProcessingException
    {
    	Recording test = createXml();
    	test.getLink().setUrl(null);
    	TxtLinkListFactory.createOtrDownload(test);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingOtrId() throws OtrProcessingException
    {
    	Recording test = createXml();
    	test.setOtrId(null);
    	TxtLinkListFactory.createOtrDownload(test);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingOtrIdKey() throws OtrProcessingException
    {
    	Recording test = createXml();
    	test.getOtrId().setKey(null);
    	TxtLinkListFactory.createOtrDownload(test);
    }
 
    private Recording createXml()
    {
    	OtrId otrId = new OtrId();
    	otrId.setKey("myKey");
    	
    	Link link = new Link();
    	link.setUrl(url);
    	
    	Quality quality = new Quality();
    	quality.setType("myQ");
    	
    	Format format = new Format();
    	format.setType("avi");
    	format.setQuality(quality);
    	
    	Recording recording = new Recording();
    	recording.setFormat(format);
    	recording.setLink(link);
    	recording.setOtrId(otrId);
    	
    	return recording;
    }
    
    public static void main(String[] args) throws ExlpConfigurationException
    {
		OtrUtilTstBootstrap.init();		
			
		TestTxtLinkListFactory.initPrefixMapper();
	
		TestTxtLinkListFactory test = new TestTxtLinkListFactory();
    }
 }