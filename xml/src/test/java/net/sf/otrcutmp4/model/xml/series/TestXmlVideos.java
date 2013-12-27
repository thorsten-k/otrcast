package net.sf.otrcutmp4.model.xml.series;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlVideos extends AbstractXmlSeriesTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlVideos.class);
	
	@BeforeClass
	public static void initFiles()
	{
        setXmlFile(dirSuffix,Videos.class);
	}
    
    @Test
    public void testAclContainer() throws FileNotFoundException
    {
    	Videos test = create(true);
    	Videos ref =JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Videos.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Videos create(boolean withChilds)
    {
    	Videos xml = new Videos();
    
    	if(withChilds)
    	{
    		xml.getVideo().add(TestXmlVideo.create(false));
    		xml.getVideo().add(TestXmlVideo.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlVideos.initFiles();	
		TestXmlVideos test = new TestXmlVideos();
		test.save();
    }
}