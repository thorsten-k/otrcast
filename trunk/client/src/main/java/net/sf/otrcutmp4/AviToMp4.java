package net.sf.otrcutmp4;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.batch.BatchGenerator;
import net.sf.otrcutmp4.controller.batch.RenameGenerator;
import net.sf.otrcutmp4.controller.cli.CliCutlistChooserController;
import net.sf.otrcutmp4.controller.cutlist.DefaultCutlistLoader;
import net.sf.otrcutmp4.controller.exception.OtrConfigurationException;
import net.sf.otrcutmp4.controller.exception.OtrInternalErrorException;
import net.sf.otrcutmp4.controller.processor.SrcDirProcessor;
import net.sf.otrcutmp4.controller.web.WebAviScanner;
import net.sf.otrcutmp4.controller.web.WebCutlistChooserController;
import net.sf.otrcutmp4.interfaces.controller.CutlistChooser;
import net.sf.otrcutmp4.interfaces.controller.CutlistLoader;
import net.sf.otrcutmp4.interfaces.view.ViewCutlistChooser;
import net.sf.otrcutmp4.interfaces.view.ViewSrcDirProcessor;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.model.xml.series.Videos;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.OtrConfig.Dir;
import net.sf.otrcutmp4.view.cli.CliCutlistChooserView;
import net.sf.otrcutmp4.view.cli.CliSrcDirProcessorView;
import net.sf.otrcutmp4.view.web.WebCutlistChooserView;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AviToMp4
{
	final static Logger logger = LoggerFactory.getLogger(AviToMp4.class);
	
	public static enum Profile {P0,P1}
	
	public static final String exeName = "CutHqAviToMp4";
	
	private Option oHelp,oDebug,oProfile,oWeb;
	private Option oAc3,oRename,oMp4,oTag;
	private Option oScan;
	private Options options;
	private OtrConfig otrConfig;
	
	private Profile profile;
	
	public AviToMp4()
	{
		otrConfig = new OtrConfig();
	}
	
	public void parseArguments(String args[]) throws ParseException, OtrConfigurationException, OtrInternalErrorException, UtilsProcessingException
	{
		options = createOptions();
		CommandLineParser parser = new PosixParser();
		CommandLine line = parser.parse(options , args); 
	    
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
        
        otrConfig.readConfig(configFile);
        
        if(line.hasOption("createDirs")){otrConfig.createDirs();}
        otrConfig.checkConfigSettings();
         
        ViewSrcDirProcessor view = new CliSrcDirProcessorView();
        
        SrcDirProcessor srcDirProcessor = new SrcDirProcessor(view);
        
        DefaultCutlistLoader clFinder = new DefaultCutlistLoader();
        VideoFiles vFiles;
        
        if(line.hasOption(oScan.getOpt()))
    	{
    		otrConfig.checkEmailPwd();
    		WebAviScanner was = new WebAviScanner(otrConfig);
    		was.scan(srcDirProcessor);
         }
        
        if(line.hasOption(oRename.getOpt()))
        {
        	vFiles = srcDirProcessor.scan(otrConfig.getDir(Dir.RENAME)); 
            JaxbUtil.debug(vFiles);
            
        	RenameGenerator batchRen = new RenameGenerator(otrConfig,profile);	
        	batchRen.create(vFiles);
            logger.trace("RENAME finished");
            return;
        }

        if(line.hasOption(oMp4.getOpt()))
        {
	        vFiles = srcDirProcessor.scan(otrConfig.getDir(Dir.AVI));
	        if(line.hasOption("ac3"))
	        {
	        	logger.warn("Remember, the option ac3 is EXPERIMENTAL");
	        	logger.warn("http://otrcutmp4.sourceforge.net/future.html");
	        	try {Thread.sleep(3000);} catch (InterruptedException e) {logger.error("",e);}
	        }
	        else
	        {
	        	for(VideoFile vf : vFiles.getVideoFile()){vf.getOtrId().getFormat().setAc3(false);}
	        }
	        
	        vFiles = clFinder.searchCutlist(vFiles);
	        
	        ViewCutlistChooser viewCutlistChooser = null;
	        CutlistChooser controllerCutlistChooser = null;
	              
	        if(line.hasOption(oWeb.getOpt()))
	        {
	        	viewCutlistChooser = new WebCutlistChooserView();
	        	controllerCutlistChooser = new WebCutlistChooserController(viewCutlistChooser,otrConfig);
	        }
	        else
	        {
	        	viewCutlistChooser = new CliCutlistChooserView();
	        	controllerCutlistChooser = new CliCutlistChooserController(viewCutlistChooser);
	        }
	    	
	    	Videos videos = controllerCutlistChooser.chooseCutlists(vFiles);
	    	JaxbUtil.warn(videos);
	    	
	    	CutlistLoader cutlistLoader = new DefaultCutlistLoader();;
	    	cutlistLoader.loadCuts(videos);       
	        
	    	boolean tag = false;
	    	
	    	
	    	BatchGenerator batch = new BatchGenerator(otrConfig,profile,line.hasOption(oTag.getOpt()));
	    	batch.build(videos);
        }
        
        logger.info("... finished.");
	}
	
	@SuppressWarnings("static-access")
	private Options createOptions()
	{
		oHelp = new Option("help", "Print this message" );
		oDebug = new Option("debug", "Debug output");
		oRename = new Option("rename", "Rename downloaded HQ.MP4.cut");
		oMp4 = new Option("mp4", "Converts AVI to MP4"); 
		oWeb = new Option("web", "Web GUI Interface");
		oAc3 = new Option("ac3", "Use AC3 Audio for HD if available (experimental)");
		oScan = new Option("scan","Scans subdirectories and stores AVI files as unprocesed. Login required!");
		oTag = new Option("tag","Tag final Mp4 file. Login required!");
		
		Option oCreate = new Option("createConfig", "Create a default properties file");
		Option oDir = new Option("createDirs", "Create directories specified in configuration file");
		
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
		options.addOption(oWeb);
		options.addOption(oAc3);
		options.addOption(oMp4);
		options.addOption(oRename);
		options.addOption(oScan);
		options.addOption(oCreate);
		options.addOption(oDir);
		options.addOption(oConfig);
		options.addOption(oProfile);
		options.addOption(oTag);
		
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
//		JaxbUtil.setNsPrefixMapper(new OtrCutNsPrefixMapper());
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
