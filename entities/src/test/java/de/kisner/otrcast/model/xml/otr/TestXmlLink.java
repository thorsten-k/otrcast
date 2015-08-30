package de.kisner.otrcast.model.xml.otr;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.otr.Link;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlLink extends AbstractXmlOtrTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlOtrId.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"link.xml");
	}
    
    @Test
    public void testDownload() throws FileNotFoundException
    {
    	Link test = create(true);
    	Link ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Link.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Link create(boolean withChilds)
    {
    	Link xml = new Link();
    	xml.setId(123);
    	xml.setUrl("myUrl");
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();	
			
		TestXmlLink.initFiles();	
		TestXmlLink test = new TestXmlLink();
		test.save();
    }
}