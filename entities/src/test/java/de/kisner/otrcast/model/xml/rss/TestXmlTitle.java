package de.kisner.otrcast.model.xml.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestXmlTitle extends AbstractXmlRssTest<Title>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTitle.class);
	
	public TestXmlTitle(){super(Title.class);}
	public static Title create(boolean withChildren){return (new TestXmlTitle()).build(withChildren);}
     
    public Title build(boolean withChilds)
    {
    	Title xml = new Title();
    	xml.setValue("myTitle");
    	return xml;
    }

	public static void main(String[] args)
    {
		OtrXmlTestBootstrap.init();
	
		TestXmlTitle test = new TestXmlTitle();
		test.saveReferenceXml();
    }
}