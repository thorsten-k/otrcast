package de.kisner.otrcast.model.xml.video;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestXmlTag extends AbstractXmlVideoTest<Tag>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTag.class);
	
	public TestXmlTag(){super(Tag.class);}
	public static Tag create(boolean withChildren){return (new TestXmlTag()).build(withChildren);}
    
    public Tag build(boolean withChilds)
    {
    		Tag xml = new Tag();
    		xml.setId(123);  
        return xml;
    }
	
	public static void main(String[] args)
    {
		OtrXmlTestBootstrap.init();
		TestXmlTag test = new TestXmlTag();
		test.saveReferenceXml();
    }
}