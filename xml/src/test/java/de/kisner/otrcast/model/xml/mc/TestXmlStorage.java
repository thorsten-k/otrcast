package de.kisner.otrcast.model.xml.mc;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.mc.Storage;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlStorage extends AbstractXmlMcTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlStorage.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Storage.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Storage actual = create();
    	Storage expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Storage.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Storage create()
    {
    	Storage xml = new Storage();
    	xml.setId(123);
    	xml.setHash("myHash");
    	xml.setSize(123);
    	xml.setName("myName");
    	xml.setLastModified(getDefaultXmlDate());
    	
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		DateUtil.ignoreTimeZone=true;	
		
		TestXmlStorage.initXml();	
		TestXmlStorage.initFiles();
		TestXmlStorage test = new TestXmlStorage();
		test.save();
    }
}