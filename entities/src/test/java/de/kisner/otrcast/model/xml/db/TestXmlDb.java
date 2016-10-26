package de.kisner.otrcast.model.xml.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestXmlDb extends AbstractXmlDbTest<Db>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDb.class);
	
	public TestXmlDb(){super(Db.class);}
	public static Db create(boolean withChildren){return (new TestXmlDb()).build(withChildren);}
     
    public Db build(boolean withChilds)
    {
    	Db xml = new Db();
    	xml.setId("myImage");
    	xml.setSource("mySource");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OtrXmlTestBootstrap.init();
		TestXmlDb test = new TestXmlDb();
		test.saveReferenceXml();
    }
}