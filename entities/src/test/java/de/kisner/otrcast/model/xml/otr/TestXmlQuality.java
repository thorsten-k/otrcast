package de.kisner.otrcast.model.xml.otr;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.otr.Quality;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlQuality extends AbstractXmlOtrTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlQuality.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"quality.xml");
	}
    
    @Test
    public void testDownload() throws FileNotFoundException
    {
    	Quality test = create();
    	Quality ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Quality.class);
    	assertJaxbEquals(ref, test);
    }
    
    private static Quality create(){return create(true);}
    public static Quality create(boolean withChilds)
    {
    	Quality xml = new Quality();
    	xml.setId(1);
    	xml.setType("myType");
    	xml.setImage("myImage");
    	xml.setName("myName");
    	
    	if(withChilds)
    	{
    		xml.getRecording().add(TestXmlRecording.create(false));
    		xml.getRecording().add(TestXmlRecording.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();		
			
		TestXmlQuality.initFiles();	
		TestXmlQuality test = new TestXmlQuality();
		test.save();
    }
}