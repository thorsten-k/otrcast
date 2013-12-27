package net.sf.otrcutmp4.model.xml.tvdb;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.model.xml.series.AbstractXmlSeriesTest;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlMeta extends AbstractXmlSeriesTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlMeta.class);
	
	@BeforeClass
	public static void initFiles()
	{
        setXmlFile(dirSuffix,Meta.class);
	}
    
    @Test
    public void jaxb() throws FileNotFoundException
    {
        Meta test = create(true);
        Meta ref =JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Meta.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Meta create(boolean withChilds)
    {
        Meta xml = new Meta();
        xml.setId(123);
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlMeta.initFiles();
		TestXmlMeta test = new TestXmlMeta();
		test.save();
    }
}