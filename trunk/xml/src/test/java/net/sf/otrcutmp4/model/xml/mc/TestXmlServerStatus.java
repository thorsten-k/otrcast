package net.sf.otrcutmp4.model.xml.mc;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlServerStatus extends AbstractXmlMcTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlServerStatus.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,ServerStatus.class.getSimpleName()+".xml");
	}
    
    @Test
    public void testDownload() throws FileNotFoundException
    {
    	ServerStatus actual = create(true);
    	ServerStatus expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), ServerStatus.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static ServerStatus create(boolean withChilds)
    {
    	ServerStatus xml = new ServerStatus();
    	xml.setLastRestart(getDefaultXmlDate());
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
			
		TestXmlServerStatus.initXml();	
		TestXmlServerStatus.initFiles();
		TestXmlServerStatus test = new TestXmlServerStatus();
		test.save();
    }
}