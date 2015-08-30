package de.kisner.otrcast.model.xml.series;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.mc.TestXmlImage;
import de.kisner.otrcast.model.xml.mc.TestXmlStorage;
import de.kisner.otrcast.model.xml.series.Episode;
import de.kisner.otrcast.model.xml.tvdb.TestXmlSync;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlEpisode extends AbstractXmlSeriesTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlEpisode.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,"episode");}
    
    @Test
    public void testEpisode() throws FileNotFoundException
    {
    	Episode test = create(true);
    	Episode ref = (Episode)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Episode.class);
    	assertJaxbEquals(ref, test);
    }
     
    public static Episode create(boolean withChilds)
    {
    	Episode xml = new Episode();
    	xml.setId(1);
    	xml.setNr(1);
    	xml.setName("Test Name");
    	
    	if(withChilds)
    	{
    		xml.setSeason(TestXmlSeason.create(false));
    		xml.setImage(TestXmlImage.create(false));
    		xml.setStorage(TestXmlStorage.create());
    		xml.setSync(TestXmlSync.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlEpisode.initFiles();	
		TestXmlEpisode test = new TestXmlEpisode();
		test.save();
    }
}