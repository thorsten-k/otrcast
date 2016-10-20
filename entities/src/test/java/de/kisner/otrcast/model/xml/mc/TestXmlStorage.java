package de.kisner.otrcast.model.xml.mc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTstBootstrap;
import net.sf.exlp.util.DateUtil;

public class TestXmlStorage extends AbstractXmlMcTest<Storage>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlStorage.class);
	
	public TestXmlStorage(){super(Storage.class);}
	public static Storage create(boolean withChildren){return (new TestXmlStorage()).build(withChildren);}
    
    public Storage build(boolean withChilds)
    {
    	Storage xml = new Storage();
    	xml.setId(123);
    	xml.setHash("myHash");
    	xml.setSize(123);
    	xml.setName("myName");
    	xml.setLastModified(getDefaultXmlDate());
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		DateUtil.ignoreTimeZone=true;	
		TestXmlStorage test = new TestXmlStorage();
		test.saveReferenceXml();
    }
}