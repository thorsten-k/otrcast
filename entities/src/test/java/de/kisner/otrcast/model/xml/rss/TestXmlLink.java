package de.kisner.otrcast.model.xml.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlLink extends AbstractXmlRssTest<Link>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlLink.class);
	
	public TestXmlLink(){super(Link.class);}
	public static Link create(boolean withChildren){return (new TestXmlLink()).build(withChildren);}
     
    public Link build(boolean withChilds)
    {
    	Link xml = new Link();
    	xml.setValue("myLink");
    	return xml;
    }

	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		TestXmlLink test = new TestXmlLink();
		test.saveReferenceXml();
    }
}