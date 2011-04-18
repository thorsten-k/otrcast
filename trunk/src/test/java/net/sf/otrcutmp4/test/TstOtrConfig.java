package net.sf.otrcutmp4.test;

import java.io.File;

import net.sf.exlp.util.io.ConfigLoader;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.AviProcessor;
import net.sf.otrcutmp4.data.jaxb.VideoFiles;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TstOtrConfig
{
	static Log logger = LogFactory.getLog(TstOtrConfig.class);
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/main/resources/config");
			loggerInit.init();
		
		ConfigLoader.add("src/test/resources/properties/user.properties");
		ConfigLoader.add("src/test/resources/properties/user.otr.properties");
		Configuration config = ConfigLoader.init();	
				
		String s = "c:\\TstAviProcessor.java";
		logger.debug(s);
		logger.debug(FilenameUtils.normalize(s));
	}
}
