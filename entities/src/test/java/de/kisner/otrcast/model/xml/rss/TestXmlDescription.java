package de.kisner.otrcast.model.xml.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestXmlDescription extends AbstractXmlRssTest<Description>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDescription.class);
	
	public TestXmlDescription(){super(Description.class);}
	public static Description create(boolean withChildren){return (new TestXmlDescription()).build(withChildren);}
     
    public Description build(boolean withChilds)
    {
    	Description xml = new Description();
    	xml.setValue("myDescription");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OtrXmlTestBootstrap.init();
		TestXmlDescription test = new TestXmlDescription();
		test.saveReferenceXml();
    }
}