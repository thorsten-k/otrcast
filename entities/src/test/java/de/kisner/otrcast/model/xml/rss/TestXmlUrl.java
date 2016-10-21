package de.kisner.otrcast.model.xml.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlUrl extends AbstractXmlRssTest<Url>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUrl.class);
	
	public TestXmlUrl(){super(Url.class);}
	public static Url create(boolean withChildren){return (new TestXmlUrl()).build(withChildren);}
     
    public Url build(boolean withChilds)
    {
    	Url xml = new Url();
    	xml.setValue("myUrl");
    	return xml;
    }

	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		TestXmlUrl test = new TestXmlUrl();
		test.saveReferenceXml();
    }
}