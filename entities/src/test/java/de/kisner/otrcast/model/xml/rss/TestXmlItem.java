package de.kisner.otrcast.model.xml.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.itunes.TestXmlSummary;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlItem extends AbstractXmlRssTest<Item>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlItem.class);
	
	public TestXmlItem(){super(Item.class);}
	public static Item create(boolean withChildren){return (new TestXmlItem()).build(withChildren);}
     
    public Item build(boolean withChilds)
    {
    	Item xml = new Item();
    	if(withChilds)
    	{
    		xml.setTitle(TestXmlTitle.create(false));
    		xml.setDescription(TestXmlDescription.create(false));
    		xml.setSummary(TestXmlSummary.create(false));
    		xml.setImage(de.kisner.otrcast.model.xml.itunes.TestXmlImage.create(false));
    		xml.setPubDate(TestXmlPubDate.create(false));
    		xml.setEnclosure(TestXmlEnclosure.create(false));
    		xml.setGuid(TestXmlGuid.create(false));
    	}
    	return xml;
    }
    
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		TestXmlItem test = new TestXmlItem();
		test.saveReferenceXml();
    }
}