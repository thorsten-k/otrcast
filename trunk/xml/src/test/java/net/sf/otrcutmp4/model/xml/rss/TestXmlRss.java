package net.sf.otrcutmp4.model.xml.rss;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlRss extends AbstractXmlRssTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlRss.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Rss.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Rss actual = create(true);
    	Rss expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Rss.class);
    	assertJaxbEquals(expected, actual);
    }
     
    public static Rss create(boolean withChilds)
    {
    	Rss xml = new Rss();
    	xml.setVersion("myVersion");
    	
    	if(withChilds)
    	{
    		xml.setChannel(TestXmlChannel.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlRss.initFiles();	
		TestXmlRss test = new TestXmlRss();
		test.save();
    }
}