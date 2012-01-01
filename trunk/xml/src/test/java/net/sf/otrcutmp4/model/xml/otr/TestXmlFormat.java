package net.sf.otrcutmp4.model.xml.otr;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlFormat extends AbstractXmlOtrTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFormat.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"format.xml");
	}
    
    @Test
    public void testDownload() throws FileNotFoundException
    {
    	Format test = create();
    	Format ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Format.class);
    	assertJaxbEquals(ref, test);
    }
    
    private static Format create(){return create(true);}
    public static Format create(boolean withChilds)
    {
    	Format xml = new Format();
    	xml.setId(1);
    	xml.setCut(true);
    	xml.setImage("myImage");
    	xml.setName("myName");
    	xml.setOtrkey(true);
    	xml.setType("myType");
    	
    	if(withChilds)
    	{
    		xml.setQuality(TestXmlQuality.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
			
		TestXmlFormat.initFiles();	
		TestXmlFormat test = new TestXmlFormat();
		test.save();
    }
}