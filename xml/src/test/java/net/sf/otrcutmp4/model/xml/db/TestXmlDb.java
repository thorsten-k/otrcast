package net.sf.otrcutmp4.model.xml.db;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

public class TestXmlDb extends AbstractXmlDbTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDb.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, Db.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Db actual = create();
    	Db expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Db.class);
    	assertJaxbEquals(expected, actual);
    }
     
    public static Db create()
    {
    	Db xml = new Db();
    	xml.setId("myImage");
    	xml.setSource("mySource");
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlDb.initFiles();	
		TestXmlDb test = new TestXmlDb();
		test.save();
    }
}