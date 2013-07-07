package net.sf.otrcutmp4.model.xml.rss;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlDescription extends AbstractXmlRssTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDescription.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Description.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Description actual = create();
    	Description expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Description.class);
    	assertJaxbEquals(expected, actual);
    }
     
    public static Description create()
    {
    	Description xml = new Description();
    	xml.setValue("myDescription");
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlDescription.initFiles();	
		TestXmlDescription test = new TestXmlDescription();
		test.save();
    }
}