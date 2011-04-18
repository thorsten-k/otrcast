package net.sf.otrcutmp4.test;

import java.io.File;

import net.sf.exlp.util.io.ConfigLoader;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.AviProcessor;
import net.sf.otrcutmp4.data.jaxb.VideoFiles;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TstAviProcessor
{
	static Log logger = LogFactory.getLog(TstAviProcessor.class);
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/main/resources/config");
			loggerInit.init();
		
		ConfigLoader.add("src/test/resources/properties/user.properties");
		ConfigLoader.add("src/test/resources/properties/user.otr.properties");
		Configuration config = ConfigLoader.init();	
				
		AviProcessor test = new AviProcessor(config);
		VideoFiles videoFiles = test.readFiles();
		
		JaxbUtil.debug(TstAviProcessor.class,videoFiles);
		String xmlFile = config.getString("xml.test.1.avi");
		logger.debug("Saving to file: "+xmlFile);
		JaxbUtil.save(new File(xmlFile), videoFiles, true);
	}
}
