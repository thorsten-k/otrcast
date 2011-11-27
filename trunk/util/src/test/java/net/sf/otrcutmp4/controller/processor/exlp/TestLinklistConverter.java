package net.sf.otrcutmp4.controller.processor.exlp;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.exlp.util.io.StringIO;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.model.xml.otr.Linklist;
import net.sf.otrcutmp4.test.AbstractUtilTest;
import net.sf.otrcutmp4.test.OtrUtilTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLinklistConverter extends AbstractUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestLinklistConverter.class);
	
	private static File fXml;
	private static String sourceLinklist;
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File("src/test/resources/xml","linklist.xml");
		sourceLinklist = StringIO.loadTxt("src/test/resources/data/linklist.txt");
	}
    
    @Test
    public void testAclContainer() throws FileNotFoundException
    {
    	LinklistConverter llc = new LinklistConverter();
    	Linklist test = llc.convert(sourceLinklist);
    	Linklist ref = (Linklist)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Linklist.class);
    	assertJaxbEquals(ref, test);
    }
 
    public void save()
    {
    	LinklistConverter llc = new LinklistConverter();
    	Linklist xml = llc.convert(sourceLinklist);
    	JaxbUtil.debug(this.getClass(),xml);
    	JaxbUtil.save(fXml, xml, nsPrefixMapper, true);
    }
	
	public static void main(String[] args) throws ExlpConfigurationException
    {
		OtrUtilTstBootstrap.init();		
			
		TestLinklistConverter.initPrefixMapper();
		TestLinklistConverter.initFiles();
	
		TestLinklistConverter test = new TestLinklistConverter();
		test.save();
    }
}