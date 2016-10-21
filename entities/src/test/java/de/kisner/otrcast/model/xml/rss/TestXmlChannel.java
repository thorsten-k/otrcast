package de.kisner.otrcast.model.xml.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlChannel extends AbstractXmlRssTest<Channel>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlChannel.class);
	
	public TestXmlChannel(){super(Channel.class);}
	public static Channel create(boolean withChildren){return (new TestXmlChannel()).build(withChildren);}
     
    public Channel build(boolean withChilds)
    {
    	Channel xml = new Channel();
    	
    	if(withChilds)
    	{
    		xml.setTitle(TestXmlTitle.create(false));
    		xml.setLink(TestXmlLink.create(false));
    		xml.setDescription(TestXmlDescription.create(false));
    		xml.setLanguage(TestXmlLanguage.create(false));
    		xml.setCopyright(TestXmlCopyright.create(false));
    		xml.setPubDate(TestXmlPubDate.create(false));
    		xml.setImage(TestXmlImage.create(false));
    		xml.getItem().add(TestXmlItem.create(false));xml.getItem().add(TestXmlItem.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		TestXmlChannel test = new TestXmlChannel();
		test.saveReferenceXml();
    }
}