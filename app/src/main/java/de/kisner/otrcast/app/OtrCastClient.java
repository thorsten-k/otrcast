package de.kisner.otrcast.app;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.OtrCastBootstrap;
import de.kisner.otrcast.controller.exception.OtrConfigurationException;
import de.kisner.otrcast.controller.media.McLibraryTagger;
import de.kisner.otrcast.controller.processor.SrcDirProcessor;
import de.kisner.otrcast.controller.web.rest.WebAviScanner;
import de.kisner.otrcast.factory.xml.otr.XmlOtrIdFactory;
import de.kisner.otrcast.interfaces.view.ViewSrcDirProcessor;
import de.kisner.otrcast.model.xml.OtrCastNsPrefixMapper;
import de.kisner.otrcast.util.OtrConfig;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.ahtutils.util.cli.UtilsCliOption;
import net.sf.exlp.util.xml.JaxbUtil;

public class OtrCastClient
{
	final static Logger logger = LoggerFactory.getLogger(OtrCastClient.class);
	
	public static final String exeName = "OtrCastClient-<version>.jar";

	
	private UtilsCliOption uOption;
	private OtrConfig otrConfig;
	
	private Option oTag,oScan;
	
	public OtrCastClient(UtilsCliOption uOption)
	{
		this.uOption=uOption;
		otrConfig = new OtrConfig();
	}
	
	public void parseArguments(String args[]) throws Exception
	{
		createOptions();
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(uOption.getOptions(), args);

		uOption.handleHelp(cmd);
		uOption.handleLogger(cmd);
       
        otrConfig.readConfig(uOption.initConfig(cmd, OtrCastBootstrap.xmlConfig));
        otrConfig.checkMcSettings();
        
        ViewSrcDirProcessor view = null;//= new CliSrcDirProcessorView();
        SrcDirProcessor srcDirProcessor = new SrcDirProcessor(view);
        
        if(cmd.hasOption(oTag.getOpt()) && uOption.allowAppStart())
        {
        		tagMediathek(otrConfig.getDir(OtrConfig.Dir.MC));
        }
        
        if(cmd.hasOption(oScan.getOpt()) && uOption.allowAppStart())
	    	{
	    		otrConfig.checkEmailPwd();
	    		WebAviScanner was = new WebAviScanner(otrConfig);
	    		srcDirProcessor.addValidSuffix(XmlOtrIdFactory.typeOtrkey);
	    		was.scan(srcDirProcessor);
	    	}
        
        if(!uOption.isAppStarted())
        {
        		uOption.help();
        }
	}

	private void tagMediathek(File f) throws IOException
	{
		File fMc = otrConfig.getDir(OtrConfig.Dir.MC);
		File fMcXmlLib = otrConfig.getFile(OtrConfig.fileMcXmlLib);
		File fTmp = new File("/tmp");
		logger.info("Scanning for MP4 in "+f.getAbsolutePath());
		logger.info(" ... saving to : "+fMcXmlLib.getAbsolutePath());
		
		McLibraryTagger tagger = new McLibraryTagger(fTmp,null);
		tagger.scan(fMc);
		tagger.saveToXml(fMcXmlLib);
	}

	private void createOptions()
	{
		uOption.buildHelp();
        uOption.buildDebug();
        uOption.buildConfig();
        
        oTag = Option.builder("tag").required(false).desc("Tags the MP4 Library").build(); uOption.getOptions().addOption(oTag);
        oScan = Option.builder("scan").required(false).desc("Scans the MP4 Library").build(); uOption.getOptions().addOption(oScan);
	}

	public static void main(String args[]) throws Exception
	{
		JaxbUtil.setNsPrefixMapper(new OtrCastNsPrefixMapper());
		
		UtilsCliOption uOption = new UtilsCliOption(de.kisner.otrcast.api.Version.class.getPackage().getImplementationVersion());
		uOption.setLog4jPaths("otrcast-app/config");
		
		OtrCastClient otrMc = new OtrCastClient(uOption);
		try {otrMc.parseArguments(args);}
		catch (ParseException e) {logger.error(e.getMessage());uOption.help();}
		catch (OtrConfigurationException e) {logger.error(e.getMessage());uOption.help();}
		catch (UtilsProcessingException e) {e.printStackTrace();}
	}
}