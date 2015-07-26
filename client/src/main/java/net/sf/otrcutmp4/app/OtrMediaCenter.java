package net.sf.otrcutmp4.app;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.otrcutmp4.controller.OtrCutMp4Bootstrap;
import net.sf.otrcutmp4.controller.exception.OtrConfigurationException;
import net.sf.otrcutmp4.controller.processor.mc.MediaCenterScanner;
import net.sf.otrcutmp4.util.OtrConfig;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class OtrMediaCenter extends AbstractCommandLine
{
	final static Logger logger = LoggerFactory.getLogger(OtrMediaCenter.class);
	
	public static final String exeName = "OtrMediaCenter-<version>.jar";

	private static enum McMode {managed,unmanged}
	
	private OtrConfig otrConfig;
	
	private Option oMediaCenter,oMediaCenterMode,oRetagger;
	
	public OtrMediaCenter()
	{
		otrConfig = new OtrConfig();
	}
	
	public void parseArguments(String args[]) throws Exception
	{
		
		createOptions();
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options , args);

        super.parseArguments(cmd);
       
        String configFile = cmd.getOptionValue(oConfig.getOpt(),OtrConfig.otrConfigName);
        
        otrConfig.readConfig(configFile);
        otrConfig.checkMcSettings();
        
        if(cmd.hasOption(oMediaCenter.getOpt()))
        {
        	McMode mode = null;
        	if(cmd.hasOption(oMediaCenterMode.getOpt()))
        	{
        		String s = cmd.getOptionValue(oMediaCenterMode.getOpt(),McMode.unmanged.toString());
        		mode = McMode.valueOf(s);
        	}
        	logger.info("Starting with mode "+mode+" (NYI)");
        	
        	OtrCutMp4Bootstrap.buildEmf(otrConfig).createEntityManager();
        	scanMediathek(otrConfig.getDir(OtrConfig.Dir.MC));
        }
        else if(cmd.hasOption(oRetagger.getOpt()))
        {
        	OtrCutMp4Bootstrap.buildEmf(otrConfig).createEntityManager();
        	scanMediathek(otrConfig.getDir(OtrConfig.Dir.MC));
        }
       

//        McIncomingHotfolder hot = new McIncomingHotfolder(otrConfig);
//		hot.addRoute();
//		hot.startHotFolder();
        Thread.sleep(1000);
	}

	public void scanMediathek(File f)
	{
		logger.info("Scanning for MP4 in "+f.getAbsolutePath());
		MediaCenterScanner mcs = new MediaCenterScanner(OtrCutMp4Bootstrap.buildEmf().createEntityManager());
		mcs.scan(f);
	}

	private void createOptions()
	{
        super.buildOptions();

		options.addOption(oHelp);
        options.addOption(oConfig);
        
        oMediaCenter  = Option.builder("mc").required(false).desc("Starts Mediacenter").build();
        oRetagger  = Option.builder("tag").required(false).desc("Re-Tags the MP4 Library").build();
        
        oMediaCenterMode  = Option.builder("mode").required(false)
				.hasArg(true).argName("MODE").desc("only for mc, MODE can be "+McMode.managed+" or "+McMode.unmanged)
				.build();
        
        options.addOption(oMediaCenter);
        options.addOption(oMediaCenterMode);
        options.addOption(oRetagger);
	}

	public static void main(String args[]) throws Exception
	{
		OtrMediaCenter otrMc = new OtrMediaCenter();
		try {otrMc.parseArguments(args);}
		catch (ParseException e) {logger.error(e.getMessage());otrMc.printHelp();}
		catch (OtrConfigurationException e) {logger.error(e.getMessage());otrMc.printHelp();}
		catch (UtilsProcessingException e) {e.printStackTrace();}
	}
}