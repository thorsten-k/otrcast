package net.sf.otrcutmp4.test;

import java.io.File;

import net.sf.exlp.util.io.ConfigLoader;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.data.jaxb.VideoFiles;
import net.sf.otrcutmp4.util.SrcDirProcessor;
import net.sf.otrcutmp4.util.OtrConfig;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TstAviProcessor
{
	static Log logger = LogFactory.getLog(TstAviProcessor.class);
	
	private Configuration config;
	
	public TstAviProcessor(Configuration config)
	{
		this.config=config;
	}
	
	public void cut()
	{
		SrcDirProcessor test = new SrcDirProcessor();
		VideoFiles videoFiles = test.readFiles(new File(config.getString(OtrConfig.dirHqAvi)),SrcDirProcessor.VideType.avi); 
		
		JaxbUtil.debug(TstAviProcessor.class,videoFiles);
		String xmlFile = config.getString("xml.test.cut.1");
		logger.debug("Saving to file: "+xmlFile);
		JaxbUtil.save(new File(xmlFile), videoFiles, true);
	}
	
	public void rename()
	{
		SrcDirProcessor test = new SrcDirProcessor();
		VideoFiles videoFiles = test.readFiles(new File(config.getString(OtrConfig.dirMp4Rename)),SrcDirProcessor.VideType.mp4); 
		
		JaxbUtil.debug(TstAviProcessor.class,videoFiles);
		String xmlFile = config.getString("xml.test.rename.1");
		logger.debug("Saving to file: "+xmlFile);
		JaxbUtil.save(new File(xmlFile), videoFiles, true);
	}
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();
		
		ConfigLoader.add("src/test/resources/properties/user.properties");
		ConfigLoader.add("src/test/resources/properties/user.otr.properties");
		Configuration config = ConfigLoader.init();	
				
		TstAviProcessor test = new TstAviProcessor(config);
		test.cut();
		test.rename();
	}
}
