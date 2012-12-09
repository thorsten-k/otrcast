package net.sf.otrcutmp4.controller.cli;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.interfaces.controller.CutlistChooser;
import net.sf.otrcutmp4.interfaces.view.ViewCutlistChooser;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.model.xml.series.Videos;
import net.sf.otrcutmp4.test.OtrClientTstBootstrap;
import net.sf.otrcutmp4.view.cli.CliCutlistChooserView;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestCliCutlistChooserController
{ 
	final static Logger logger = LoggerFactory.getLogger(TestCliCutlistChooserController.class);
	
	private Configuration config;
	
	public TestCliCutlistChooserController(Configuration config)
	{
		this.config=config;
	}
	
	public void chooseCutlists(String type2) throws FileNotFoundException, UtilsProcessingException
	{
		String xmlIn = config.getString("test.xml.clFinderResult");
		logger.debug("Loading from file: "+xmlIn);
		VideoFiles vFiles = (VideoFiles)JaxbUtil.loadJAXB(xmlIn, VideoFiles.class);
		JaxbUtil.info(vFiles);
		
		ViewCutlistChooser viewCutlistChooser = new CliCutlistChooserView();
		CutlistChooser chooser = new CliCutlistChooserController(viewCutlistChooser);
		Videos videos = chooser.chooseCutlists(vFiles);
		JaxbUtil.info(videos);		
		
		String xmlOut = config.getString("test.xml.clChooserResult");
		JaxbUtil.save(new File(xmlOut), videos, true);
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = OtrClientTstBootstrap.init();
		
		TestCliCutlistChooserController test = new TestCliCutlistChooserController(config);
		test.chooseCutlists("cut");
	}
}