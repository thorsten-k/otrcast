package de.kisner.otrcast.model.xml.rss;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.rss.Title;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlTitle extends AbstractXmlRssTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTitle.class);
	
	@BeforeClass
	public static void initFiles()
	{
        fXml = new File(getXmlDir(dirSuffix),Title.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Title actual = create();
    	Title expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Title.class);
    	assertJaxbEquals(expected, actual);
    }
     
    public static Title create()
    {
    	Title xml = new Title();
    	xml.setValue("myTitle");
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlTitle.initFiles();	
		TestXmlTitle test = new TestXmlTitle();
		test.save();
    }
}