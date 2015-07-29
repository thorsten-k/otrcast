package de.kisner.otrcast.model.xml.otr;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.otr.Format;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

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
    	Format test = create(true);
    	Format ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Format.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Format create(boolean withChilds)
    {
    	Format xml = new Format();
    	xml.setId(1);
    	xml.setCut(true);
    	xml.setImage("myImage");
    	xml.setName("myName");
    	xml.setOtrkey(true);
    	xml.setType("myType");
    	xml.setAc3(true);
    	
    	if(withChilds)
    	{
    		xml.setQuality(TestXmlQuality.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
			
		TestXmlFormat.initFiles();	
		TestXmlFormat test = new TestXmlFormat();
		test.save();
    }
}