package de.kisner.otrcast.model.xml.otr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestXmlOtrId extends AbstractXmlOtrTest<OtrId>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlOtrId.class);
	
	public TestXmlOtrId(){super(OtrId.class);}
	public static OtrId create(boolean withChildren){return (new TestXmlOtrId()).build(withChildren);}
	
    public OtrId build(boolean withChilds)
    {
    	OtrId xml = new OtrId();
    	xml.setId(1);
    	xml.setKey("myKey");
    	xml.setOtrCl("1234");
    	
    	if(withChilds)
    	{
    		xml.getQuality().add(TestXmlQuality.create(false));
    		xml.getQuality().add(TestXmlQuality.create(false));
    		xml.setFormat(TestXmlFormat.create(false));
    		xml.setTv(TestXmlTv.create(false));
    	}
    	
    	return xml;
    }
    
	public static void main(String[] args)
    {
		OtrXmlTestBootstrap.init();	
		TestXmlOtrId test = new TestXmlOtrId();
		test.saveReferenceXml();
    }
}