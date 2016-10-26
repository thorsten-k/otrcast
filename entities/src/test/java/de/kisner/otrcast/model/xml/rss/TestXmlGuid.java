package de.kisner.otrcast.model.xml.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestXmlGuid extends AbstractXmlRssTest<Guid>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlGuid.class);
	
	public TestXmlGuid(){super(Guid.class);}
	public static Guid create(boolean withChildren){return (new TestXmlGuid()).build(withChildren);}
     
    public Guid build(boolean withChilds)
    {
    	Guid xml = new Guid();
    	xml.setIsPermaLink(false);
    	xml.setValue("myGuid");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OtrXmlTestBootstrap.init();
		TestXmlGuid test = new TestXmlGuid();
		test.saveReferenceXml();
    }
}