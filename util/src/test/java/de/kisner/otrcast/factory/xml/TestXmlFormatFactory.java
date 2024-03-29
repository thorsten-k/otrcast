package de.kisner.otrcast.factory.xml;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.otr.Format;
import de.kisner.otrcast.test.AbstractOtrcastTest;
import de.kisner.otrcast.test.OtrCastUtilTestBootstrap;
import net.sf.exlp.exception.ExlpConfigurationException;

public class TestXmlFormatFactory extends AbstractOtrcastTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFormatFactory.class);
	
	private String suffix;
	
//	@Rule public IgnoreOtherRule test = new IgnoreOtherRule("testHdAc3Key");
	
	//HD
    @Test public void testHdAc3Key()
    {
    	suffix = "HD.ac3.otrkey";
    	Format test = XmlFormatFactory.create(suffix);
    	assertSet(test);
    	assertQuality(XmlQualityFactory.qHD,test);
    	Assert.assertEquals(true, test.isOtrkey());
    	Assert.assertEquals(false, test.isCut());
    	Assert.assertEquals("ac3", test.getType());
    }
    @Test public void testHdAvi()
    {
    	suffix = "HD.avi";
    	Format test = XmlFormatFactory.create(suffix);
    	assertSet(test);
    	assertQuality(XmlQualityFactory.qHD,test);
    	Assert.assertEquals(false, test.isOtrkey());
    	Assert.assertEquals(false, test.isCut());
    	Assert.assertEquals("avi", test.getType());
    }
    @Test public void testHdAviKey()
    {
    	suffix = "HD.avi.otrkey";
    	Format test = XmlFormatFactory.create(suffix);
    	assertSet(test);
    	assertQuality(XmlQualityFactory.qHD,test);
    	Assert.assertEquals(true, test.isOtrkey());
    	Assert.assertEquals(false, test.isCut());
    	Assert.assertEquals("avi", test.getType());
    }
    
    //HQ
    @Test public void testHqMp4Cut()
    {
    	suffix = "HQ.cut.mp4";
    	Format test = XmlFormatFactory.create(suffix);
    	assertSet(test);
    	assertQuality(XmlQualityFactory.qHQ,test);
    	assertFormatBools(test, true, false);
    	Assert.assertEquals("mp4", test.getType());
    }
    @Test public void testHqAvi()
    {
    	suffix = "HQ.avi";
    	Format test = XmlFormatFactory.create(suffix);
    	assertSet(test);
    	assertQuality(XmlQualityFactory.qHQ,test);
    	assertFormatBools(test, false, false);
    	Assert.assertEquals("avi", test.getType());
    }
    @Test public void testHqAviKey()
    {
    	suffix = "HQ.avi.otrkey";
    	Format test = XmlFormatFactory.create(suffix);
    	assertSet(test);
    	assertQuality(XmlQualityFactory.qHQ,test);
    	assertFormatBools(test, false, true);
    	Assert.assertEquals("avi", test.getType());
    }
    
    //LQ
    @Test public void testLqAvi()
    {
    	suffix = "avi";
    	Format test = XmlFormatFactory.create(suffix);
    	assertSet(test);
    	assertQuality(XmlQualityFactory.qDivX,test);
    	assertFormatBools(test, false, false);
    	Assert.assertEquals("avi", test.getType());
    }
    @Test public void testLqAviKey()
    {
    	suffix = "avi.otrkey";
    	Format test = XmlFormatFactory.create(suffix);
    	assertSet(test);
    	assertQuality(XmlQualityFactory.qDivX,test);
    	assertFormatBools(test, false, true);
    	Assert.assertEquals("avi", test.getType());
    }
    @Test public void testMp4()
    {
    	suffix = "mp4";
    	Format test = XmlFormatFactory.create(suffix);
    	assertSet(test);
    	assertQuality(XmlQualityFactory.qMobile,test);
    	assertFormatBools(test, false, false);
    	Assert.assertEquals("mp4", test.getType());
    }
    @Test public void testMp4Key()
    {
    	suffix = "mp4.otrkey";
    	Format test = XmlFormatFactory.create(suffix);
    	assertSet(test);
    	assertQuality(XmlQualityFactory.qMobile,test);
    	assertFormatBools(test, false, true);
    	Assert.assertEquals("mp4", test.getType());
    }
    @Test public void testMp4Cut()
    {
    	suffix = "cut.mp4";
    	Format test = XmlFormatFactory.create(suffix);
    	assertSet(test);
    	assertQuality(XmlQualityFactory.qMobile,test);
    	assertFormatBools(test, true, false);
    	Assert.assertEquals("mp4", test.getType());
    }
    
    private void assertSet(Format test)
    {
    	Assert.assertTrue("Set otrkey", test.isSetOtrkey());
    	Assert.assertTrue("Set cut", test.isSetCut());
    	Assert.assertTrue("Set type", test.isSetType());
    	
    	Assert.assertTrue("Set quality", test.isSetQuality());
    	Assert.assertTrue("Set quality.type", test.getQuality().isSetType());
    }
    
    private void assertQuality(String expected, Format test)
    {
    	Assert.assertEquals("Quality.Type",expected, test.getQuality().getType());
    }
    
    private void assertFormatBools(Format test, boolean cut, boolean key)
    {
    	Assert.assertEquals("Cut", cut, test.isCut());
    	Assert.assertEquals("Key", key, test.isOtrkey());
    }
    
    public static void main(String[] args) throws ExlpConfigurationException
    {
		OtrCastUtilTestBootstrap.init();		
			
		TestXmlFormatFactory.initPrefixMapper();
	
		TestXmlFormatFactory test = new TestXmlFormatFactory();
		test.testMp4();
    }
 }