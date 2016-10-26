package de.kisner.otrcast.model.xml.series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestXmlVideos extends AbstractXmlSeriesTest<Videos>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlVideos.class);
	
	public TestXmlVideos(){super(Videos.class);}
	public static Videos create(boolean withChildren){return (new TestXmlVideos()).build(withChildren);}
    
    public Videos build(boolean withChilds)
    {
    	Videos xml = new Videos();
    
    	if(withChilds)
    	{
    		xml.getVideo().add(TestXmlVideo.create(false));
    		xml.getVideo().add(TestXmlVideo.create(false));
    	}
    	
    	return xml;
    }

	public static void main(String[] args)
    {
		OtrXmlTestBootstrap.init();
		TestXmlVideos test = new TestXmlVideos();
		test.saveReferenceXml();
    }
}