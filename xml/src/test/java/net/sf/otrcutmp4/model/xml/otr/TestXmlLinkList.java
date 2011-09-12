package net.sf.otrcutmp4.model.xml.otr;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlLinkList extends AbstractXmlOtrTest
{
	static Log logger = LogFactory.getLog(TestXmlLinkList.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"linklist.xml");
	}
    
    @Test
    public void testDownload() throws FileNotFoundException
    {
    	Linklist test = createLinkList(true);
    	Linklist ref = (Linklist)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Linklist.class);
    	assertJaxbEquals(ref, test);
    }
 
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Linklist xml = createLinkList(true);
    	JaxbUtil.debug2(this.getClass(),xml, nsPrefixMapper);
    	JaxbUtil.save(fXml, xml, nsPrefixMapper, true);
    }
    
    public static Linklist createLinkList(){return createLinkList(false);}
    public static Linklist createLinkList(boolean withChilds)
    {
    	Linklist xml = new Linklist();
    	
    	if(withChilds)
    	{
    		xml.getDownload().add(TestXmlDownload.createDownload(false));
    	}
    	return xml;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlLinkList.initPrefixMapper();
		TestXmlLinkList.initFiles();	
		TestXmlLinkList test = new TestXmlLinkList();
		test.save();
    }
}