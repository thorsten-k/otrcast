package net.sf.otrcutmp4.web.rest;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.ahtutils.web.rest.RestEasyPreemptiveClientExecutor;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.cli.CliCutlistChooserController;
import net.sf.otrcutmp4.controller.cli.CliTestRun;
import net.sf.otrcutmp4.controller.cutlist.DefaultCutlistLoader;
import net.sf.otrcutmp4.controller.processor.SrcDirProcessor;
import net.sf.otrcutmp4.interfaces.controller.CutlistChooser;
import net.sf.otrcutmp4.interfaces.rest.OtrCutRest;
import net.sf.otrcutmp4.interfaces.view.ViewCutlistChooser;
import net.sf.otrcutmp4.interfaces.view.ViewSrcDirProcessor;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.model.xml.series.Videos;
import net.sf.otrcutmp4.test.OtrClientTstBootstrap;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.view.cli.CliCutlistChooserView;
import net.sf.otrcutmp4.view.cli.CliSrcDirProcessorView;

import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestCutRest
{
	final static Logger logger = LoggerFactory.getLogger(TestCutRest.class);
	
	private OtrConfig otrConfig;
	private OtrCutRest rest;
	File fSrcDirProcessorResult,fClFinderResult,flClChooserResult;
	
	public TestCutRest(OtrConfig otrConfig)
	{	
		this.otrConfig=otrConfig;
		
		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
		ClientExecutor clientExecutor = RestEasyPreemptiveClientExecutor.factory("user","pwd");
		rest = ProxyFactory.create(OtrCutRest.class, "http://localhost:8080/otr",clientExecutor);
		
		fSrcDirProcessorResult = new File(otrConfig.getKey(CliTestRun.testSrcDirProcessor));
		fClFinderResult = new File(otrConfig.getKey(CliTestRun.testClFinder));
		flClChooserResult = new File(otrConfig.getKey(CliTestRun.testClChooser));
	}
	
	public void readSrcDir()
	{
		 ViewSrcDirProcessor view = new CliSrcDirProcessorView();
		 SrcDirProcessor srcDirProcessor = new SrcDirProcessor(view);
		 VideoFiles vFiles = srcDirProcessor.scan(new File(otrConfig.getKey(otrConfig.getKey(CliTestRun.testAviDir))));
		 JaxbUtil.info(vFiles);
		 
		 logger.info("Saving result to "+fSrcDirProcessorResult.getAbsolutePath());
		 JaxbUtil.save(fSrcDirProcessorResult , vFiles, true);
	}
	
	public void findCl() throws FileNotFoundException
	{
		VideoFiles vFiles = JaxbUtil.loadJAXB(fSrcDirProcessorResult.getAbsolutePath(), VideoFiles.class);
		DefaultCutlistLoader clFinder = new DefaultCutlistLoader();
		vFiles = clFinder.searchCutlist(vFiles);
		JaxbUtil.info(vFiles);
		
		logger.info("Saving result to "+fSrcDirProcessorResult.getAbsolutePath());
		JaxbUtil.save(fClFinderResult , vFiles, true);
	}
	
	public void upload() throws FileNotFoundException, UtilsProcessingException
	{
		VideoFiles vFiles = JaxbUtil.loadJAXB(fClFinderResult.getAbsolutePath(), VideoFiles.class);
		JaxbUtil.debug(vFiles);
		String s = rest.addCutPackage(vFiles);
		logger.debug("Saved Request with token: "+s);
	}
	
	public void chooseCl() throws FileNotFoundException, UtilsProcessingException
	{
		VideoFiles vFiles = JaxbUtil.loadJAXB(fClFinderResult.getAbsolutePath(), VideoFiles.class);
        ViewCutlistChooser viewCutlistChooser = new CliCutlistChooserView();
        CutlistChooser controllerCutlistChooser = new CliCutlistChooserController(viewCutlistChooser);
        Videos videos = controllerCutlistChooser.chooseCutlists(vFiles);
        JaxbUtil.info(videos);
        
		logger.info("Saving result to "+fSrcDirProcessorResult.getAbsolutePath());
		JaxbUtil.save(flClChooserResult , videos, true);
	}
	
	public static void main(String[] args) throws Exception
	{
		OtrConfig otrConfig = OtrClientTstBootstrap.initOtr();
		TestCutRest test = new TestCutRest(otrConfig);
		
//		test.readSrcDir();
//		test.findCl();
		
		test.upload();
		
//		test.chooseCl();

	}
}