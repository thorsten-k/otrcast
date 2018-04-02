package de.kisner.otrcast.model.xml.series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.video.tv.Movies;
import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestXmlMovies extends AbstractXmlSeriesTest<Movies>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlMovies.class);
	
	public TestXmlMovies(){super(Movies.class);}
	public static Movies create(boolean withChildren){return (new TestXmlMovies()).build(withChildren);}
    
    public Movies build(boolean withChilds)
    {
    	Movies xml = new Movies();
    	
    	if(withChilds)
    	{
    		xml.getMovie().add(TestXmlMovie.create(false));
    		xml.getMovie().add(TestXmlMovie.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OtrXmlTestBootstrap.init();
		TestXmlMovies test = new TestXmlMovies();
		test.saveReferenceXml();
    }
}