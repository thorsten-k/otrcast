package net.sf.otrcutmp4.model.xml.otr;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlOtrId extends AbstractXmlOtrTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlOtrId.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"otrId.xml");
	}
    
    @Test
    public void testDownload() throws FileNotFoundException
    {
    	OtrId test = create();
    	OtrId ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), OtrId.class);
    	assertJaxbEquals(ref, test);
    }
    
    private static OtrId create(){return create(true);}
    public static OtrId create(boolean withChilds)
    {
    	OtrId xml = new OtrId();
    	xml.setId(1);
    	xml.setKey("myKey");
    	
    	if(withChilds)
    	{
    		xml.getQuality().add(TestXmlQuality.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
			
		TestXmlOtrId.initFiles();	
		TestXmlOtrId test = new TestXmlOtrId();
		test.save();
    }
}