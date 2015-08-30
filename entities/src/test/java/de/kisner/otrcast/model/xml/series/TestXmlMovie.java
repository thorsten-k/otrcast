package de.kisner.otrcast.model.xml.series;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.db.TestXmlDb;
import de.kisner.otrcast.model.xml.mc.TestXmlImage;
import de.kisner.otrcast.model.xml.mc.TestXmlStorage;
import de.kisner.otrcast.model.xml.series.Movie;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlMovie extends AbstractXmlSeriesTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlMovie.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,"movie");}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Movie test = create(true);
    	Movie ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Movie.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Movie create(boolean withChilds)
    {
    	Movie xml = new Movie();
    	xml.setId(123);
    	xml.setName("Test Name");
    	xml.setYear(2103);
    	
    	if(withChilds)
    	{
    		xml.setImage(TestXmlImage.create(false));
    		xml.setStorage(TestXmlStorage.create());
    		xml.setDb(TestXmlDb.create());
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlMovie.initFiles();	
		TestXmlMovie test = new TestXmlMovie();
		test.save();
    }
}