package net.sf.otrcutmp4.model.xml.otr;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlQuality extends AbstractXmlOtrTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlQuality.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"quality.xml");
	}
    
    @Test
    public void testDownload() throws FileNotFoundException
    {
    	Quality test = createQuality(true);
    	Quality ref = (Quality)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Quality.class);
    	assertJaxbEquals(ref, test);
    }
 
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Quality xml = createQuality(true);
    	JaxbUtil.debug2(this.getClass(),xml, nsPrefixMapper);
    	JaxbUtil.save(fXml, xml, nsPrefixMapper, true);
    }
    
    public static Quality createQuality(){return createQuality(false);}
    public static Quality createQuality(boolean withChilds)
    {
    	Quality xml = new Quality();
    	xml.setId(1);
    	xml.setType("myType");
    	xml.setImage("myImage");
    	xml.setName("myName");
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlQuality.initPrefixMapper();
		TestXmlQuality.initFiles();	
		TestXmlQuality test = new TestXmlQuality();
		test.save();
    }
}