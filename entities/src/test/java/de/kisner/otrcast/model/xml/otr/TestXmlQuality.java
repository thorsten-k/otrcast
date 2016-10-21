package de.kisner.otrcast.model.xml.otr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlQuality extends AbstractXmlOtrTest<Quality>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlQuality.class);
	
	public TestXmlQuality(){super(Quality.class);}
	public static Quality create(boolean withChildren){return (new TestXmlQuality()).build(withChildren);}
    
    public Quality build(boolean withChilds)
    {
    	Quality xml = new Quality();
    	xml.setId(1);
    	xml.setType("myType");
    	xml.setImage("myImage");
    	xml.setName("myName");
    	
    	if(withChilds)
    	{
    		xml.getRecording().add(TestXmlRecording.create(false));
    		xml.getRecording().add(TestXmlRecording.create(false));
    	}
    	
    	return xml;
    }
    
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();		
		TestXmlQuality test = new TestXmlQuality();
		test.saveReferenceXml();
    }
}