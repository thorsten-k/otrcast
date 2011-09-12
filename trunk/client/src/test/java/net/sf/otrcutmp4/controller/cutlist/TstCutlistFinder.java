package net.sf.otrcutmp4.controller.cutlist;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.ns.NsPrefixMapperInterface;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.model.xml.ns.OtrCutNsPrefixMapper;
import net.sf.otrcutmp4.test.OtrUtilTstBootstrap;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TstCutlistFinder
{ 
	static Log logger = LogFactory.getLog(TstCutlistFinder.class);
	
	private Configuration config;
	private NsPrefixMapperInterface nsPrefixMapper;
	
	public TstCutlistFinder(Configuration config)
	{
		this.config=config;
		nsPrefixMapper = new OtrCutNsPrefixMapper();
	}
	
	public void findCl() throws FileNotFoundException
	{
		String xmlIn = config.getString("test.xml.aviprocessor.cut");
		logger.debug("Loading from file: "+xmlIn);
		VideoFiles vFiles = (VideoFiles)JaxbUtil.loadJAXB(xmlIn, VideoFiles.class);
		
		CutlistFinder finder = new CutlistFinder();
		vFiles = finder.searchCutlist(vFiles);
		JaxbUtil.debug2(this.getClass(), vFiles, nsPrefixMapper);
		
		String xmlOut = config.getString("test.xml.cutlistfinder");
		JaxbUtil.save(new File(xmlOut), vFiles, nsPrefixMapper, true);
	}
	
	public void chooseCl(String type) throws FileNotFoundException, InterruptedException
	{
		String xmlIn = config.getString("xml.test."+type+".2");
		logger.debug("Loading from file: "+xmlIn);
		VideoFiles vFiles = (VideoFiles)JaxbUtil.loadJAXB(xmlIn, VideoFiles.class);
		
		CutlistChooser chooser = new CutlistChooser();
		
		if(type.equals("rename"))
		{
			chooser.setRenameOutput();
			vFiles = chooser.chooseFileRename(vFiles);
		}
		else{logger.warn("No output specified. Do this in test class");}
		
		
		JaxbUtil.debug(this.getClass(),vFiles);
		
		String xmlOut = config.getString("xml.test."+type+".3");
		JaxbUtil.save(new File(xmlOut), vFiles, true);
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = OtrUtilTstBootstrap.init();
		
		TstCutlistFinder test = new TstCutlistFinder(config);
		test.findCl();
//		test.findCl("rename");
//		test.chooseCl("cut");
//		test.chooseCl("rename");
	}
}