package de.kisner.otrcast.model.xml.cut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlFileName extends AbstractXmlCutTest<FileName>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFileName.class);
	
	public TestXmlFileName(){super(FileName.class);}
	public static FileName create(boolean withChildren){return (new TestXmlFileName()).build(withChildren);}
    
    public FileName build(boolean withChilds)
    {
    	FileName xml = new FileName();
    	xml.setValue("myFile.mp4");
    	
    	return xml;
    }

	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		TestXmlFileName test = new TestXmlFileName();
		test.saveReferenceXml();
    }
}