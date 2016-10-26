package de.kisner.otrcast.model.xml.mc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestXmlServerStatus extends AbstractXmlMcTest<ServerStatus>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlServerStatus.class);
	
	public TestXmlServerStatus(){super(ServerStatus.class);}
	public static ServerStatus create(boolean withChildren){return (new TestXmlServerStatus()).build(withChildren);}
    
    public ServerStatus build(boolean withChilds)
    {
    	ServerStatus xml = new ServerStatus();
    	xml.setLastRestart(getDefaultXmlDate());
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OtrXmlTestBootstrap.init();
		TestXmlServerStatus test = new TestXmlServerStatus();
		test.saveReferenceXml();
    }
}