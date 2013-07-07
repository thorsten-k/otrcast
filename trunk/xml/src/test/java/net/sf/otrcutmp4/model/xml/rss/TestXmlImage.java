package net.sf.otrcutmp4.model.xml.rss;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlImage extends AbstractXmlRssTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlImage.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Image.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Image actual = create(true);
    	Image expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Image.class);
    	assertJaxbEquals(expected, actual);
    }
     
    public static Image create(boolean withChilds)
    {
    	Image xml = new Image();
    	
    	if(withChilds)
    	{
    		xml.setUrl(TestXmlUrl.create());
    		xml.setTitle(TestXmlTitle.create());
    		xml.setLink(TestXmlLink.create());
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlImage.initFiles();	
		TestXmlImage test = new TestXmlImage();
		test.save();
    }
}