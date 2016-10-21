package de.kisner.otrcast.model.xml.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlRss extends AbstractXmlRssTest<Rss>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlRss.class);
	
	public TestXmlRss(){super(Rss.class);}
	public static Rss create(boolean withChildren){return (new TestXmlRss()).build(withChildren);}
     
    public Rss build(boolean withChilds)
    {
    	Rss xml = new Rss();
    	xml.setVersion("myVersion");
    	
    	if(withChilds)
    	{
    		xml.setChannel(TestXmlChannel.create(false));
    	}
    	
    	return xml;
    }

	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();

		TestXmlRss test = new TestXmlRss();
		test.saveReferenceXml();
    }
}