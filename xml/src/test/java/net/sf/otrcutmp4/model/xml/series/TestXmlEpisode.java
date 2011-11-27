package net.sf.otrcutmp4.model.xml.series;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlEpisode extends AbstractXmlSeriesTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlEpisode.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"episode.xml");
	}
    
    @Test
    public void testEpisode() throws FileNotFoundException
    {
    	Episode test = createEpisode(true);
    	Episode ref = (Episode)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Episode.class);
    	assertJaxbEquals(ref, test);
    }
 
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Episode xml = createEpisode(true);
    	JaxbUtil.debug2(this.getClass(),xml, nsPrefixMapper);
    	JaxbUtil.save(fXml, xml, nsPrefixMapper, true);
    }
    
    public static Episode createEpisode(){return createEpisode(false);}
    public static Episode createEpisode(boolean withChilds)
    {
    	Episode xml = new Episode();
    	xml.setId(1);
    	xml.setNr(1);
    	xml.setName("Test Name");
    	
    	if(withChilds)
    	{
    		xml.setSeason(TestXmlSeason.create());
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlEpisode.initPrefixMapper();
		TestXmlEpisode.initFiles();	
		TestXmlEpisode test = new TestXmlEpisode();
		test.save();
    }
}