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
	private TxtLinkListFactory txtLinkFactory;
	
	@Before
	public void init()
	{
		TestTxtLinkListFactory factory = TestTxtLinkListFactory.factory();
		recording = factory.createXml();
		txtLinkFactory = new TxtLinkListFactory();
	}
	
	@Test
	public void noXmlErrors() throws OtrProcessingException
    {
		txtLinkFactory.checkXmlStructure(recording);
    }
	
    @Test (expected=OtrProcessingException.class)
    public void missingFormat() throws OtrProcessingException
    {
    	recording.setFormat(null);
    	txtLinkFactory.checkXmlStructure(recording);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingFormatType() throws OtrProcessingException
    {
    	recording.getFormat().setType(null);
    	txtLinkFactory.checkXmlStructure(recording);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingFormatCut() throws OtrProcessingException
    {
    	recording.getFormat().unsetCut();
    	txtLinkFactory.checkXmlStructure(recording);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingFormatKey() throws OtrProcessingException
    {
    	recording.getFormat().unsetOtrkey();
    	txtLinkFactory.checkXmlStructure(recording);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingFormatQuality() throws OtrProcessingException
    {
    	recording.getFormat().setQuality(null);
    	txtLinkFactory.checkXmlStructure(recording);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingFormatQualityType() throws OtrProcessingException
    {
    	recording.getFormat().getQuality().setType(null);
    	txtLinkFactory.checkXmlStructure(recording);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingLink() throws OtrProcessingException
    {
    	recording.setLink(null);
    	txtLinkFactory.checkXmlStructure(recording);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingLinkUrl() throws OtrProcessingException
    {
    	recording.getLink().setUrl(null);
    	txtLinkFactory.checkXmlStructure(recording);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingOtrId() throws OtrProcessingException
    {
    	recording.setOtrId(null);
    	txtLinkFactory.checkXmlStructure(recording);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingOtrIdKey() throws OtrProcessingException
    {
    	recording.getOtrId().setKey(null);
    	txtLinkFactory.checkXmlStructure(recording);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingCutlist() throws OtrProcessingException
    {
    	recording.getFormat().setCut(true);
    	recording.setCutList(null);
    	txtLinkFactory.checkXmlStructure(recording);
    }
    
    @Test (expected=OtrProcessingException.class)
    public void missingCutlistId() throws OtrProcessingException
    {
    	recording.getFormat().setCut(true);
    	
    	CutList cl = new CutList();
    	recording.setCutList(cl);
    	
    	txtLinkFactory.checkXmlStructure(recording);
    }
    
    @Test
    public void missingCutlistNoErrors() throws OtrProcessingException
    {
    	recording.getFormat().setCut(true);
    	
    	CutList cl = new CutList();
    	cl.setId(",yId");
    	recording.setCutList(cl);
    	
    	txtLinkFactory.checkXmlStructure(recording);
    }
 }