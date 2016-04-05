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
import de.kisner.otrcast.model.xml.OtrCutNsPrefixMapper;
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
	
	private Option oScan;
	
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
        
        if(cmd.hasOption(oScan.getOpt()) && uOption.allowAppStart())
        {
        	scanMediathek(otrConfig.getDir(OtrConfig.Dir.MC));
        }
        
        if(!uOption.isAppStarted())
        {
        	uOption.help();
        }
	}

	private void scanMediathek(File f) throws IOException
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
        
        oScan  = Option.builder("tag").required(false).desc("Scan the MP4 Library").build();
        
        uOption.getOptions().addOption(oScan);
	}

	public static void main(String args[]) throws Exception
	{
		JaxbUtil.setNsPrefixMapper(new OtrCutNsPrefixMapper());
		
		UtilsCliOption uOption = new UtilsCliOption(de.kisner.otrcast.api.Version.class.getPackage().getImplementationVersion());
		uOption.setLog4jPaths("config.otrcast-client");
		
		OtrCastClient otrMc = new OtrCastClient(uOption);
		try {otrMc.parseArguments(args);}
		catch (ParseException e) {logger.error(e.getMessage());uOption.help();}
		catch (OtrConfigurationException e) {logger.error(e.getMessage());uOption.help();}
		catch (UtilsProcessingException e) {e.printStackTrace();}
	}
}