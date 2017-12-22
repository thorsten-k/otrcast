package de.kisner.otrcast.app;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.batch.BatchGenerator;
import de.kisner.otrcast.controller.cli.CliCutlistChooserController;
import de.kisner.otrcast.controller.cover.FileSystemCoverManager;
import de.kisner.otrcast.controller.cover.FileSystemWebCoverManager;
import de.kisner.otrcast.controller.cutlist.JdomCutlistLoader;
import de.kisner.otrcast.controller.exception.OtrConfigurationException;
import de.kisner.otrcast.controller.exception.OtrInternalErrorException;
import de.kisner.otrcast.controller.processor.SeriesTagger;
import de.kisner.otrcast.controller.processor.SrcDirProcessor;
import de.kisner.otrcast.controller.web.rest.WebAviScanner;
import de.kisner.otrcast.controller.web.rest.WebCutlistChooserController;
import de.kisner.otrcast.factory.xml.otr.XmlOtrIdFactory;
import de.kisner.otrcast.interfaces.OtrCastInterface;
import de.kisner.otrcast.interfaces.OtrCastInterface.Profile;
import de.kisner.otrcast.interfaces.controller.CoverManager;
import de.kisner.otrcast.interfaces.controller.CutlistChooser;
import de.kisner.otrcast.interfaces.controller.CutlistLoader;
import de.kisner.otrcast.interfaces.view.ViewCutlistChooser;
import de.kisner.otrcast.interfaces.view.ViewSrcDirProcessor;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.cut.VideoFiles;
import de.kisner.otrcast.model.xml.series.Videos;
import de.kisner.otrcast.util.OtrConfig;
import de.kisner.otrcast.util.OtrConfig.Dir;
import de.kisner.otrcast.view.cli.CliCutlistChooserView;
import de.kisner.otrcast.view.cli.CliSrcDirProcessorView;
import de.kisner.otrcast.view.web.WebCutlistChooserView;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.exlp.util.xml.JaxbUtil;

public class AviToMp4 extends AbstractCommandLine
{
	final static Logger logger = LoggerFactory.getLogger(AviToMp4.class);
	
	public static final String exeName = "OtrCutMp4-<version>.jar";
	
	private Option oAc3;
	private Option oTagMp4,oTag;

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
        
        if(line.hasOption(oTagMp4.getOpt()))
        {
	        	logger.info("Tagging MP4");
	        	SeriesTagger tagger = new SeriesTagger(otrConfig,profile,coverManager);
	        	tagger.tag(new Long(line.getOptionValue(oTagMp4.getOpt())));
	        	return;
        }

        
        
        logger.info("... finished.");
	}

	private Options createOptions()
	{
        super.buildOptions();

		oAc3 = new Option("ac3", "Use AC3 Audio for HD if available (experimental)");
		oTag = new Option("tag","Tag processed files. Login required!");
		
		Option oCreate = new Option("createConfig", "Create a default properties file");
		Option oDir = new Option("createDirs", "Create directories specified in configuration file");

		oTagMp4  = Option.builder("tagMp4").required(false)
					.hasArg(true).argName("TOKEN").desc("Tag MP4 file. Login required! (TOKEN format is ID-FILE)")
					.build();


		
		options.addOption(oHelp);
		options.addOption(oDebug);
		options.addOption(oAc3);
		options.addOption(oCreate);
		options.addOption(oDir);
		options.addOption(oConfig);
		options.addOption(oTag);
		options.addOption(oTagMp4);
		
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