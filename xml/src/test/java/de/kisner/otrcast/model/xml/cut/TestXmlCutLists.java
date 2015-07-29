package de.kisner.otrcast.model.xml.cut;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.cut.CutLists;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlCutLists extends AbstractXmlCutTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCutLists.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,CutLists.class.getSimpleName()+".xml");
	}
    
    @Test
    public void testDownload() throws FileNotFoundException
    {
    	CutLists actual = create(true);
    	CutLists expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), CutLists.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static CutLists create(boolean withChilds)
    {
    	CutLists xml = new CutLists();
    	
    	if(withChilds)
    	{
    		xml.getCutList().add(TestXmlCutList.create(false));
    		xml.getCutList().add(TestXmlCutList.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
			
		TestXmlCutLists.initFiles();	
		TestXmlCutLists test = new TestXmlCutLists();
		test.save();
    }
}