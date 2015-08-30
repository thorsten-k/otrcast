package de.kisner.otrcast.model.xml.tvdb;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.series.TestXmlSeason;
import de.kisner.otrcast.model.xml.tvdb.Banners;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlBanners extends AbstractXmlTvDbTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlBanners.class);
	
	@BeforeClass
	public static void initFiles()
	{
        setXmlFile(dirSuffix,Banners.class);
	}
    
    @Test
    public void jaxb() throws FileNotFoundException
    {
        Banners actual = create(true);
        Banners expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Banners.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Banners create(boolean withChilds)
    {
        Banners xml = new Banners();

    	if(withChilds)
        {
            xml.getBanner().add(TestXmlBanner.create(false));xml.getBanner().add(TestXmlBanner.create(false));         
            xml.getSeason().add(TestXmlSeason.create(false)); xml.getSeason().add(TestXmlSeason.create(false));
        }

    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlBanners.initFiles();
		TestXmlBanners test = new TestXmlBanners();
		test.save();
    }
}