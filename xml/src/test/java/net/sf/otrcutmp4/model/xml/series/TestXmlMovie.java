package net.sf.otrcutmp4.model.xml.series;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.model.xml.mc.TestXmlCover;
import net.sf.otrcutmp4.model.xml.mc.TestXmlStorage;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlMovie extends AbstractXmlSeriesTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlMovie.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"movie.xml");
	}
    
    @Test
    public void testAclContainer() throws FileNotFoundException
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
    		xml.setCover(TestXmlCover.create(false));
    		xml.setStorage(TestXmlStorage.create());
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