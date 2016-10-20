package de.kisner.otrcast.model.xml.series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.mc.TestXmlImage;
import de.kisner.otrcast.model.xml.tvdb.TestXmlBanners;
import de.kisner.otrcast.model.xml.tvdb.TestXmlSync;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlSeason extends AbstractXmlSeriesTest<Season>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSeason.class);
	
	public TestXmlSeason(){super(Season.class);}
	public static Season create(boolean withChildren){return (new TestXmlSeason()).build(withChildren);}
    
    public Season build(boolean withChilds)
    {
    	Season xml = new Season();
    	xml.setId(123);
    	xml.setNr(1);
    	xml.setName("myName");
    	xml.setShowName(true);
    	xml.setShowNr(true);
    	
    	if(withChilds)
    	{
    		xml.setSeries(TestXmlSeries.create(false));
    		xml.setImage(TestXmlImage.create(false));
    		xml.getEpisode().add(TestXmlEpisode.create(false));
    		xml.getEpisode().add(TestXmlEpisode.create(false));
    		xml.setSync(TestXmlSync.create(false));
    		xml.setBanners(TestXmlBanners.create(false));
    	}
    	
    	return xml;
    }

	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		TestXmlSeason test = new TestXmlSeason();
		test.saveReferenceXml();
    }
}