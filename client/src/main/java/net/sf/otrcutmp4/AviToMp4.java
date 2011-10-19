package net.sf.otrcutmp4;

import java.io.File;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.otrcutmp4.controller.SrcDirProcessor;
import net.sf.otrcutmp4.controller.batch.CutGenerator;
import net.sf.otrcutmp4.controller.batch.RenameGenerator;
import net.sf.otrcutmp4.controller.cutlist.CutlistChooser;
import net.sf.otrcutmp4.controller.cutlist.CutlistFinder;
import net.sf.otrcutmp4.controller.exception.OtrConfigurationException;
import net.sf.otrcutmp4.controller.exception.OtrInternalErrorException;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.view.cli.CliView;
import net.sf.otrcutmp4.view.interfaces.ViewInterface;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AviToMp4
{
	static Log logger = LogFactory.getLog(AviToMp4.class);
	
	public static enum Quality {HQ,HD}
	public static enum Audio {Mp3,Ac3}
	public static enum Profile {P0,P1}
	
	public static final String exeName = "CutHqAviToMp4";
	
	private Option oHelp,oDebug,oProfile;
	private Options options;
	private OtrConfig otrConfig;
	
	private Profile profile;
	
	public AviToMp4()
	{
		otrConfig = new OtrConfig();
	}
	
	public void parseArguments(String args[]) throws ParseException, OtrConfigurationException, OtrInternalErrorException
	{
		options = createOptions();
		CommandLineParser parser = new PosixParser();
		CommandLine line = null;
	    line = parser.parse(options , args); 
	    
        String configFile = line.getOptionValue("config",OtrConfig.otrConfigName);
        
        if(line.hasOption(oHelp.getOpt())) {printHelp();}
        
        if(line.hasOption(oDebug.getOpt())) {initLogger("log4j.debug.xml");}
        else{initLogger("log4j.xml");}
        
        if(line.hasOption(oProfile.getOpt()))
        {
        	try {profile = Profile.valueOf(line.getOptionValue(oProfile.getOpt()));}
        	catch (IllegalArgumentException e)
        	{
        		logger.warn("Profie "+line.getOptionValue(oProfile.getOpt())+" not available");
        		printHelp();
        	}
        }
        else
        {
        	profile = Profile.P0;
        }
        logger.debug("Using Profile :"+profile);
        
        if(line.hasOption("createConfig")){otrConfig.createDefault(configFile);}
        
        Configuration config = otrConfig.readConfig(configFile);
        
        if(line.hasOption("createDirs")){otrConfig.createDirs(config);}
        otrConfig.checkDirs(config);
        otrConfig.checkTools(config);
         
        ViewInterface view = new CliView();
        
        SrcDirProcessor aviProcessor = new SrcDirProcessor(view);
        CutlistFinder clFinder = new CutlistFinder();
        CutlistChooser clChooser = new CutlistChooser();
        
        VideoFiles vFiles = null;
        
        if(line.hasOption("hq"))
        {
        	vFiles = aviProcessor.readFiles(new File(config.getString(OtrConfig.dirHqAvi)),SrcDirProcessor.VideType.avi); 
        }
        else if(line.hasOption("hd"))
        {
        	aviProcessor.readFiles(new File(config.getString(OtrConfig.dirHdAvi)),SrcDirProcessor.VideType.avi); 
        }
        
        if(line.hasOption("tag"))
        {
        	if(vFiles!=null)
        	{
        		
        	}
        	return;
        }
        
        Audio audio = Audio.Mp3;
        if(line.hasOption("ac3") && !line.hasOption("hd"))
        {
        	logger.warn("ac3 is not allowed for HQ");
        	printHelp();
        }
        if(line.hasOption("ac3"))
        {
        	logger.warn("Remember, the option ac3 is EXPERIMENTAL");
        	logger.warn("http://otrcutmp4.sourceforge.net/future.html");
        	try {Thread.sleep(3000);} catch (InterruptedException e) {logger.error(e);}
        	audio = Audio.Ac3;
        }
        
        if(line.hasOption("hq"))
        {
        	CutGenerator batch = new CutGenerator(config,Quality.HQ,Audio.Mp3,profile);
        	
            vFiles = clFinder.searchCutlist(vFiles);
            vFiles = clChooser.chooseCutlists(vFiles);
            batch.create(vFiles);
        }
        if(line.hasOption("hd"))
        {
        	CutGenerator batch = new CutGenerator(config,Quality.HD,audio,profile);
        	
            vFiles = clFinder.searchCutlist(vFiles);
            vFiles = clChooser.chooseCutlists(vFiles);
            batch.create(vFiles);
        }
        if(line.hasOption("rename"))
        {
        	RenameGenerator batch = new RenameGenerator(config);
        	
        	vFiles = aviProcessor.readFiles(new File(config.getString(OtrConfig.dirMp4Rename)),SrcDirProcessor.VideType.mp4); 
            vFiles = clFinder.searchCutlist(vFiles);
            clChooser.setRenameOutput();
            vFiles = clChooser.chooseFileRename(vFiles);
            
            batch.create(vFiles);
        }
        
        if(!line.hasOption("rename")
        		&& !line.hasOption("hq")
        		&& !line.hasOption("hd")
        		&& !line.hasOption("createDirs")
        		&& !line.hasOption("createConfig"))
        {
        	printHelp();
        }
        
        logger.info("... finished.");
	}
	
	@SuppressWarnings("static-access")
	private Options createOptions()
	{
		oHelp = new Option("help", "Print this message" );
		oDebug = new Option("debug", "Debug output");
		Option oCreate = new Option("createConfig", "Create a default properties file");
		Option oDir = new Option("createDirs", "Create directories specified in configuration file");
		
		
		Option oHQ = new Option("hq", "Convert HQ.AVI to MP4 and apply cutlist");
		Option oHD = new Option("hd", "Convert HD.AVI to MP4 and apply cutlist");
		Option oAc3 = new Option("ac3", "Use Ac3 Audio for HD (experimental)");
		Option oRename = new Option("rename", "Rename downloaded HQ.MP4.cut with cutlist filename");
		
		Option oConfig  = OptionBuilder.withArgName("FILENAME")
						  .hasArg()
						  .withDescription( "Use configuration file FILENAME (optional, default is "+OtrConfig.otrConfigName+")")
						  .create("config");
		
		StringBuffer sb = new StringBuffer();
		for(int i=1;i<Profile.values().length;i++)
		{
			{sb.append(Profile.values()[i].toString()).append(", ");}
		}
		sb.delete(sb.length()-2, sb.length());
		
		oProfile  = OptionBuilder.withArgName("PROFILE")
				  .hasArg()
				  .withDescription("Use (optional) experimental PROFILE: "+sb.toString())
				  .create("profile"); 
		
		Options options = new Options();
		options.addOption(oHelp);
		options.addOption(oDebug);
		options.addOption(oHQ);
		options.addOption(oHD);
		options.addOption(oAc3);
		options.addOption(oRename);
		options.addOption(oCreate);
		options.addOption(oDir);
		options.addOption(oConfig);
		options.addOption(oProfile);
		
		return options;
	}
	
	private void printHelp()
	{
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp( "java -jar "+exeName, options );
		System.exit(0);
	}
	
	private void initLogger(String logConfig)
	{
		LoggerInit loggerInit = new LoggerInit(logConfig);	
		loggerInit.addAltPath("src/main/resources/otrcutmp4");
		loggerInit.addAltPath("otrcutmp4");
		loggerInit.setAllLoadTypes(LoggerInit.LoadType.File,LoggerInit.LoadType.Resource);
		loggerInit.init();
	}
	
	public static void main(String args[]) throws OtrInternalErrorException
	{		
		AviToMp4 hqToMp4 = new AviToMp4();	
		try {hqToMp4.parseArguments(args);}
		catch (ParseException e) {logger.error(e.getMessage());hqToMp4.printHelp();}
		catch (OtrConfigurationException e) {logger.error(e.getMessage());hqToMp4.printHelp();}
	}
}
