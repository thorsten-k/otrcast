package de.kisner.otrcast.model.xml.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlCopyright extends AbstractXmlRssTest<Copyright>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCopyright.class);
	
	public TestXmlCopyright(){super(Copyright.class);}
	public static Copyright create(boolean withChildren){return (new TestXmlCopyright()).build(withChildren);}
     
    @Override public Copyright build(boolean withChilds)
    {
    	Copyright xml = new Copyright();
    	xml.setValue("myCopyright");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		TestXmlCopyright test = new TestXmlCopyright();
		test.saveReferenceXml();
    }
}