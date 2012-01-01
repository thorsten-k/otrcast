package net.sf.otrcutmp4.model.xml.cut;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.model.xml.otr.TestXmlOtrId;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlVideoFile extends AbstractXmlCutTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlVideoFile.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"videoFile.xml");
	}
    
    @Test
    public void testDownload() throws FileNotFoundException
    {
    	VideoFile actual = create();
    	VideoFile expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), VideoFile.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static VideoFile create(){return create(true);}
    public static VideoFile create(boolean withChilds)
    {
    	VideoFile xml = new VideoFile();
    	
    	if(withChilds)
    	{
    		xml.setFileName(TestXmlFileName.create(false));
    		xml.setOtrId(TestXmlOtrId.create(false));
    		xml.setCutListsAvailable(TestXmlCutListsAvailable.create(false));
    		xml.setCutListsSelected(TestXmlCutListsSelected.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
			
		TestXmlVideoFile.initFiles();	
		TestXmlVideoFile test = new TestXmlVideoFile();
		test.save();
    }
}