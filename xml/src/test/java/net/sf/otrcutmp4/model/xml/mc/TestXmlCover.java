package net.sf.otrcutmp4.model.xml.mc;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCover extends AbstractXmlMcTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCover.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Cover.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Cover actual = create(true);
    	Cover expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Cover.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Cover create(boolean withChilds)
    {
    	Cover xml = new Cover();
    	xml.setId(123);
    	xml.setType("png");
    	
    	if(withChilds)
    	{
    		xml.setData("myBinaryData".getBytes());
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
			
		TestXmlCover.initXml();	
		TestXmlCover.initFiles();
		TestXmlCover test = new TestXmlCover();
		test.save();
    }
}