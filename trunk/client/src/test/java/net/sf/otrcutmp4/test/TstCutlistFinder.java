package net.sf.otrcutmp4.test;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.ConfigLoader;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.cutlist.CutlistChooser;
import net.sf.otrcutmp4.cutlist.CutlistFinder;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TstCutlistFinder
{ 
	static Log logger = LogFactory.getLog(TstCutlistFinder.class);
	
	private Configuration config;
	
	public TstCutlistFinder(Configuration config)
	{
		this.config=config;
	}
	
	public void findCl(String type) throws FileNotFoundException
	{
		String xmlIn = config.getString("xml.test."+type+".1");
		logger.debug("Loading from file: "+xmlIn);
		VideoFiles vFiles = (VideoFiles)JaxbUtil.loadJAXB(xmlIn, VideoFiles.class);
		
		CutlistFinder finder = new CutlistFinder();
		vFiles = finder.searchCutlist(vFiles);
		JaxbUtil.debug(this.getClass(),vFiles);
		
		String xmlOut = config.getString("xml.test."+type+".2");
		JaxbUtil.save(new File(xmlOut), vFiles, true);
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
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();
		
		ConfigLoader.add("src/test/resources/properties/user.otr.properties");
		ConfigLoader.add("src/test/resources/properties/user.properties");
		Configuration config = ConfigLoader.init();
		
		TstCutlistFinder test = new TstCutlistFinder(config);
//		test.findCl("cut");
//		test.findCl("rename");
//		test.chooseCl("cut");
		test.chooseCl("rename");
	}
}