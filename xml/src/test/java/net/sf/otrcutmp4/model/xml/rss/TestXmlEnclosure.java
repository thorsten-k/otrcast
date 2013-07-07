package net.sf.otrcutmp4.model.xml.rss;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlEnclosure extends AbstractXmlRssTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlEnclosure.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Enclosure.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Enclosure actual = create();
    	Enclosure expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Enclosure.class);
    	assertJaxbEquals(expected, actual);
    }
     
    public static Enclosure create()
    {
    	Enclosure xml = new Enclosure();
    	xml.setUrl("myUrl");
    	xml.setType("myType");
    	xml.setLength(123);
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlEnclosure.initFiles();	
		TestXmlEnclosure test = new TestXmlEnclosure();
		test.save();
    }
}