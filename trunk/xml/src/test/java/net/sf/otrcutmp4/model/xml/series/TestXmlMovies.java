package net.sf.otrcutmp4.model.xml.series;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlMovies extends AbstractXmlSeriesTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlMovies.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Movies.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Movies test = create(true);
    	Movies ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Movies.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Movies create(boolean withChilds)
    {
    	Movies xml = new Movies();
    	
    	if(withChilds)
    	{
    		xml.getMovie().add(TestXmlMovie.create(false));
    		xml.getMovie().add(TestXmlMovie.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlMovies.initFiles();	
		TestXmlMovies test = new TestXmlMovies();
		test.save();
    }
}