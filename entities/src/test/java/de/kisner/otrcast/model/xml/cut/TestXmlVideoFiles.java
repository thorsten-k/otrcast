package de.kisner.otrcast.model.xml.cut;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.cut.VideoFiles;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlVideoFiles extends AbstractXmlCutTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlVideoFiles.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"videoFiles.xml");
	}
    
    @Test
    public void testDownload() throws FileNotFoundException
    {
    	VideoFiles actual = create();
    	VideoFiles expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), VideoFiles.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static VideoFiles create(){return create(true);}
    public static VideoFiles create(boolean withChilds)
    {
    	VideoFiles xml = new VideoFiles();
    	
    	if(withChilds)
    	{
    		xml.getVideoFile().add(TestXmlVideoFile.create(false));
    		xml.getVideoFile().add(TestXmlVideoFile.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
			
		TestXmlVideoFiles.initFiles();	
		TestXmlVideoFiles test = new TestXmlVideoFiles();
		test.save();
    }
}