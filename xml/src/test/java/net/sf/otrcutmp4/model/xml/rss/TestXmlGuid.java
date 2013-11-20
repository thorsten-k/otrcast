package net.sf.otrcutmp4.model.xml.rss;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlGuid extends AbstractXmlRssTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlGuid.class);
	
	@BeforeClass
	public static void initFiles()
	{
        fXml = new File(getXmlDir(dirSuffix),Guid.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Guid actual = create();
    	Guid expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Guid.class);
    	assertJaxbEquals(expected, actual);
    }
     
    public static Guid create()
    {
    	Guid xml = new Guid();
    	xml.setIsPermaLink(false);
    	xml.setValue("myGuid");
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlGuid.initFiles();	
		TestXmlGuid test = new TestXmlGuid();
		test.save();
    }
}