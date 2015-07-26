package net.sf.otrcutmp4.model.xml.otr;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.model.xml.container.Otr;
import net.sf.otrcutmp4.model.xml.series.TestXmlEpisode;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

public class TestXmlOtr extends AbstractXmlOtrTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlOtr.class);
	
	@BeforeClass public static void initFiles(){fXml = new File(rootDir,Otr.class.getSimpleName()+".xml");}
    
    @Test
    public void testDownload() throws FileNotFoundException
    {
    	Otr test = create(true);
    	Otr ref = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Otr.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Otr create(boolean withChilds)
    {
    	Otr xml = new Otr();
    	
    	
    	if(withChilds)
    	{
    		xml.getEpisode().add(TestXmlEpisode.create(false));xml.getEpisode().add(TestXmlEpisode.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
			
		TestXmlOtr.initFiles();	
		TestXmlOtr test = new TestXmlOtr();
		test.save();
    }
}