package de.kisner.otrcast.factory.xml;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.exception.OtrProcessingException;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.test.AbstractOtrcastTest;
import de.kisner.otrcast.test.OtrCastUtilTestBootstrap;
import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;
import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlVideoFileFactory extends AbstractOtrcastTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlVideoFileFactory.class);
		
    private void test(String fileName) throws OtrProcessingException
    {
    	VideoFile xml = XmlVideoFileFactory.create(fileName);
    	JaxbUtil.debug(xml);
    	Assert.assertTrue(xml.isSetOtrId());
    	Assert.assertTrue(xml.isSetFileName());
    	Assert.assertTrue(xml.getFileName().isSetValue());
    	Assert.assertEquals(fileName, xml.getFileName().getValue());
    	
    }
     
    @Test public void test() throws FileNotFoundException, IOException
    {
    	MultiResourceLoader mrl = new MultiResourceLoader();
    	List<String> keys = IOUtils.readLines(mrl.searchIs("data/txt/otrkeys.txt"));
    	for(String key : keys)
    	{
    		try
    		{
    			logger.debug("Testing"+key);
				test(key);
			}
    		catch (OtrProcessingException e)
    		{
				e.printStackTrace();
			}
    	}
    }
    
    public static void main(String[] args) throws ExlpConfigurationException, OtrProcessingException, FileNotFoundException, IOException
    {
		OtrCastUtilTestBootstrap.init();		
		TestXmlVideoFileFactory test = new TestXmlVideoFileFactory();
		test.test();
    }
 }