package net.sf.otrcutmp4.controller.factory.txt;

import net.sf.otrcutmp4.controller.exception.OtrProcessingException;
import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.otr.Recording;
import net.sf.otrcutmp4.test.AbstractUtilTest;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTxtLinkListFactoryXmlStructure extends AbstractUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestTxtLinkListFactoryXmlStructure.class);
	
	private Recording recording;
	
	@Before
	public void init()
	{
		TestTxtLinkListFactory factory = TestTxtLinkListFactory.factory();
		recording = factory.createXml();
	}
	
	@Test
	public void noXmlErrors() throws OtrProcessingException
    {
    	TxtLinkListFactory.checkXmlStructure(recording);
    }
	
    @Test (expected=OtrProcessingException.class)
    public void missingFormat() throws OtrProcessingException
    {
    	recording.setFormat(null);
    	TxtLinkListFactory.checkXmlStructure(recording);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingFormatType() throws OtrProcessingException
    {
    	recording.getFormat().setType(null);
    	TxtLinkListFactory.checkXmlStructure(recording);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingFormatCut() throws OtrProcessingException
    {
    	recording.getFormat().unsetCut();
    	TxtLinkListFactory.checkXmlStructure(recording);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingFormatKey() throws OtrProcessingException
    {
    	recording.getFormat().unsetOtrkey();
    	TxtLinkListFactory.checkXmlStructure(recording);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingFormatQuality() throws OtrProcessingException
    {
    	recording.getFormat().setQuality(null);
    	TxtLinkListFactory.checkXmlStructure(recording);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingFormatQualityType() throws OtrProcessingException
    {
    	recording.getFormat().getQuality().setType(null);
    	TxtLinkListFactory.checkXmlStructure(recording);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingLink() throws OtrProcessingException
    {
    	recording.setLink(null);
    	TxtLinkListFactory.checkXmlStructure(recording);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingLinkUrl() throws OtrProcessingException
    {
    	recording.getLink().setUrl(null);
    	TxtLinkListFactory.checkXmlStructure(recording);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingOtrId() throws OtrProcessingException
    {
    	recording.setOtrId(null);
    	TxtLinkListFactory.checkXmlStructure(recording);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingOtrIdKey() throws OtrProcessingException
    {
    	recording.getOtrId().setKey(null);
    	TxtLinkListFactory.checkXmlStructure(recording);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingCutlist() throws OtrProcessingException
    {
    	recording.getFormat().setCut(true);
    	TxtLinkListFactory.checkXmlStructure(recording);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingCutlistId() throws OtrProcessingException
    {
    	recording.getFormat().setCut(true);
    	
    	CutList cl = new CutList();
    	recording.setCutList(cl);
    	
    	TxtLinkListFactory.checkXmlStructure(recording);
    }
    
    @Test
    public void missingCutlistNoErrors() throws OtrProcessingException
    {
    	recording.getFormat().setCut(true);
    	
    	CutList cl = new CutList();
    	cl.setId(",yId");
    	recording.setCutList(cl);
    	
    	TxtLinkListFactory.checkXmlStructure(recording);
    }
 }