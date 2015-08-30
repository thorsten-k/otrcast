package de.kisner.otrcast.model.xml.cut;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.cut.Cut;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlCut extends AbstractXmlCutTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCut.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"cut.xml");
	}
    
    @Test
    public void testDownload() throws FileNotFoundException
    {
    	Cut actual = create();
    	Cut expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Cut.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Cut create(){return create(true);}
    public static Cut create(boolean withChilds)
    {
    	Cut xml = new Cut();
    	xml.setDuration(12);
    	xml.setId("myId");
    	xml.setStart(32);
    	xml.setInclude(true);
    	
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
			
		TestXmlCut.initFiles();	
		TestXmlCut test = new TestXmlCut();
		test.save();
    }
}