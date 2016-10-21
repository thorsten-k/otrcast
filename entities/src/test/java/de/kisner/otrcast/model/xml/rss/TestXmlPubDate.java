package de.kisner.otrcast.model.xml.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlPubDate extends AbstractXmlRssTest<PubDate>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlPubDate.class);
	
	public TestXmlPubDate(){super(PubDate.class);}
	public static PubDate create(boolean withChildren){return (new TestXmlPubDate()).build(withChildren);}
     
    public PubDate build(boolean withChilds)
    {
    	PubDate xml = new PubDate();
    	xml.setValue("myPubDate");
    	return xml;
    }

	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		TestXmlPubDate test = new TestXmlPubDate();
		test.saveReferenceXml();
    }
}