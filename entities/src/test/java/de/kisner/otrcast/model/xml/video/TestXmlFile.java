package de.kisner.otrcast.model.xml.video;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlFile extends AbstractXmlVideoTest<File>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFile.class);
	
	public TestXmlFile(){super(File.class);}
	public static File create(boolean withChildren){return (new TestXmlFile()).build(withChildren);}
    
    public File build(boolean withChilds)
    {
    	File xml = new File();
    	xml.setName("myName");
    	xml.setPath("myPath");
        xml.setValue("myPath/myName");
        return xml;
    }
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		TestXmlFile test = new TestXmlFile();
		test.saveReferenceXml();
    }
}