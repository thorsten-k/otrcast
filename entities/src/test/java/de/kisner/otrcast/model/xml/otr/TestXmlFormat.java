package de.kisner.otrcast.model.xml.otr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestXmlFormat extends AbstractXmlOtrTest<Format>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFormat.class);
	
	public TestXmlFormat(){super(Format.class);}
	public static Format create(boolean withChildren){return (new TestXmlFormat()).build(withChildren);}
    
    public Format build(boolean withChilds)
    {
    	Format xml = new Format();
    	xml.setId(1);
    	xml.setCut(true);
    	xml.setImage("myImage");
    	xml.setName("myName");
    	xml.setOtrkey(true);
    	xml.setType("myType");
    	xml.setAc3(true);
    	
    	if(withChilds)
    	{
    		xml.setQuality(TestXmlQuality.create(false));
    	}
    	
    	return xml;
    }
    
	public static void main(String[] args)
    {
		OtrXmlTestBootstrap.init();
		TestXmlFormat test = new TestXmlFormat();
		test.saveReferenceXml();
    }
}