package de.kisner.otrcast.model.xml.cut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestXmlName extends AbstractXmlCutTest<Name>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlName.class);
	
	public TestXmlName(){super(Name.class);}
	public static Name create(boolean withChildren){return (new TestXmlName()).build(withChildren);}
    
    public Name build(boolean withChilds)
    {
    	Name xml = new Name();
    	xml.setValue("myName");
    	
    	return xml;
    }

	public static void main(String[] args)
    {
		OtrXmlTestBootstrap.init();
		TestXmlName test = new TestXmlName();
		test.saveReferenceXml();
    }
}