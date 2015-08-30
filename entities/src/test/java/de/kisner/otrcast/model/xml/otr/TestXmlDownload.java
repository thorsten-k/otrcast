package de.kisner.otrcast.model.xml.otr;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.otr.Download;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlDownload extends AbstractXmlOtrTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDownload.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"download.xml");
	}
    
    @Test
    public void testDownload() throws FileNotFoundException
    {
    	Download test = create();
    	Download ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Download.class);
    	assertJaxbEquals(ref, test);
    }
    
    private static Download create(){return create(true);}
    public static Download create(boolean withChilds)
    {
    	Download xml = new Download();
    	xml.setId(1);
    	xml.setType("myType");
    	
    	if(withChilds)
    	{
    		xml.getOtrId().add(TestXmlOtrId.create(false));
    		xml.getOtrId().add(TestXmlOtrId.create(false));
    		xml.getRecording().add(TestXmlRecording.create(false));
    		xml.getRecording().add(TestXmlRecording.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
			
		TestXmlDownload.initFiles();	
		TestXmlDownload test = new TestXmlDownload();
		test.save();
    }
}