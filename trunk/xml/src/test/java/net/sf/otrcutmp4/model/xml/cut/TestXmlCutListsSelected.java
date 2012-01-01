package net.sf.otrcutmp4.model.xml.cut;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCutListsSelected extends AbstractXmlCutTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCutListsSelected.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"cutListsSelected.xml");
	}
    
    @Test
    public void testDownload() throws FileNotFoundException
    {
    	CutListsSelected actual = create();
    	CutListsSelected expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), CutListsSelected.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static CutListsSelected create(){return create(true);}
    public static CutListsSelected create(boolean withChilds)
    {
    	CutListsSelected xml = new CutListsSelected();
    	
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
			
		TestXmlCutListsSelected.initFiles();	
		TestXmlCutListsSelected test = new TestXmlCutListsSelected();
		test.save();
    }
}