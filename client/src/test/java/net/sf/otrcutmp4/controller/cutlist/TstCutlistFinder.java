package net.sf.otrcutmp4.controller.cutlist;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrClientTestBootstrap;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.cli.CliCutlistChooserController;
import de.kisner.otrcast.controller.cutlist.DefaultCutlistLoader;
import de.kisner.otrcast.controller.exception.OtrProcessingException;
import de.kisner.otrcast.factory.xml.XmlVideoFileFactory;
import de.kisner.otrcast.interfaces.controller.CutlistChooser;
import de.kisner.otrcast.interfaces.view.ViewCutlistChooser;
import de.kisner.otrcast.model.xml.cut.CutLists;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.cut.VideoFiles;
import de.kisner.otrcast.view.cli.CliCutlistChooserView;

public class TstCutlistFinder
{ 
	final static Logger logger = LoggerFactory.getLogger(TstCutlistFinder.class);
	
	private Configuration config;
	
	public TstCutlistFinder(Configuration config)
	{
		this.config=config;
	}
	
	public void findCl() throws FileNotFoundException
	{
		String xmlIn = config.getString("test.xml.aviprocessor.cut");
		logger.debug("Loading from file: "+xmlIn);
		VideoFiles vFiles = (VideoFiles)JaxbUtil.loadJAXB(xmlIn, VideoFiles.class);
		
		DefaultCutlistLoader finder = new DefaultCutlistLoader();
		vFiles = finder.searchCutlist(vFiles);
		JaxbUtil.debug(vFiles);
		
		String xmlOut = config.getString("test.xml.cutlistfinder");
		JaxbUtil.save(new File(xmlOut), vFiles, true);
	}
	
	public void chooseCl(String type) throws FileNotFoundException, UtilsProcessingException
	{
		String xmlIn = config.getString("test.xml.aviprocessor");
		logger.debug("Loading from file: "+xmlIn);
		VideoFiles vFiles = (VideoFiles)JaxbUtil.loadJAXB(xmlIn, VideoFiles.class);
		
		ViewCutlistChooser viewCutlistChooser = new CliCutlistChooserView();
		CutlistChooser chooser = new CliCutlistChooserController(viewCutlistChooser);
		chooser.chooseCutlists(vFiles);
		
		JaxbUtil.info(vFiles);		
		String xmlOut = config.getString("test.xml.cutlistfinder");
		JaxbUtil.save(new File(xmlOut), vFiles, true);
	}
	
	public void find() throws OtrProcessingException
	{
		find("Supernatural_11.10.24_22-05_pro7_55_TVOON_DE.mpg.HQ.avi");
		
	}
	
	public void find(String clKey) throws OtrProcessingException
	{
		
		VideoFile vf = XmlVideoFileFactory.create(clKey);
		JaxbUtil.debug(vf);
		
		DefaultCutlistLoader clf = new DefaultCutlistLoader();
		CutLists cla = clf.searchCutlist(vf);
		JaxbUtil.debug(cla);
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = OtrClientTestBootstrap.init();
		
		TstCutlistFinder test = new TstCutlistFinder(config);
//		test.findCl();
//		test.findCl("rename");
		test.chooseCl("cut");
//		test.chooseCl("rename");
		test.find();
	}
}