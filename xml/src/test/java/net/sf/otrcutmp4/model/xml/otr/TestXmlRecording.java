package net.sf.otrcutmp4.model.xml.otr;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlRecording extends AbstractXmlOtrTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlRecording.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"recording.xml");
	}
    
    @Test
    public void testDownload() throws FileNotFoundException
    {
    	Recording actual = create(true);
    	Recording expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Recording.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Recording create(){return create(true);}
    public static Recording create(boolean withChilds)
    {
    	Recording xml = new Recording();
    	xml.setId(1);
    	
    	if(withChilds)
    	{
    		//TODO fields missing
    		xml.setOtrId(TestXmlOtrId.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
			
		TestXmlRecording.initFiles();	
		TestXmlRecording test = new TestXmlRecording();
		test.save();
    }
}