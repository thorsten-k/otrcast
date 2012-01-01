package net.sf.otrcutmp4.model.xml.cut;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCutListsAvailable extends AbstractXmlCutTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCutListsAvailable.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"cutListsAvailable.xml");
	}
    
    @Test
    public void testDownload() throws FileNotFoundException
    {
    	CutListsAvailable actual = create();
    	CutListsAvailable expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), CutListsAvailable.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static CutListsAvailable create(){return create(true);}
    public static CutListsAvailable create(boolean withChilds)
    {
    	CutListsAvailable xml = new CutListsAvailable();
    	
    	if(withChilds)
    	{
    		xml.getCutList().add(TestXmlCutList.create(false));
    		xml.getCutList().add(TestXmlCutList.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
			
		TestXmlCutListsAvailable.initFiles();	
		TestXmlCutListsAvailable test = new TestXmlCutListsAvailable();
		test.save();
    }
}