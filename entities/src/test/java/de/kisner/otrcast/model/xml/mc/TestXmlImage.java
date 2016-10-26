package de.kisner.otrcast.model.xml.mc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestXmlImage extends AbstractXmlMcTest<Image>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlImage.class);
	
	public TestXmlImage(){super(Image.class);}
	public static Image create(boolean withChildren){return (new TestXmlImage()).build(withChildren);}
    
    public Image build(boolean withChilds)
    {
    	Image xml = new Image();
    	xml.setId(123);
    	xml.setFileType("png");
    	
    	if(withChilds)
    	{
    		xml.setData("myBinaryData".getBytes());
    		xml.setUrl("myURL");
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OtrXmlTestBootstrap.init();
		TestXmlImage test = new TestXmlImage();
		test.saveReferenceXml();
    }
}