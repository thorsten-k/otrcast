package net.sf.otrcutmp4.model.xml.series;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlUpdate extends AbstractXmlSeriesTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUpdate.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"update.xml");
	}
    
    @Test
    public void testAclContainer() throws FileNotFoundException
    {
    	Update test = create(true);
    	Update ref =JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Update.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Update create(boolean withChilds)
    {
    	Update xml = new Update();
    	xml.setActive(true);
    	
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlUpdate.initFiles();	
		TestXmlUpdate test = new TestXmlUpdate();
		test.save();
    }
}