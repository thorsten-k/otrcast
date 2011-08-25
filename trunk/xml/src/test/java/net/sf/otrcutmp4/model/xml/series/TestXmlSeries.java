package net.sf.otrcutmp4.model.xml.series;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlSeries extends AbstractXmlSeriesTest
{
	static Log logger = LogFactory.getLog(TestXmlSeries.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"series.xml");
	}
    
    @Test
    public void testAclContainer() throws FileNotFoundException
    {
    	Series test = create(true);
    	Series ref = (Series)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Series.class);
    	assertJaxbEquals(ref, test);
    }
 
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Series xml = create(true);
    	JaxbUtil.debug2(this.getClass(),xml, nsPrefixMapper);
    	JaxbUtil.save(fXml, xml, nsPrefixMapper, true);
    }
    
    public static Series create(){return create(false);}
    public static Series create(boolean withChilds)
    {
    	Series xml = new Series();
    	xml.setName("Test Name");
    	
    	if(withChilds)
    	{
    		xml.getSeason().add(TestXmlSeason.create());
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlSeries.initPrefixMapper();
		TestXmlSeries.initFiles();	
		TestXmlSeries test = new TestXmlSeries();
		test.save();
    }
}