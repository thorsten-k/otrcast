package de.kisner.otrcast.model.xml.otr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestXmlLink extends AbstractXmlOtrTest<Link>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlOtrId.class);
	
	public TestXmlLink(){super(Link.class);}
	public static Link create(boolean withChildren){return (new TestXmlLink()).build(withChildren);}
    
    public Link build(boolean withChilds)
    {
    	Link xml = new Link();
    	xml.setId(123l);
    	xml.setUrl("myUrl");
    	return xml;
    }
    
	public static void main(String[] args)
    {
		OtrXmlTestBootstrap.init();		
		TestXmlLink test = new TestXmlLink();
		test.saveReferenceXml();
    }
}