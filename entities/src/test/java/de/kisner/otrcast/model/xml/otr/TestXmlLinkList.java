package de.kisner.otrcast.model.xml.otr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestXmlLinkList extends AbstractXmlOtrTest<Linklist>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlOtrId.class);
	
	public TestXmlLinkList(){super(Linklist.class);}
	public static Linklist create(boolean withChildren){return (new TestXmlLinkList()).build(withChildren);}
    
    public Linklist build(boolean withChilds)
    {
    	Linklist xml = new Linklist();
    	
    	if(withChilds)
    	{
    		xml.getDownload().add(TestXmlDownload.create(false));
    		xml.getDownload().add(TestXmlDownload.create(false));
    		xml.getRecording().add(TestXmlRecording.create(false));
    		xml.getRecording().add(TestXmlRecording.create(false));
    	}
    	return xml;
    }
    
	public static void main(String[] args)
    {
		OtrXmlTestBootstrap.init();		
		TestXmlLinkList test = new TestXmlLinkList();
		test.saveReferenceXml();
    }
}