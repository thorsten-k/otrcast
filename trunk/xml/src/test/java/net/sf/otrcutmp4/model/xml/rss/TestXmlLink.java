package net.sf.otrcutmp4.model.xml.rss;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlLink extends AbstractXmlRssTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlLink.class);
	
	@BeforeClass
	public static void initFiles()
	{
        fXml = new File(getXmlDir(dirSuffix),Link.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Link actual = create();
    	Link expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Link.class);
    	assertJaxbEquals(expected, actual);
    }
     
    public static Link create()
    {
    	Link xml = new Link();
    	xml.setValue("myLink");
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlLink.initFiles();	
		TestXmlLink test = new TestXmlLink();
		test.save();
    }
}