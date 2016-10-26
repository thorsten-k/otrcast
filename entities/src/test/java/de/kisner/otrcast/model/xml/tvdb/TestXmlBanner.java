package de.kisner.otrcast.model.xml.tvdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestXmlBanner extends AbstractXmlTvDbTest<Banner>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlBanner.class);
	
	public TestXmlBanner(){super(Banner.class);}
	public static Banner create(boolean withChildren){return (new TestXmlBanner()).build(withChildren);}
    
    public Banner build(boolean withChilds)
    {
        Banner xml = new Banner();
        xml.setId(123);
        xml.setUrl("myUrl");
        xml.setType("myType");
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OtrXmlTestBootstrap.init();
		TestXmlBanner test = new TestXmlBanner();
		test.saveReferenceXml();
    }
}