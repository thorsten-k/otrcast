package de.kisner.otrcast.model.xml.tvdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestXmlSync extends AbstractXmlTvDbTest<Sync>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSync.class);
	
	public TestXmlSync(){super(Sync.class);}
	public static Sync create(boolean withChildren){return (new TestXmlSync()).build(withChildren);}
    
    public Sync build(boolean withChilds)
    {
        Sync xml = new Sync();
        xml.setId(123);

        if(withChilds)
        {
            xml.setBanners(TestXmlBanners.create(false));
        }

        return xml;
    }
	
	public static void main(String[] args)
    {
		OtrXmlTestBootstrap.init();
		TestXmlSync test = new TestXmlSync();
		test.saveReferenceXml();
    }
}