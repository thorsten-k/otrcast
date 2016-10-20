package de.kisner.otrcast.model.xml.cut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlCut extends AbstractXmlCutTest<Cut>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCut.class);
	
	public TestXmlCut(){super(Cut.class);}
	public static Cut create(boolean withChildren){return (new TestXmlCut()).build(withChildren);}
    
    public Cut build(boolean withChilds)
    {
    	Cut xml = new Cut();
    	xml.setDuration(12);
    	xml.setId("myId");
    	xml.setStart(32);
    	xml.setInclude(true);
    	
    	return xml;
    }
    
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		TestXmlCut test = new TestXmlCut();
		test.saveReferenceXml();
    }
}