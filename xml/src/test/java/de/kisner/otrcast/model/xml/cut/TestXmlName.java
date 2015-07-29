package de.kisner.otrcast.model.xml.cut;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.cut.Name;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlName extends AbstractXmlCutTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlName.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"name.xml");
	}
    
    @Test
    public void testDownload() throws FileNotFoundException
    {
    	Name actual = create();
    	Name expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Name.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Name create(){return create(true);}
    public static Name create(boolean withChilds)
    {
    	Name xml = new Name();
    	xml.setValue("myName");
    	
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
			
		TestXmlName.initFiles();	
		TestXmlName test = new TestXmlName();
		test.save();
    }
}