package de.kisner.otrcast.model.xml.tvdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.series.TestXmlSeason;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlBanners extends AbstractXmlTvDbTest<Banners>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlBanners.class);
	
	public TestXmlBanners(){super(Banners.class);}
	public static Banners create(boolean withChildren){return (new TestXmlBanners()).build(withChildren);}
    
    public Banners build(boolean withChilds)
    {
        Banners xml = new Banners();

    	if(withChilds)
        {
            xml.getBanner().add(TestXmlBanner.create(false));xml.getBanner().add(TestXmlBanner.create(false));         
            xml.getSeason().add(TestXmlSeason.create(false)); xml.getSeason().add(TestXmlSeason.create(false));
        }

    	return xml;
    }
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		TestXmlBanners test = new TestXmlBanners();
		test.saveReferenceXml();
    }
}