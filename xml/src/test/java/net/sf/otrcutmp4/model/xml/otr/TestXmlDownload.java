package net.sf.otrcutmp4.model.xml.otr;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlDownload extends AbstractXmlOtrTest
{
	static Log logger = LogFactory.getLog(TestXmlDownload.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"download.xml");
	}
    
    @Test
    public void testDownload() throws FileNotFoundException
    {
    	Download test = createDownload(true);
    	Download ref = (Download)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Download.class);
    	assertJaxbEquals(ref, test);
    }
 
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Download xml = createDownload(true);
    	JaxbUtil.debug2(this.getClass(),xml, nsPrefixMapper);
    	JaxbUtil.save(fXml, xml, nsPrefixMapper, true);
    }
    
    public static Download createDownload(){return createDownload(false);}
    public static Download createDownload(boolean withChilds)
    {
    	Download xml = new Download();
    	xml.setId(1);
    	xml.setType("myType");
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlDownload.initPrefixMapper();
		TestXmlDownload.initFiles();	
		TestXmlDownload test = new TestXmlDownload();
		test.save();
    }
}