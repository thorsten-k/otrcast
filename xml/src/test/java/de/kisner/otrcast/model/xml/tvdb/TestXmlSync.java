package de.kisner.otrcast.model.xml.tvdb;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.tvdb.Sync;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlSync extends AbstractXmlTvDbTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSync.class);
	
	@BeforeClass
	public static void initFiles()
	{
        setXmlFile(dirSuffix,Sync.class);
	}
    
    @Test
    public void jaxb() throws FileNotFoundException
    {
        Sync test = create(true);
        Sync ref =JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Sync.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Sync create(boolean withChilds)
    {
        Sync xml = new Sync();
        xml.setId(123);

        if(withChilds)
        {
            xml.setBanners(TestXmlBanners.create(false));
        }


        return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlSync.initFiles();
		TestXmlSync test = new TestXmlSync();
		test.save();
    }
}