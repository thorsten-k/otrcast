package net.sf.otrcutmp4.model.xml.rss;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlLanguage extends AbstractXmlRssTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlLanguage.class);
	
	@BeforeClass
	public static void initFiles()
	{
        fXml = new File(getXmlDir(dirSuffix),Language.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Language actual = create();
    	Language expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Language.class);
    	assertJaxbEquals(expected, actual);
    }
     
    public static Language create()
    {
    	Language xml = new Language();
    	xml.setValue("myLink");
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlLanguage.initFiles();	
		TestXmlLanguage test = new TestXmlLanguage();
		test.save();
    }
}