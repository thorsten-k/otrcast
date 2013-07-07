package net.sf.otrcutmp4.model.xml.rss;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCopyright extends AbstractXmlRssTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCopyright.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Copyright.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Copyright actual = create();
    	Copyright expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Copyright.class);
    	assertJaxbEquals(expected, actual);
    }
     
    public static Copyright create()
    {
    	Copyright xml = new Copyright();
    	xml.setValue("myCopyright");
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlCopyright.initFiles();	
		TestXmlCopyright test = new TestXmlCopyright();
		test.save();
    }
}