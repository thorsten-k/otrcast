package net.sf.otrcutmp4.controller.cli;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.AviToMp4.Profile;
import net.sf.otrcutmp4.controller.batch.BatchGenerator;
import net.sf.otrcutmp4.controller.cli.CliCutlistChooserController;
import net.sf.otrcutmp4.controller.cutlist.DefaultCutlistLoader;
import net.sf.otrcutmp4.controller.exception.OtrConfigurationException;
import net.sf.otrcutmp4.controller.exception.OtrInternalErrorException;
import net.sf.otrcutmp4.controller.processor.SrcDirProcessor;
import net.sf.otrcutmp4.interfaces.controller.CutlistChooser;
import net.sf.otrcutmp4.interfaces.controller.CutlistLoader;
import net.sf.otrcutmp4.interfaces.view.ViewSrcDirProcessor;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.model.xml.series.Videos;
import net.sf.otrcutmp4.test.OtrClientTstBootstrap;
import net.sf.otrcutmp4.util.OtrBootstrap;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.view.cli.CliCutlistChooserView;
import net.sf.otrcutmp4.view.cli.CliSrcDirProcessorView;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliTestRun
{
	final static Logger logger = LoggerFactory.getLogger(CliTestRun.class);
	
	public static String testAviDir = "test.dir.avi";
	public static String testSrcDirProcessor = "test.xml.scrDirProcessor";
	public static String testClFinder = "test.xml.cutlistFinder";
	public static String testClChooser = "test.xml.clChooser";
	public static String testClLoader = "test.xml.clLoader";
	
	private Configuration config;
	private ViewSrcDirProcessor view;
	
	public CliTestRun(Configuration config)
	{
		this.config=config;
		
		view = new CliSrcDirProcessorView();
	}
	
	public void srcDirProcessor()
	{
		SrcDirProcessor test = new SrcDirProcessor(view);
		VideoFiles videoFiles = test.scan(new File(config.getString(CliTestRun.testAviDir))); 
		JaxbUtil.debug(videoFiles);
		
		String xmlFile = config.getString(CliTestRun.testSrcDirProcessor);
		logger.debug("Saving to file: "+xmlFile);
		JaxbUtil.save(new File(xmlFile), videoFiles, true);
	}
	
	public void cutlistFinder() throws FileNotFoundException
	{
		VideoFiles input = JaxbUtil.loadJAXB(config.getString(CliTestRun.testSrcDirProcessor),VideoFiles.class);
		
		DefaultCutlistLoader finder = new DefaultCutlistLoader();
		VideoFiles result = finder.searchCutlist(input);
		JaxbUtil.debug(result);
		
		String xmlOutput = config.getString(CliTestRun.testClFinder);
		logger.debug("Saving to file: "+xmlOutput);
		JaxbUtil.save(new File(xmlOutput), result, true);
	}
	
	public void cutlistChooser() throws FileNotFoundException, UtilsProcessingException
	{
		VideoFiles input = JaxbUtil.loadJAXB(config.getString(CliTestRun.testClFinder),VideoFiles.class);
		CutlistChooser chooser = new CliCutlistChooserController(new CliCutlistChooserView());
		Videos videos = chooser.chooseCutlists(input);
		JaxbUtil.debug(videos);
		
		String xmlOutput = config.getString(CliTestRun.testClChooser);
		logger.debug("Saving to file: "+xmlOutput);
		JaxbUtil.save(new File(xmlOutput), videos, true);
	}
	
	public void cutlistLoader() throws FileNotFoundException
	{
		Videos videos = JaxbUtil.loadJAXB(config.getString(CliTestRun.testClChooser),Videos.class);
		
		CutlistLoader cutlistLoader = new DefaultCutlistLoader();
		cutlistLoader.loadCuts(videos);
		JaxbUtil.debug(videos);
		
		String xmlOutput = config.getString(CliTestRun.testClLoader);
		logger.debug("Saving to file: "+xmlOutput);
		JaxbUtil.save(new File(xmlOutput), videos, true);
	}
	
	public void batch() throws OtrConfigurationException, ExlpConfigurationException, FileNotFoundException, OtrInternalErrorException
	{
		OtrConfig otrConfig = new OtrConfig();
		otrConfig.readConfig(ExlpCentralConfigPointer.getFile(OtrBootstrap.appCode,OtrBootstrap.confCode).getAbsolutePath());
		
		Videos videos = JaxbUtil.loadJAXB(config.getString(CliTestRun.testClLoader),Videos.class);
		BatchGenerator batch = new BatchGenerator(otrConfig,Profile.P0);
    	batch.build(videos);
	}
	
	public void rename()
	{
		SrcDirProcessor test = new SrcDirProcessor(view);
		VideoFiles videoFiles = test.scan(new File(config.getString(OtrConfig.dirRename))); 
		
		JaxbUtil.debug(videoFiles);
		String xmlFile = config.getString("xml.test.rename.1");
		logger.debug("Saving to file: "+xmlFile);
		JaxbUtil.save(new File(xmlFile), videoFiles, true);
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = OtrClientTstBootstrap.init();
				
		CliTestRun test = new CliTestRun(config);
//		test.srcDirProcessor();
//		test.cutlistFinder();
//		test.cutlistChooser();
//		test.cutlistLoader();
		test.batch();
		
//		test.rename();
	}
}
