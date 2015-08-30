package de.kisner.otrcast.model.xml.rss;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.rss.Url;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlUrl extends AbstractXmlRssTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUrl.class);
	
	@BeforeClass
	public static void initFiles()
	{
        fXml = new File(getXmlDir(dirSuffix),Url.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Url actual = create();
    	Url expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Url.class);
    	assertJaxbEquals(expected, actual);
    }
     
    public static Url create()
    {
    	Url xml = new Url();
    	xml.setValue("myUrl");
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlUrl.initFiles();	
		TestXmlUrl test = new TestXmlUrl();
		test.save();
    }
}