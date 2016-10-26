package de.kisner.otrcast.model.xml.itunes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestXmlSummary extends AbstractXmlItunesTest<Summary>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSummary.class);
	
	public TestXmlSummary(){super(Summary.class);}
	public static Summary create(boolean withChildren){return (new TestXmlSummary()).build(withChildren);}
     
    public Summary build(boolean withChilds)
    {
    	Summary xml = new Summary();
    	xml.setValue("mySummary");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OtrXmlTestBootstrap.init();
		TestXmlSummary test = new TestXmlSummary();
		test.saveReferenceXml();
    }
}