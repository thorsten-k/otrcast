package de.kisner.otrcast.model.xml.otr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.cut.TestXmlVideoFile;
import de.kisner.otrcast.model.xml.mc.TestXmlImage;
import de.kisner.otrcast.model.xml.series.TestXmlEpisode;
import de.kisner.otrcast.model.xml.series.TestXmlMovie;
import de.kisner.otrcast.model.xml.series.TestXmlSeason;
import de.kisner.otrcast.model.xml.series.TestXmlSeries;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlQuery extends AbstractXmlOtrTest<Query>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlQuery.class);
	
	public TestXmlQuery(){super(Query.class);}
	public static Query create(boolean withChildren){return (new TestXmlQuery()).build(withChildren);}
    
    public Query build(boolean withChilds)
    {
    	Query xml = new Query();    	
    	if(withChilds)
    	{
    		xml.setVideoFile(TestXmlVideoFile.create(false));
    		xml.setSeries(TestXmlSeries.create(false));
    		xml.setSeason(TestXmlSeason.create(false));
    		xml.setEpisode(TestXmlEpisode.create(false));
    		xml.setImage(TestXmlImage.create(false));
    		xml.setMovie(TestXmlMovie.create(false));
    	}
    	
    	return xml;
    }
    
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		TestXmlQuery test = new TestXmlQuery();
		test.saveReferenceXml();
    }
}