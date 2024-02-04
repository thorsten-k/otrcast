package net.sf.otrcutmp4.controller.batch;

import java.io.FileNotFoundException;

import org.apache.commons.configuration.Configuration;
import org.exlp.util.jx.JaxbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.batch.BatchGenerator;
import de.kisner.otrcast.controller.batch.RenameGenerator;
import de.kisner.otrcast.controller.exception.OtrInternalErrorException;
import de.kisner.otrcast.interfaces.OtrCastInterface;
import de.kisner.otrcast.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.test.OtrClientTestBootstrap;

public class CliBatchProcessor
{ 
	final static Logger logger = LoggerFactory.getLogger(CliBatchProcessor.class);
	
	private Configuration config;

	public CliBatchProcessor()
	{
	}
	
	public void cutGenerator() throws FileNotFoundException, OtrInternalErrorException
	{
		String xmlIn = config.getString("xml.test.cut.3");
		logger.debug("Loading from file: "+xmlIn);
		VideoFiles vFiles = (VideoFiles)JaxbUtil.loadJAXB(xmlIn, VideoFiles.class);
		JaxbUtil.trace(vFiles);
		
		BatchGenerator test = new BatchGenerator(null,OtrCastInterface.Profile.P0,false,false);
		logger.warn("NYI "+test.getClass().getSimpleName());
	}
	
	public void renameGenerator() throws FileNotFoundException
	{
		String xmlIn = config.getString("xml.test.rename.3");
		logger.debug("Loading from file: "+xmlIn);
		VideoFiles vFiles = (VideoFiles)JaxbUtil.loadJAXB(xmlIn, VideoFiles.class);
		
		RenameGenerator test = new RenameGenerator(null,OtrCastInterface.Profile.P0);
		test.create(vFiles);
	}
	
	public void setConfig(Configuration config) {this.config = config;}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = OtrClientTestBootstrap.init();
		
		CliBatchProcessor test = new CliBatchProcessor();
		test.setConfig(config);
		
		test.renameGenerator();
	}
}