package net.sf.otrcutmp4.model.xml.cut;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlFileName extends AbstractXmlCutTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFileName.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"fileName.xml");
	}
    
    @Test
    public void testDownload() throws FileNotFoundException
    {
    	FileName actual = create();
    	FileName expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), FileName.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static FileName create(){return create(true);}
    public static FileName create(boolean withChilds)
    {
    	FileName xml = new FileName();
    	xml.setValue("myFile.mp4");
    	
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
			
		TestXmlFileName.initFiles();	
		TestXmlFileName test = new TestXmlFileName();
		test.save();
    }
}