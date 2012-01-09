package net.sf.otrcutmp4.model.xml.series;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSeason extends AbstractXmlSeriesTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSeason.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"season.xml");
	}
    
    @Test
    public void testAclContainer() throws FileNotFoundException
    {
    	Season test = create(true);
    	Season ref = (Season)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Season.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Season create(boolean withChilds)
    {
    	Season xml = new Season();
    	xml.setId(123);
    	xml.setNr(1);
    	
    	if(withChilds)
    	{
    		xml.getEpisode().add(TestXmlEpisode.create(false));
    	}
    	
    	return xml;
    }
	
    public void save() {save(create(true), fXml);}
    
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlSeason.initFiles();	
		TestXmlSeason test = new TestXmlSeason();
		test.save();
    }
}