package net.sf.otrcutmp4.controller.batch;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.api.rest.OtrCutRest;
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
import de.kisner.otrcast.interfaces.controller.TestPropertyKeys;
import de.kisner.otrcast.interfaces.view.ViewSrcDirProcessor;
import de.kisner.otrcast.model.xml.cut.VideoFiles;
import de.kisner.otrcast.model.xml.series.Videos;
import de.kisner.otrcast.util.OtrBootstrap;
import de.kisner.otrcast.util.OtrConfig;
import de.kisner.otrcast.util.OtrConfig.Credential;
import de.kisner.otrcast.view.cli.CliCutlistChooserView;
import de.kisner.otrcast.view.cli.CliSrcDirProcessorView;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrClientTestBootstrap;

public class CliTestRun
{
	final static Logger logger = LoggerFactory.getLogger(CliTestRun.class);
	
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
		
		ResteasyClient client = new ResteasyClientBuilder().build();
		client.register(new BasicAuthentication(otrConfig.getCredential(Credential.EMAIL,""),"test"));
		ResteasyWebTarget restTarget = client.target("http://localhost:8080/otr");
		rest = restTarget.proxy(OtrCutRest.class);
	}
	
	public void srcDirProcessor()
	{
		File f = new File(config.getString(TestPropertyKeys.testCutAviDir));
		logger.info("SrcDirProcessor with "+f.getAbsolutePath());
		SrcDirProcessor test = new SrcDirProcessor(view);
		VideoFiles videoFiles = test.scan(f); 
		JaxbUtil.debug(videoFiles);
		
		String xmlFile = config.getString(TestPropertyKeys.testCutAviXml);
		logger.debug("Saving to file: "+xmlFile);
		JaxbUtil.save(new File(xmlFile), videoFiles, true);
	}
	
	public void cutlistFinder() throws FileNotFoundException
	{
		VideoFiles input = JaxbUtil.loadJAXB(config.getString(TestPropertyKeys.testCutAviXml),VideoFiles.class);
		
		DefaultCutlistLoader finder = new DefaultCutlistLoader();
		VideoFiles result = finder.searchCutlist(input);
		JaxbUtil.debug(result);
		
		String xmlOutput = config.getString(TestPropertyKeys.testCutXmlCutFinder);
		logger.debug("Saving to file: "+xmlOutput);
		JaxbUtil.save(new File(xmlOutput), result, true);
	}
	
	public void cutlistChooseCli() throws FileNotFoundException, UtilsProcessingException
	{
		VideoFiles input = JaxbUtil.loadJAXB(config.getString(TestPropertyKeys.testCutXmlCutFinder),VideoFiles.class);
		CutlistChooser chooser = new CliCutlistChooserController(new CliCutlistChooserView());
		Videos videos = chooser.chooseCutlists(input);
		JaxbUtil.debug(videos);
		
		String xmlOutput = config.getString(TestPropertyKeys.testCutXmlCutSelected);
		logger.debug("Saving to file: "+xmlOutput);
		JaxbUtil.save(new File(xmlOutput), videos, true);
	}
	
	public void cutlistChooseWeb() throws FileNotFoundException, UtilsProcessingException
	{
		VideoFiles vFiles = JaxbUtil.loadJAXB(config.getString(TestPropertyKeys.testCutXmlCutFinder), VideoFiles.class);
		JaxbUtil.debug(vFiles);
		String token = rest.addCutPackage(vFiles);
		logger.debug("Saved Request with token: "+token);
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		logger.debug("result: "+token);
		Videos videos = rest.findCutPackage(token);
		JaxbUtil.info(videos);
		
		String xmlOutput = config.getString(TestPropertyKeys.testCutXmlCutSelected);
		logger.debug("Saving to file: "+xmlOutput);
		JaxbUtil.save(new File(xmlOutput), videos, true);
		sc.close();
	}
	
	public void cutLoader() throws FileNotFoundException
	{
		Videos videos = JaxbUtil.loadJAXB(config.getString(TestPropertyKeys.testCutXmlCutSelected),Videos.class);
		
		CutlistLoader cutlistLoader = new DefaultCutlistLoader();
		cutlistLoader.loadCuts(videos);
		JaxbUtil.debug(videos);
		
		String xmlOutput = config.getString(TestPropertyKeys.testCutXmlCutLoaded);
		logger.debug("Saving to file: "+xmlOutput);
		JaxbUtil.save(new File(xmlOutput), videos, true);
	}
	
	public void batch() throws OtrConfigurationException, ExlpConfigurationException, FileNotFoundException, OtrInternalErrorException, UtilsProcessingException
	{
		OtrConfig otrConfig = new OtrConfig();
		otrConfig.readConfig(ExlpCentralConfigPointer.getFile(OtrBootstrap.appCode,OtrBootstrap.confCode).getAbsolutePath());
		
		Videos videos = JaxbUtil.loadJAXB(config.getString(TestPropertyKeys.testCutXmlCutLoaded),Videos.class);
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
		logger.warn("NYI: You need to set the file");
		VideoFiles videoFiles = test.scan(new File("x")); 
		
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

//		test.cutlistChooseCli();
//		test.cutlistChooseWeb();
		
//		test.cutLoader();
		test.batch();
		
//		test.scan();
		
//		test.rename();
	}
}