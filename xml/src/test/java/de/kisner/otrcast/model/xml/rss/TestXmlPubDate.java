package de.kisner.otrcast.model.xml.rss;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.rss.PubDate;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlPubDate extends AbstractXmlRssTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlPubDate.class);
	
	@BeforeClass
	public static void initFiles()
	{
        fXml = new File(getXmlDir(dirSuffix),PubDate.class.getSimpleName()+".xml");
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