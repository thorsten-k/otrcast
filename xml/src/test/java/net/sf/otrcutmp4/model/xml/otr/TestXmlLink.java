package net.sf.otrcutmp4.model.xml.otr;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    	Link test = create();
    	Link ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Link.class);
    	assertJaxbEquals(ref, test);
    }
    
    private static Link create(){return create(true);}
    public static Link create(boolean withChilds)
    {
    	Link xml = new Link();
    	xml.setId(123);
    	xml.setUrl("myUrl");
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();	
			
		TestXmlLink.initFiles();	
		TestXmlLink test = new TestXmlLink();
		test.save();
    }
}