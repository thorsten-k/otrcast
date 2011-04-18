package net.sf.otrcutmp4.test;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.ConfigLoader;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.BatchGenerator;
import net.sf.otrcutmp4.data.jaxb.VideoFiles;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TstBatchProcessor
{ 
	static Log logger = LogFactory.getLog(TstBatchProcessor.class);
	
	private Configuration config;
	
	public TstBatchProcessor(Configuration config)
	{
		this.config=config;
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
	
	public static void main(String args[]) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();
		
		ConfigLoader.add("src/test/resources/properties/user.otr.properties");
		ConfigLoader.add("src/test/resources/properties/user.properties");
		Configuration config = ConfigLoader.init();
		
		TstBatchProcessor test = new TstBatchProcessor(config);
//		test.batchGenerator();
		test.path();
	}
}