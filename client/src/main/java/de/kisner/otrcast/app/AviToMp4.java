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
import de.kisner.otrcast.controller.cutlist.DefaultCutlistLoader;
import de.kisner.otrcast.controller.exception.OtrConfigurationException;
import de.kisner.otrcast.controller.exception.OtrInternalErrorException;
import de.kisner.otrcast.controller.processor.SeriesTagger;
import de.kisner.otrcast.controller.processor.SrcDirProcessor;
import de.kisner.otrcast.controller.web.WebCutlistChooserController;
import de.kisner.otrcast.controller.web.rest.WebAviScanner;
import de.kisner.otrcast.factory.xml.otr.XmlOtrIdFactory;
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
	
	public static enum Profile {P0,P1}
	
	public static final String exeName = "OtrCutMp4-<version>.jar";
	
	private Option oProfile,oWeb;
	private Option oAc3,oMp4;
	private Option oCover;
	private Option oTagMp4,oTag;
	private Option oScan;

	private OtrConfig otrConfig;
	
	private Profile profile;
	
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
        
        String configFile = line.getOptionValue("config",OtrConfig.otrConfigName);
        if(line.hasOption("createConfig")){otrConfig.createDefault(configFile);}
        otrConfig.readConfig(configFile);
        
        if(line.hasOption("createDirs")){otrConfig.createDirs();}
        otrConfig.checkCutSettings();
         
        ViewSrcDirProcessor view = new CliSrcDirProcessorView();
        SrcDirProcessor srcDirProcessor = new SrcDirProcessor(view);
        
        DefaultCutlistLoader clFinder = new DefaultCutlistLoader();
        VideoFiles vFiles;
        
        if(line.hasOption(oScan.getOpt()))
	    	{
	    		otrConfig.checkEmailPwd();
	    		WebAviScanner was = new WebAviScanner(otrConfig);
	    		srcDirProcessor.addValidSuffix(XmlOtrIdFactory.typeOtrkey);
	    		was.scan(srcDirProcessor);
	    	}
	        
	        CoverManager coverManager = null;
	        if(line.hasOption(oCover.getOpt()))
	        {
	        	String type = line.getOptionValue(oCover.getOpt());
	        	CoverManager.TYPE cmType = CoverManager.TYPE.valueOf(type);
	        	
	        	switch(cmType)
	        	{
	        		case FS:	coverManager = new FileSystemCoverManager(otrConfig.getDir(Dir.COVER));break;
	        		case FSW:	coverManager = new FileSystemWebCoverManager(otrConfig.getDir(Dir.COVER));break;
	        		default: coverManager=null;break;
	        	}
        }
        
        if(line.hasOption(oTagMp4.getOpt()))
    	{
        	logger.info("Tagging MP4");
        	SeriesTagger tagger = new SeriesTagger(otrConfig,profile,coverManager);
        	tagger.tag(new Long(line.getOptionValue(oTagMp4.getOpt())));
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
	    	JaxbUtil.debug(videos);
	    	
	    	CutlistLoader cutlistLoader = new DefaultCutlistLoader();;
	    	cutlistLoader.loadCuts(videos);       
	    	
	    	BatchGenerator batch = new BatchGenerator(otrConfig,profile,line.hasOption(oTag.getOpt()));
	    	batch.build(videos);
        }
        
        logger.info("... finished.");
	}

	private Options createOptions()
	{
        super.buildOptions();
		oMp4 = new Option("mp4", "Converts AVI to MP4"); 
		oWeb = new Option("web", "Web GUI Interface");
		oAc3 = new Option("ac3", "Use AC3 Audio for HD if available (experimental)");
		oScan = new Option("scan","Scans subdirectories and stores AVI files as unprocesed. Login required!");
		oTag = new Option("tag","Tag processed files. Login required!");
		
		Option oCreate = new Option("createConfig", "Create a default properties file");
		Option oDir = new Option("createDirs", "Create directories specified in configuration file");

		oTagMp4  = Option.builder("tagMp4").required(false)
					.hasArg(true).argName("TOKEN").desc("Tag MP4 file. Login required! (TOKEN format is ID-FILE)")
					.build();

		oCover  = Option.builder("cover").required(false)
				.hasArg(true).argName("TYPE").desc("CoverManager: (FS) FileSystem")
				.build();
		
		StringBuffer sb = new StringBuffer();
		for(int i=1;i<Profile.values().length;i++)
		{
			{sb.append(Profile.values()[i].toString()).append(", ");}
		}
		sb.delete(sb.length()-2, sb.length());
		
		oProfile  = Option.builder("profile").required(false)
				.hasArg(true).argName("PROFILE").desc("Use (optional) experimental PROFILE: "+sb.toString())
				.build();

		options.addOption(oHelp);
		options.addOption(oDebug);
		options.addOption(oWeb);
		options.addOption(oAc3);
		options.addOption(oMp4);
		options.addOption(oScan);
		options.addOption(oCreate);
		options.addOption(oDir);
		options.addOption(oConfig);
		options.addOption(oCover);
		options.addOption(oProfile);
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