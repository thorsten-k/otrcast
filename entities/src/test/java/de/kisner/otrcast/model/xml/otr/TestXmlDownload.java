package de.kisner.otrcast.model.xml.otr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlDownload extends AbstractXmlOtrTest<Download>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDownload.class);
	
	public TestXmlDownload(){super(Download.class);}
	public static Download create(boolean withChildren){return (new TestXmlDownload()).build(withChildren);}
    
    public Download build(boolean withChilds)
    {
    	Download xml = new Download();
    	xml.setId(1);
    	xml.setType("myType");
    	
    	if(withChilds)
    	{
    		xml.getOtrId().add(TestXmlOtrId.create(false));
    		xml.getOtrId().add(TestXmlOtrId.create(false));
    		xml.getRecording().add(TestXmlRecording.create(false));
    		xml.getRecording().add(TestXmlRecording.create(false));
    	}
    	
    	return xml;
    }
    
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();	
		TestXmlDownload test = new TestXmlDownload();
		test.saveReferenceXml();
    }
}