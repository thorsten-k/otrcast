package de.kisner.otrcast.model.xml.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlLanguage extends AbstractXmlRssTest<Language>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlLanguage.class);
	
	public TestXmlLanguage(){super(Language.class);}
	public static Language create(boolean withChildren){return (new TestXmlLanguage()).build(withChildren);}
     
    public Language build(boolean withChilds)
    {
    	Language xml = new Language();
    	xml.setValue("myLink");
    	return xml;
    }
    
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();	
		TestXmlLanguage test = new TestXmlLanguage();
		test.saveReferenceXml();
    }
}