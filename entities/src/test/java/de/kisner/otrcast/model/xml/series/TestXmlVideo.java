package de.kisner.otrcast.model.xml.series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.cut.TestXmlVideoFiles;
import de.kisner.otrcast.model.xml.video.TestXmlTag;
import de.kisner.otrcast.model.xml.video.Video;
import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestXmlVideo extends AbstractXmlSeriesTest<Video>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlVideo.class);
	
	public TestXmlVideo(){super(Video.class);}
	public static Video create(boolean withChildren){return (new TestXmlVideo()).build(withChildren);}
    
    public Video build(boolean withChilds)
    {
	    	Video xml = new Video();
	    
	    	if(withChilds)
	    	{
	    		xml.setMovie(TestXmlMovie.create(false));
	    		xml.setEpisode(TestXmlEpisode.create(false));
	    		xml.setVideoFiles(TestXmlVideoFiles.create(false));
	    		xml.setTag(TestXmlTag.create(false));
	    	}
	    	
	    	return xml;
    }
	
	public static void main(String[] args)
    {
		OtrXmlTestBootstrap.init();
		TestXmlVideo test = new TestXmlVideo();
		test.saveReferenceXml();
    }
}