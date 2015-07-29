package de.kisner.otrcast.model.xml.series;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.mc.TestXmlImage;
import de.kisner.otrcast.model.xml.series.Season;
import de.kisner.otrcast.model.xml.tvdb.TestXmlBanners;
import de.kisner.otrcast.model.xml.tvdb.TestXmlSync;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestXmlSeason extends AbstractXmlSeriesTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSeason.class);
	
	@BeforeClass
	public static void initFiles()
	{
        setXmlFile(dirSuffix,"season");
	}
    
    @Test
    public void testAclContainer() throws FileNotFoundException
    {
    	Season test = create(true);
    	Season ref = (Season)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Season.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Season create(boolean withChilds)
    {
    	Season xml = new Season();
    	xml.setId(123);
    	xml.setNr(1);
    	xml.setName("myName");
    	xml.setShowName(true);
    	xml.setShowNr(true);
    	
    	if(withChilds)
    	{
    		xml.setSeries(TestXmlSeries.create(false));
    		xml.setImage(TestXmlImage.create(false));
    		xml.getEpisode().add(TestXmlEpisode.create(false));
    		xml.getEpisode().add(TestXmlEpisode.create(false));
    		xml.setSync(TestXmlSync.create(false));
    		xml.setBanners(TestXmlBanners.create(false));
    	}
    	
    	return xml;
    }
	
    public void save() {save(create(true), fXml);}
    
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlSeason.initFiles();	
		TestXmlSeason test = new TestXmlSeason();
		test.save();
    }
}