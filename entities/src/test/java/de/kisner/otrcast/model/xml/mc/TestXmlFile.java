package de.kisner.otrcast.model.xml.mc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestXmlFile extends AbstractXmlMcTest<File>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFile.class);
	
	public TestXmlFile(){super(File.class);}
	public static File create(boolean withChildren){return (new TestXmlFile()).build(withChildren);}
    
    public File build(boolean withChilds)
    {
    	File xml = new File();
    	xml.setId(123);
    	xml.setHash("myHash");
    	xml.setSize(123);
    	xml.setName("myName");
    	xml.setLastModified(getDefaultXmlDate());
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OtrXmlTestBootstrap.init();	
		TestXmlFile test = new TestXmlFile();
		test.saveReferenceXml();
    }
}