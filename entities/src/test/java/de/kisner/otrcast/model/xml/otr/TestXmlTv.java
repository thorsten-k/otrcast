package de.kisner.otrcast.model.xml.otr;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.otr.Tv;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlTv extends AbstractXmlOtrTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlOtrId.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Tv.class.getSimpleName()+".xml");
	}
    
    @Test
    public void testDownload() throws FileNotFoundException
    {
    	Tv test = create(true);
    	Tv ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Tv.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Tv create(boolean withChilds)
    {
    	Tv xml = new Tv();
    	xml.setChannel("myChannel");
    	xml.setDuration(123);
    	xml.setName("myName");
    	xml.setAirtime(getDefaultXmlDate());
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();	
			
		TestXmlTv.initFiles();	
		TestXmlTv test = new TestXmlTv();
		test.save();
    }
}