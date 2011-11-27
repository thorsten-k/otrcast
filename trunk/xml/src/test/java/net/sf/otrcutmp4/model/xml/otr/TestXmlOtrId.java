package net.sf.otrcutmp4.model.xml.otr;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlOtrId extends AbstractXmlOtrTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlOtrId.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"otrId.xml");
	}
    
    @Test
    public void testDownload() throws FileNotFoundException
    {
    	OtrId test = createOtrId(true);
    	OtrId ref = (OtrId)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), OtrId.class);
    	assertJaxbEquals(ref, test);
    }
 
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	OtrId xml = createOtrId(true);
    	JaxbUtil.debug2(this.getClass(),xml, nsPrefixMapper);
    	JaxbUtil.save(fXml, xml, nsPrefixMapper, true);
    }
    
    public static OtrId createOtrId(){return createOtrId(false);}
    public static OtrId createOtrId(boolean withChilds)
    {
    	OtrId xml = new OtrId();
    	xml.setId(1);
    	xml.setKey("myKey");
    	
    	if(withChilds)
    	{
    		xml.getQuality().add(TestXmlQuality.createQuality());
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlOtrId.initPrefixMapper();
		TestXmlOtrId.initFiles();	
		TestXmlOtrId test = new TestXmlOtrId();
		test.save();
    }
}