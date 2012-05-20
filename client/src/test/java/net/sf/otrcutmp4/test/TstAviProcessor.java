package net.sf.otrcutmp4.test;

import java.io.File;

import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.ns.NsPrefixMapperInterface;
import net.sf.otrcutmp4.controller.SrcDirProcessor;
import net.sf.otrcutmp4.interfaces.view.ViewSrcDirProcessor;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.model.xml.ns.OtrCutNsPrefixMapper;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.view.cli.CliSrcDirProcessorView;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstAviProcessor
{
	final static Logger logger = LoggerFactory.getLogger(TstAviProcessor.class);
	
	private Configuration config;
	private ViewSrcDirProcessor view;
	private NsPrefixMapperInterface nsPrefixMapper;
	
	public TstAviProcessor(Configuration config)
	{
		this.config=config;
		view = new CliSrcDirProcessorView();
		nsPrefixMapper = new OtrCutNsPrefixMapper();
	}
	
	public void cut()
	{
		SrcDirProcessor test = new SrcDirProcessor(view);
		VideoFiles videoFiles = test.readFiles(new File(config.getString(OtrConfig.dirHqAvi))); 
		JaxbUtil.debug(videoFiles, nsPrefixMapper);
		
		String xmlFile = config.getString("test.xml.aviprocessor.cut");
		logger.debug("Saving to file: "+xmlFile);
		JaxbUtil.save(new File(xmlFile), videoFiles, nsPrefixMapper, true);
	}
	
	public void rename()
	{
		SrcDirProcessor test = new SrcDirProcessor(view);
		VideoFiles videoFiles = test.readFiles(new File(config.getString(OtrConfig.dirRename))); 
		
		JaxbUtil.debug(videoFiles);
		String xmlFile = config.getString("xml.test.rename.1");
		logger.debug("Saving to file: "+xmlFile);
		JaxbUtil.save(new File(xmlFile), videoFiles, true);
	}
	
	public static void main(String args[]) throws ExlpConfigurationException
	{
		Configuration config = OtrClientTstBootstrap.init();
				
		TstAviProcessor test = new TstAviProcessor(config);
		test.cut();
//		test.rename();
	}
}
