package de.kisner.otrcast.model.xml.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlEnclosure extends AbstractXmlRssTest<Enclosure>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlEnclosure.class);
	
	public TestXmlEnclosure(){super(Enclosure.class);}
	public static Enclosure create(boolean withChildren){return (new TestXmlEnclosure()).build(withChildren);}
     
    public Enclosure build(boolean withChilds)
    {
    	Enclosure xml = new Enclosure();
    	xml.setUrl("myUrl");
    	xml.setType("myType");
    	xml.setLength(123);
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		TestXmlEnclosure test = new TestXmlEnclosure();
		test.saveReferenceXml();
    }
}