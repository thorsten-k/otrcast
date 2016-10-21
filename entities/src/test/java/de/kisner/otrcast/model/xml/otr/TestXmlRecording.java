package de.kisner.otrcast.model.xml.otr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.cut.TestXmlCutList;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlRecording extends AbstractXmlOtrTest<Recording>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlRecording.class);
	
	public TestXmlRecording(){super(Recording.class);}
	public static Recording create(boolean withChildren){return (new TestXmlRecording()).build(withChildren);}
    
    public Recording build(boolean withChilds)
    {
    	Recording xml = new Recording();
    	xml.setId(1);
    	
    	if(withChilds)
    	{
    		xml.setOtrId(TestXmlOtrId.create(false));
    		xml.setFormat(TestXmlFormat.create(false));
    		xml.setCutList(TestXmlCutList.create(false));
    		xml.setLink(TestXmlLink.create(false));
    	}
    	
    	return xml;
    }
    
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		TestXmlRecording test = new TestXmlRecording();
		test.saveReferenceXml();
    }
}