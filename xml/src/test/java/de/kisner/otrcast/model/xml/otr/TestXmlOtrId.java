package de.kisner.otrcast.model.xml.otr;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.otr.OtrId;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

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
    	xml.setOtrCl("1234");
    	
    	if(withChilds)
    	{
    		xml.getQuality().add(TestXmlQuality.create(false));
    		xml.getQuality().add(TestXmlQuality.create(false));
    		xml.setFormat(TestXmlFormat.create(false));
    		xml.setTv(TestXmlTv.create(false));
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