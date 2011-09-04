package net.sf.otrcutmp4.test;

import java.io.File;

import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.ns.NsPrefixMapperInterface;
import net.sf.otrcutmp4.controller.SrcDirProcessor;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.model.xml.ns.OtrCutNsPrefixMapper;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.view.cli.CliView;
import net.sf.otrcutmp4.view.interfaces.ViewInterface;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TstAviProcessor
{
	static Log logger = LogFactory.getLog(TstAviProcessor.class);
	
	private Configuration config;
	private ViewInterface view;
	private NsPrefixMapperInterface nsPrefixMapper;
	
	public TstAviProcessor(Configuration config)
	{
		this.config=config;
		view = new CliView();
		nsPrefixMapper = new OtrCutNsPrefixMapper();
	}
	
	public void cut()
	{
		SrcDirProcessor test = new SrcDirProcessor(view);
		VideoFiles videoFiles = test.readFiles(new File(config.getString(OtrConfig.dirHqAvi)),SrcDirProcessor.VideType.avi); 
		JaxbUtil.debug2(this.getClass(), videoFiles, nsPrefixMapper);
		
		String xmlFile = config.getString("test.xml.aviprocessor.cut");
		logger.debug("Saving to file: "+xmlFile);
		JaxbUtil.save(new File(xmlFile), videoFiles, nsPrefixMapper, true);
	}
	
	public void rename()
	{
		SrcDirProcessor test = new SrcDirProcessor(view);
		VideoFiles videoFiles = test.readFiles(new File(config.getString(OtrConfig.dirMp4Rename)),SrcDirProcessor.VideType.mp4); 
		
		JaxbUtil.debug(TstAviProcessor.class,videoFiles);
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
