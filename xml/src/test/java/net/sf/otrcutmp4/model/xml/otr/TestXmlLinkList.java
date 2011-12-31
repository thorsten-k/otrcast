package net.sf.otrcutmp4.model.xml.otr;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlLinkList extends AbstractXmlOtrTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlOtrId.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"linklist.xml");
	}
    
    @Test
    public void testDownload() throws FileNotFoundException
    {
    	Linklist test = create();
    	Linklist ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Linklist.class);
    	assertJaxbEquals(ref, test);
    }
    
    private static Linklist create(){return create(true);}
    public static Linklist create(boolean withChilds)
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
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();	
			
		TestXmlLinkList.initFiles();	
		TestXmlLinkList test = new TestXmlLinkList();
		test.save();
    }
}