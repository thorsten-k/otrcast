package net.sf.otrcutmp4.model.xml.rss;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlPubDate extends AbstractXmlRssTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlPubDate.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,PubDate.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	PubDate actual = create();
    	PubDate expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), PubDate.class);
    	assertJaxbEquals(expected, actual);
    }
     
    public static PubDate create()
    {
    	PubDate xml = new PubDate();
    	xml.setValue("myPubDate");
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlPubDate.initFiles();	
		TestXmlPubDate test = new TestXmlPubDate();
		test.save();
    }
}