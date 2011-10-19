package net.sf.otrcutmp4.test;

import java.io.FileNotFoundException;

import junit.framework.TestCase;
import net.sf.exlp.util.io.ConfigLoader;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.AviToMp4.Audio;
import net.sf.otrcutmp4.AviToMp4.Profile;
import net.sf.otrcutmp4.AviToMp4.Quality;
import net.sf.otrcutmp4.controller.batch.CutGenerator;
import net.sf.otrcutmp4.controller.batch.RenameGenerator;
import net.sf.otrcutmp4.controller.batch.video.VideoCutter;
import net.sf.otrcutmp4.controller.exception.OtrInternalErrorException;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;

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
		assertEquals(VideoCutter.getSecond(123.999),"124.00");
		assertEquals(VideoCutter.getSecond(123),"123.00");
		assertEquals(VideoCutter.getSecond(2.0),"2.00");
		assertEquals(VideoCutter.getSecond(2.1),"2.10");
		assertEquals(VideoCutter.getSecond(12342.1),"12342.10");
	}
	
	public void cutGenerator() throws FileNotFoundException, OtrInternalErrorException
	{
		String xmlIn = config.getString("xml.test.cut.3");
		logger.debug("Loading from file: "+xmlIn);
		VideoFiles vFiles = (VideoFiles)JaxbUtil.loadJAXB(xmlIn, VideoFiles.class);
		
		CutGenerator test = new CutGenerator(config,Quality.HQ,Audio.Mp3,Profile.P0);
		test.create(vFiles);
	}
	
	public void renameGenerator() throws FileNotFoundException
	{
		String xmlIn = config.getString("xml.test.rename.3");
		logger.debug("Loading from file: "+xmlIn);
		VideoFiles vFiles = (VideoFiles)JaxbUtil.loadJAXB(xmlIn, VideoFiles.class);
		
		RenameGenerator test = new RenameGenerator(config);
		test.create(vFiles);
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
		
		test.testBatchSecond();
		
		test.renameGenerator();
	}
}