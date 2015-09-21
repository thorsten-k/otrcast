package net.sf.otrcutmp4.controller.batch;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.ahtutils.web.rest.auth.RestEasyPreemptiveClientExecutor;
import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrClientTestBootstrap;

import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.app.AviToMp4.Profile;
import de.kisner.otrcast.controller.batch.BatchGenerator;
import de.kisner.otrcast.controller.cli.CliCutlistChooserController;
import de.kisner.otrcast.controller.cutlist.DefaultCutlistLoader;
import de.kisner.otrcast.controller.exception.OtrConfigurationException;
import de.kisner.otrcast.controller.exception.OtrInternalErrorException;
import de.kisner.otrcast.controller.processor.SrcDirProcessor;
import de.kisner.otrcast.controller.web.WebAviScanner;
import de.kisner.otrcast.factory.xml.otr.XmlOtrIdFactory;
import de.kisner.otrcast.interfaces.controller.CutlistChooser;
import de.kisner.otrcast.interfaces.controller.CutlistLoader;
import de.kisner.otrcast.interfaces.rest.OtrCutRest;
import de.kisner.otrcast.interfaces.view.ViewSrcDirProcessor;
import de.kisner.otrcast.model.xml.cut.VideoFiles;
import de.kisner.otrcast.model.xml.series.Videos;
import de.kisner.otrcast.util.OtrBootstrap;
import de.kisner.otrcast.util.OtrConfig;
import de.kisner.otrcast.util.OtrConfig.Credential;
import de.kisner.otrcast.view.cli.CliCutlistChooserView;
import de.kisner.otrcast.view.cli.CliSrcDirProcessorView;

public class CliTestRun
{
	final static Logger logger = LoggerFactory.getLogger(CliTestRun.class);
	
	public static String testAviDir = "test.dir.avi";
	public static String testSrcDirProcessor = "test.xml.scrDirProcessor";
	public static String testClFinder = "test.xml.cutlistFinder";
	public static String testClChooser = "test.xml.clChooser";
	public static String testClLoader = "test.xml.clLoader";
	
	private Configuration config;
	private OtrConfig otrConfig;
	private ViewSrcDirProcessor view;
	private OtrCutRest rest;
	
	public CliTestRun(Configuration config) throws OtrConfigurationException, ExlpConfigurationException
	{
		this.config=config;
		view = new CliSrcDirProcessorView();
		
		otrConfig = new OtrConfig();
		otrConfig.readConfig(ExlpCentralConfigPointer.getFile(OtrBootstrap.appCode,OtrBootstrap.confCode).getAbsolutePath());
		
		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
		ClientExecutor clientExecutor = RestEasyPreemptiveClientExecutor.factory(
				otrConfig.getCredential(Credential.EMAIL,""),
				"test");
		rest = ProxyFactory.create(OtrCutRest.class, "http://localhost:8080/otr",clientExecutor);
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
	
	public void cliChooser() throws FileNotFoundException, UtilsProcessingException
	{
		VideoFiles input = JaxbUtil.loadJAXB(config.getString(CliTestRun.testClFinder),VideoFiles.class);
		CutlistChooser chooser = new CliCutlistChooserController(new CliCutlistChooserView());
		Videos videos = chooser.chooseCutlists(input);
		JaxbUtil.debug(videos);
		
		String xmlOutput = config.getString(CliTestRun.testClChooser);
		logger.debug("Saving to file: "+xmlOutput);
		JaxbUtil.save(new File(xmlOutput), videos, true);
	}
	
	public void restChooser() throws FileNotFoundException, UtilsProcessingException
	{
		VideoFiles vFiles = JaxbUtil.loadJAXB(config.getString(CliTestRun.testClFinder), VideoFiles.class);
		JaxbUtil.debug(vFiles);
		String token = rest.addCutPackage(vFiles);
		logger.debug("Saved Request with token: "+token);
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		logger.debug("result: "+token);
		Videos videos = rest.findCutPackage(token);
		JaxbUtil.info(videos);
		
		String xmlOutput = config.getString(CliTestRun.testClChooser);
		logger.debug("Saving to file: "+xmlOutput);
		JaxbUtil.save(new File(xmlOutput), videos, true);
	}
	
	public void cutLoader() throws FileNotFoundException
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
		BatchGenerator batch = new BatchGenerator(otrConfig,Profile.P0,false);
    	batch.build(videos);
	}
	
	public void scan() throws OtrConfigurationException, ExlpConfigurationException, FileNotFoundException, OtrInternalErrorException
	{
		SrcDirProcessor srcDirProcessor = new SrcDirProcessor(view);
		srcDirProcessor.addValidSuffix(XmlOtrIdFactory.typeOtrkey);
		WebAviScanner scanner = new WebAviScanner(otrConfig);
		VideoFiles vf = scanner.scan(srcDirProcessor);
		JaxbUtil.info(vf);
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
		Configuration config = OtrClientTestBootstrap.init();
				
		CliTestRun test = new CliTestRun(config);
//		test.srcDirProcessor();
//		test.cutlistFinder();

//		test.cliChooser();
//		test.restChooser();
		
//		test.cutLoader();
//		test.batch();
		
		test.scan();
		
//		test.rename();
	}
}
