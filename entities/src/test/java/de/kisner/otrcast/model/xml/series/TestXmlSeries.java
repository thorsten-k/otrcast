package de.kisner.otrcast.model.xml.series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.tvdb.TestXmlSync;
import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestXmlSeries extends AbstractXmlSeriesTest<Series>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSeries.class);
	
	public TestXmlSeries(){super(Series.class);}
	public static Series create(boolean withChildren){return (new TestXmlSeries()).build(withChildren);}
    
    public Series build(boolean withChilds)
    {
    	Series xml = new Series();
    	xml.setName("Test Name");
    	xml.setKey("KEY");
    	
    	if(withChilds)
    	{
    		xml.getSeason().add(TestXmlSeason.create(false));
    		xml.getSeason().add(TestXmlSeason.create(false));
            xml.setSync(TestXmlSync.create(false));
    	}
    	
    	return xml;
    }

	public static void main(String[] args)
    {
		OtrXmlTestBootstrap.init();
		TestXmlSeries test = new TestXmlSeries();
		test.saveReferenceXml();
    }
}