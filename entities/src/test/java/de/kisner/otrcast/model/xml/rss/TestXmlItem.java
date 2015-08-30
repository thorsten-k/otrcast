package de.kisner.otrcast.model.xml.rss;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.itunes.TestXmlSummary;
import de.kisner.otrcast.model.xml.rss.Item;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlItem extends AbstractXmlRssTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlItem.class);
	
	@BeforeClass
	public static void initFiles()
	{
        fXml = new File(getXmlDir(dirSuffix),Item.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Item actual = create(true);
    	Item expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Item.class);
    	assertJaxbEquals(expected, actual);
    }
     
    public static Item create(boolean withChilds)
    {
    	Item xml = new Item();
    	
    	if(withChilds)
    	{
    		xml.setTitle(TestXmlTitle.create());
    		xml.setDescription(TestXmlDescription.create());
    		xml.setSummary(TestXmlSummary.create());
    		xml.setImage(de.kisner.otrcast.model.xml.itunes.TestXmlImage.create());
    		xml.setPubDate(TestXmlPubDate.create());
    		xml.setEnclosure(TestXmlEnclosure.create());
    		xml.setGuid(TestXmlGuid.create());
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlItem.initFiles();	
		TestXmlItem test = new TestXmlItem();
		test.save();
    }
}