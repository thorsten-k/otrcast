package de.kisner.otrcast.model.xml.otr;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.cut.TestXmlCutList;
import de.kisner.otrcast.model.xml.otr.Recording;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

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
    		xml.setOtrId(TestXmlOtrId.create(false));
    		xml.setFormat(TestXmlFormat.create(false));
    		xml.setCutList(TestXmlCutList.create(false));
    		xml.setLink(TestXmlLink.create(false));
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