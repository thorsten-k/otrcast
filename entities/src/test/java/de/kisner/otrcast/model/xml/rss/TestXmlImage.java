package de.kisner.otrcast.model.xml.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlImage extends AbstractXmlRssTest<Image>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlImage.class);
	
	public TestXmlImage(){super(Image.class);}
	public static Image create(boolean withChildren){return (new TestXmlImage()).build(withChildren);}
     
    public Image build(boolean withChilds)
    {
    	Image xml = new Image();
    	if(withChilds)
    	{
    		xml.setUrl(TestXmlUrl.create(false));
    		xml.setTitle(TestXmlTitle.create(false));
    		xml.setLink(TestXmlLink.create(false));
    	}
    	return xml;
    }
    
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		TestXmlImage test = new TestXmlImage();
		test.saveReferenceXml();
    }
}