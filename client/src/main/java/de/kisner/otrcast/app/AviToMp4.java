package de.kisner.otrcast.app;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.exception.OtrConfigurationException;
import de.kisner.otrcast.controller.exception.OtrInternalErrorException;
import de.kisner.otrcast.controller.media.SeriesTagger;
import de.kisner.otrcast.interfaces.OtrCastInterface;
import de.kisner.otrcast.interfaces.OtrCastInterface.Profile;
import de.kisner.otrcast.interfaces.controller.CoverManager;
import de.kisner.otrcast.util.OtrConfig;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;

public class AviToMp4 extends AbstractCommandLine
{
	final static Logger logger = LoggerFactory.getLogger(AviToMp4.class);
	
	public static final String exeName = "OtrCutMp4-<version>.jar";
	
	private Option oAc3;

	private OtrConfig otrConfig;
		
	public AviToMp4()
	{
		otrConfig = new OtrConfig();
	}
	
	public void parseArguments(String args[]) throws ParseException, OtrConfigurationException, OtrInternalErrorException, UtilsProcessingException
	{
		options = createOptions();
		CommandLineParser parser = new DefaultParser();
		CommandLine line = parser.parse(options,args); 
	     
        super.parseArguments(line);
        
        OtrCastInterface.Profile profile = Profile.P0;
                
        String configFile = line.getOptionValue("config",OtrConfig.otrConfigName);
        if(line.hasOption("createConfig")){otrConfig.createDefault(configFile);}
        otrConfig.readConfig(configFile);
        
        if(line.hasOption("createDirs")){otrConfig.createDirs();}
        otrConfig.checkCutSettings();
         

        
        CoverManager coverManager = null;
        


        
        
        logger.info("... finished.");
	}

	private Options createOptions()
	{
        super.buildOptions();

		oAc3 = new Option("ac3", "Use AC3 Audio for HD if available (experimental)");

		Option oCreate = new Option("createConfig", "Create a default properties file");
		Option oDir = new Option("createDirs", "Create directories specified in configuration file");

		options.addOption(oHelp);
		options.addOption(oDebug);
		options.addOption(oAc3);
		options.addOption(oCreate);
		options.addOption(oDir);
		options.addOption(oConfig);
		
		return options;
	}
		
	public static void main(String args[]) throws OtrInternalErrorException
	{		
		AviToMp4 avi2Mp4 = new AviToMp4();	
		try {avi2Mp4.parseArguments(args);}
		catch (ParseException e) {logger.error(e.getMessage());avi2Mp4.printHelp();}
		catch (OtrConfigurationException e) {logger.error(e.getMessage());avi2Mp4.printHelp();}
		catch (UtilsProcessingException e) {e.printStackTrace();}
	}
}