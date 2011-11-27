package net.sf.otrcutmp4.model.xml.series;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

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
 
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Season xml = create(true);
    	JaxbUtil.debug2(this.getClass(),xml, nsPrefixMapper);
    	JaxbUtil.save(fXml, xml, nsPrefixMapper, true);
    }
    
    public static Season create(){return create(false);}
    public static Season create(boolean withChilds)
    {
    	Season xml = new Season();
    	xml.setNr(1);
    	
    	if(withChilds)
    	{
    		xml.getEpisode().add(TestXmlEpisode.createEpisode());
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlSeason.initPrefixMapper();
		TestXmlSeason.initFiles();	
		TestXmlSeason test = new TestXmlSeason();
		test.save();
    }
}