package net.sf.otrcutmp4.model.xml.itunes;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSummary extends AbstractXmlItunesTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSummary.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Summary.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Summary actual = create();
    	Summary expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Summary.class);
    	assertJaxbEquals(expected, actual);
    }
     
    public static Summary create()
    {
    	Summary xml = new Summary();
    	xml.setValue("mySummary");
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlSummary.initFiles();	
		TestXmlSummary test = new TestXmlSummary();
		test.save();
    }
}