package net.sf.otrcutmp4.controller.cutlist;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.ns.NsPrefixMapperInterface;
import net.sf.otrcutmp4.controller.exception.OtrProcessingException;
import net.sf.otrcutmp4.controller.factory.xml.XmlVideoFileFactory;
import net.sf.otrcutmp4.controller.noop.NoopCutlistChooserController;
import net.sf.otrcutmp4.controller.processor.CutlistChooserProcessing;
import net.sf.otrcutmp4.model.xml.OtrCutNsPrefixMapper;
import net.sf.otrcutmp4.model.xml.cut.CutListsAvailable;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.test.OtrClientTstBootstrap;
import net.sf.otrcutmp4.view.noop.NoopCutlistChooserView;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		
		CutlistFinder finder = new CutlistFinder();
		vFiles = finder.searchCutlist(vFiles);
		JaxbUtil.debug(vFiles);
		
		String xmlOut = config.getString("test.xml.cutlistfinder");
		JaxbUtil.save(new File(xmlOut), vFiles, true);
	}
	
	public void chooseCl(String type) throws FileNotFoundException, InterruptedException
	{
		String xmlIn = config.getString("xml.test."+type+".2");
		logger.debug("Loading from file: "+xmlIn);
		VideoFiles vFiles = (VideoFiles)JaxbUtil.loadJAXB(xmlIn, VideoFiles.class);
		
		CutlistChooserProcessing chooser = new CutlistChooserProcessing(new NoopCutlistChooserView(),new NoopCutlistChooserController());
		
		if(type.equals("rename"))
		{
			chooser.setRenameOutput();
			vFiles = chooser.chooseFileRename(vFiles);
		}
		else{logger.warn("No output specified. Do this in test class");}
		
		JaxbUtil.debug(vFiles);
		
		String xmlOut = config.getString("xml.test."+type+".3");
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
		
		CutlistFinder clf = new CutlistFinder();
		CutListsAvailable cla = clf.searchCutlist(vf);
		JaxbUtil.debug(cla);
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = OtrClientTstBootstrap.init();
		
		TstCutlistFinder test = new TstCutlistFinder(config);
//		test.findCl();
//		test.findCl("rename");
//		test.chooseCl("cut");
//		test.chooseCl("rename");
		test.find();
	}
}