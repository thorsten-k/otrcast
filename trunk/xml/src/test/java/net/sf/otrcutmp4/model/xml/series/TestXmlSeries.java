package net.sf.otrcutmp4.model.xml.series;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrXmlTstBootstrap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSeries extends AbstractXmlSeriesTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSeries.class);
	
	@BeforeClass
	public static void initFiles()
	{
        setXmlFile(dirSuffix,"series");
	}
    
    @Test
    public void testAclContainer() throws FileNotFoundException
    {
    	Series test = create(true);
    	Series ref = (Series)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Series.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Series create(boolean withChilds)
    {
    	Series xml = new Series();
    	xml.setName("Test Name");
    	xml.setKey("KEY");
    	
    	if(withChilds)
    	{
    		xml.getSeason().add(TestXmlSeason.create(false));
    		xml.getSeason().add(TestXmlSeason.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		OtrXmlTstBootstrap.init();
		
		TestXmlSeries.initFiles();	
		TestXmlSeries test = new TestXmlSeries();
		test.save();
    }
}