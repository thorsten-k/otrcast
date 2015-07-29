package de.kisner.otrcast.model.xml.tvdb;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.tvdb.Banner;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

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
        xml.setId(123);
        xml.setUrl("myUrl");
        xml.setType("myType");
    	
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