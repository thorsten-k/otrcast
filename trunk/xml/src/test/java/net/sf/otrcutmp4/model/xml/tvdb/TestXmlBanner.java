package net.sf.otrcutmp4.model.xml.tvdb;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlBanner extends AbstractXmlTvDbTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlBanner.class);
	
	@BeforeClass
	public static void initFiles()
	{
        setXmlFile(dirSuffix,Banner.class);
	}
    
    @Test
    public void jaxb() throws FileNotFoundException
    {
        Banner actual = create(true);
        Banner expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Banner.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Banner create(boolean withChilds)
    {
        Banner xml = new Banner();
        xml.setUrl("myUrl");
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlBanner.initFiles();
		TestXmlBanner test = new TestXmlBanner();
		test.save();
    }
}