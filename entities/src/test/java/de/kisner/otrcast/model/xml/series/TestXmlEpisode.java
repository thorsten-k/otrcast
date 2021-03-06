package de.kisner.otrcast.model.xml.series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.mc.TestXmlImage;
import de.kisner.otrcast.model.xml.mc.TestXmlFile;
import de.kisner.otrcast.model.xml.tvdb.TestXmlSync;
import de.kisner.otrcast.model.xml.video.tv.Episode;
import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestXmlEpisode extends AbstractXmlSeriesTest<Episode>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlEpisode.class);
	
	public TestXmlEpisode(){super(Episode.class);}
	public static Episode create(boolean withChildren){return (new TestXmlEpisode()).build(withChildren);}
     
    public Episode build(boolean withChilds)
    {
    	Episode xml = new Episode();
    	xml.setId(1);
    	xml.setNr(1);
    	xml.setName("Test Name");
    	
    	if(withChilds)
    	{
    		xml.setSeason(TestXmlSeason.create(false));
    		xml.setImage(TestXmlImage.create(false));
    		xml.setFile(TestXmlFile.create(false));
    		xml.setSync(TestXmlSync.create(false));
    	}
    	
    	return xml;
    }
	public static void main(String[] args)
    {
		OtrXmlTestBootstrap.init();
		TestXmlEpisode test = new TestXmlEpisode();
		test.saveReferenceXml();
    }
}