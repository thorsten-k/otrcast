package net.sf.otrcutmp4.test;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.app.AviToMp4.Profile;
import de.kisner.otrcast.controller.batch.BatchGenerator;
import de.kisner.otrcast.controller.batch.RenameGenerator;
import de.kisner.otrcast.controller.exception.OtrInternalErrorException;
import de.kisner.otrcast.model.xml.cut.VideoFiles;

public class TstBatchProcessor
{ 
	final static Logger logger = LoggerFactory.getLogger(TstBatchProcessor.class);
	
	private Configuration config;

	public TstBatchProcessor()
	{
	}
	
	public void cutGenerator() throws FileNotFoundException, OtrInternalErrorException
	{
		String xmlIn = config.getString("xml.test.cut.3");
		logger.debug("Loading from file: "+xmlIn);
		VideoFiles vFiles = (VideoFiles)JaxbUtil.loadJAXB(xmlIn, VideoFiles.class);
		
		BatchGenerator test = new BatchGenerator(null,Profile.P0,false);
		logger.warn("NYI");
//		test.create(vFiles);
	}
	
	public void renameGenerator() throws FileNotFoundException
	{
		String xmlIn = config.getString("xml.test.rename.3");
		logger.debug("Loading from file: "+xmlIn);
		VideoFiles vFiles = (VideoFiles)JaxbUtil.loadJAXB(xmlIn, VideoFiles.class);
		
		RenameGenerator test = new RenameGenerator(null,Profile.P0);
		test.create(vFiles);
	}
	
	public void setConfig(Configuration config) {this.config = config;}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = OtrClientTestBootstrap.init();
		
		TstBatchProcessor test = new TstBatchProcessor();
		test.setConfig(config);
		
		test.renameGenerator();
	}
}