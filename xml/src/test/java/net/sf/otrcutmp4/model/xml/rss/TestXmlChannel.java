package net.sf.otrcutmp4.model.xml.rss;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlChannel extends AbstractXmlRssTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlChannel.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Channel.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Channel actual = create(true);
    	Channel expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Channel.class);
    	assertJaxbEquals(expected, actual);
    }
     
    public static Channel create(boolean withChilds)
    {
    	Channel xml = new Channel();
    	
    	if(withChilds)
    	{
    		xml.setTitle(TestXmlTitle.create());
    		xml.setLink(TestXmlLink.create());
    		xml.setDescription(TestXmlDescription.create());
    		xml.setLanguage(TestXmlLanguage.create());
    		xml.setCopyright(TestXmlCopyright.create());
    		xml.setPubDate(TestXmlPubDate.create());
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlChannel.initFiles();	
		TestXmlChannel test = new TestXmlChannel();
		test.save();
    }
}