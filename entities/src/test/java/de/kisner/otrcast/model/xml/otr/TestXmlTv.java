package de.kisner.otrcast.model.xml.otr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestXmlTv extends AbstractXmlOtrTest<Tv>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlOtrId.class);
	
	public TestXmlTv(){super(Tv.class);}
	public static Tv create(boolean withChildren){return (new TestXmlTv()).build(withChildren);}
    
    public Tv build(boolean withChilds)
    {
    	Tv xml = new Tv();
    	xml.setChannel("myChannel");
    	xml.setDuration(123);
    	xml.setName("myName");
    	xml.setAirtime(getDefaultXmlDate());
    	return xml;
    }
    
	public static void main(String[] args)
    {
		OtrXmlTestBootstrap.init();	
		TestXmlTv test = new TestXmlTv();
		test.saveReferenceXml();
    }
}