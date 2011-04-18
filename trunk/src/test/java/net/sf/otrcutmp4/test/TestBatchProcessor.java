package net.sf.otrcutmp4.test;

import java.io.File;
import java.io.FileNotFoundException;

import junit.framework.TestCase;
import net.sf.exlp.util.io.ConfigLoader;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.BatchGenerator;
import net.sf.otrcutmp4.data.jaxb.VideoFiles;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestBatchProcessor extends TestCase
{ 
	static Log logger = LogFactory.getLog(TestBatchProcessor.class);
	
	private Configuration config;

	public TestBatchProcessor()
	{
	}
	
	public void testBatchSecond()
	{		
		assertEquals(BatchGenerator.getSecond(123.999),"124.00");
		assertEquals(BatchGenerator.getSecond(123),"123.00");
		assertEquals(BatchGenerator.getSecond(2.0),"2.00");
		assertEquals(BatchGenerator.getSecond(2.1),"2.10");
		assertEquals(BatchGenerator.getSecond(12342.1),"12342.10");
	}
	
	public void batchGenerator() throws FileNotFoundException
	{
		String xmlIn = config.getString("xml.test.3.avi");
		logger.debug("Loading from file: "+xmlIn);
		VideoFiles vFiles = (VideoFiles)JaxbUtil.loadJAXB(xmlIn, VideoFiles.class);
		
		BatchGenerator test = new BatchGenerator(config);
		test.create(vFiles);
	}
	
	public void path()
	{
		File fFixed = new File(".");
		File fRelative = new File(".","target/x1.txt");
		
		RelativePathFactory rpf = new RelativePathFactory();
		
		logger.debug("Current: "+fFixed.getAbsolutePath());
		logger.debug("Absolute: "+fRelative.getAbsolutePath());
		logger.debug("Relative: "+rpf.relativate(fFixed.getAbsolutePath(), fRelative.getAbsolutePath()));
	}
	
	public void setConfig(Configuration config) {this.config = config;}
	
	public static void main(String args[]) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();
		
		ConfigLoader.add("src/test/resources/properties/user.otr.properties");
		ConfigLoader.add("src/test/resources/properties/user.properties");
		Configuration config = ConfigLoader.init();
		
		TestBatchProcessor test = new TestBatchProcessor();
		test.setConfig(config);
//		test.batchGenerator();
		test.testBatchSecond();
	}
}