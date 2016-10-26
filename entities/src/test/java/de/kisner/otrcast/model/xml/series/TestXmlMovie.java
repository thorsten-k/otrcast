package de.kisner.otrcast.model.xml.series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.db.TestXmlDb;
import de.kisner.otrcast.model.xml.mc.TestXmlImage;
import de.kisner.otrcast.model.xml.mc.TestXmlStorage;
import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestXmlMovie extends AbstractXmlSeriesTest<Movie>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlMovie.class);
	
	public TestXmlMovie(){super(Movie.class);}
	public static Movie create(boolean withChildren){return (new TestXmlMovie()).build(withChildren);}
    
    public Movie build(boolean withChilds)
    {
    	Movie xml = new Movie();
    	xml.setId(123);
    	xml.setName("Test Name");
    	xml.setYear(2103);
    	
    	if(withChilds)
    	{
    		xml.setImage(TestXmlImage.create(false));
    		xml.setStorage(TestXmlStorage.create(false));
    		xml.setDb(TestXmlDb.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OtrXmlTestBootstrap.init();
		TestXmlMovie test = new TestXmlMovie();
		test.saveReferenceXml();
    }
}