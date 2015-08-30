package de.kisner.otrcast.model.xml.series;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.cut.TestXmlVideoFiles;
import de.kisner.otrcast.model.xml.series.Video;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlVideo extends AbstractXmlSeriesTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlVideo.class);
	
	@BeforeClass
	public static void initFiles()
	{
        setXmlFile(dirSuffix,"video");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Video test = create(true);
    	Video ref =JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Video.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Video create(boolean withChilds)
    {
    	Video xml = new Video();
    
    	if(withChilds)
    	{
    		xml.setMovie(TestXmlMovie.create(false));
    		xml.setEpisode(TestXmlEpisode.create(false));
    		xml.setVideoFiles(TestXmlVideoFiles.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlVideo.initFiles();	
		TestXmlVideo test = new TestXmlVideo();
		test.save();
    }
}