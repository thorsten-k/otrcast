package de.kisner.otrcast.app;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.jeesl.controller.handler.cli.JeeslCliOptionHandler;
import org.jeesl.exception.processing.UtilsProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.OtrCastBootstrap;
import de.kisner.otrcast.controller.batch.BatchGenerator;
import de.kisner.otrcast.controller.cli.CliCutlistChooserController;
import de.kisner.otrcast.controller.cover.FileSystemCoverManager;
import de.kisner.otrcast.controller.cover.FileSystemWebCoverManager;
import de.kisner.otrcast.controller.cutlist.JdomCutlistLoader;
import de.kisner.otrcast.controller.exception.OtrConfigurationException;
import de.kisner.otrcast.controller.media.McLibraryTagger;
import de.kisner.otrcast.controller.media.SeriesTagger;
import de.kisner.otrcast.controller.processor.SrcDirProcessor;
import de.kisner.otrcast.controller.web.rest.WebAviScanner;
import de.kisner.otrcast.controller.web.rest.WebCutlistChooserController;
import de.kisner.otrcast.factory.xml.otr.XmlOtrIdFactory;
import de.kisner.otrcast.interfaces.OtrCastInterface;
import de.kisner.otrcast.interfaces.OtrCastInterface.Profile;
import de.kisner.otrcast.interfaces.controller.CoverManager;
import de.kisner.otrcast.interfaces.controller.CutlistChooser;
import de.kisner.otrcast.interfaces.view.client.ViewClient;
import de.kisner.otrcast.interfaces.view.client.ViewCutlistChooser;
import de.kisner.otrcast.model.xml.OtrCastNsPrefixMapper;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.cut.VideoFiles;
import de.kisner.otrcast.model.xml.video.Video;
import de.kisner.otrcast.model.xml.video.Videos;
import de.kisner.otrcast.util.OtrConfig;
import de.kisner.otrcast.util.OtrConfig.Dir;
import de.kisner.otrcast.view.client.ClientViewConsole;
import de.kisner.otrcast.view.client.console.ConsoleViewCutlistChooser;
import de.kisner.otrcast.view.web.WebCutlistChooserView;
import net.sf.exlp.util.xml.JaxbUtil;

public class OtrCastClient
{
	final static Logger logger = LoggerFactory.getLogger(OtrCastClient.class);
	
	public static final String exeName = "OtrCastClient-<version>.jar";

	private JeeslCliOptionHandler uOption;
	private OtrConfig otrConfig;
	
	private Option oTagLib,oScan,oProfile,oCover,oMp4,oWeb;
	private Option oTagMp4,oTagFile,oTagProcessed;
	
	public OtrCastClient(JeeslCliOptionHandler uOption)
	{
		this.uOption=uOption;
		otrConfig = new OtrConfig();
	}
	
	public void parseArguments(String args[]) throws Exception
	{
		for(String s : args)
		{
			System.out.println(s);
		}
		
		createOptions();
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(uOption.getOptions(), args);	
		
		uOption.handleHelp(cmd);
		uOption.setLog4jPaths("otrcast/config");
		uOption.handleLog4j(cmd);

        otrConfig.readConfig(uOption.initConfig(cmd, OtrCastBootstrap.xmlConfig));
        otrConfig.checkCutSettings();        
        
        ViewClient view = new ClientViewConsole();
 //       ViewClient view = new ClientViewLanterna();
        SrcDirProcessor srcDirProcessor = new SrcDirProcessor(view);
        
        OtrCastInterface.Profile profile = null;
        if(cmd.hasOption(oProfile.getOpt()))
        {
	        	try {profile = Profile.valueOf(cmd.getOptionValue(oProfile.getOpt()));}
	        	catch (IllegalArgumentException e)
	        	{
	        		logger.warn("Profie "+cmd.getOptionValue(oProfile.getOpt())+" not available");
	        		logger.warn("Maybe print a Help");
	        	}
        }
        else
        {
        		profile = Profile.P0;
        }
        logger.debug("Using Profile :"+profile);
        
        CoverManager coverManager = null;
        if(cmd.hasOption(oCover.getOpt()))
	    {
	        	String type = cmd.getOptionValue(oCover.getOpt());
	        	CoverManager.TYPE cmType = CoverManager.TYPE.valueOf(type);
	        	
	        	switch(cmType)
	        	{
	        		case FS:	coverManager = new FileSystemCoverManager(otrConfig.getDir(Dir.COVER));break;
	        		case FSW:	coverManager = new FileSystemWebCoverManager(otrConfig.getDir(Dir.COVER));break;
	        		default: coverManager=null;break;
	        	}
        }
        
        if(cmd.hasOption(oTagLib.getOpt()) && uOption.allowAppStart())
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
        
        if(cmd.hasOption(oMp4.getOpt()))
        {
        	
            JdomCutlistLoader cutlistLoader = new JdomCutlistLoader(view);
            
            VideoFiles vFiles;
            
	        vFiles = srcDirProcessor.scan(otrConfig.getDir(Dir.AVI));
	        if(cmd.hasOption("ac3"))
	        {
		        	logger.warn("Remember, the option ac3 is EXPERIMENTAL");
		        	logger.warn("http://otrcutmp4.sourceforge.net/future.html");
		        	try {Thread.sleep(3000);} catch (InterruptedException e) {logger.error("",e);}
	        }
	        else
	        {
	        		for(VideoFile vf : vFiles.getVideoFile()){vf.getOtrId().getFormat().setAc3(false);}
	        }
	        
	        vFiles = cutlistLoader.searchCutlist(vFiles);
	        
	        ViewCutlistChooser viewCutlistChooser = null;
	        CutlistChooser controllerCutlistChooser = null;
	              
	        if(cmd.hasOption(oWeb.getOpt()))
	        {
		        	viewCutlistChooser = new WebCutlistChooserView();
		        	controllerCutlistChooser = new WebCutlistChooserController(viewCutlistChooser,otrConfig);
	        }
	        else
	        {
		        	viewCutlistChooser = new ConsoleViewCutlistChooser();
		        	controllerCutlistChooser = new CliCutlistChooserController(viewCutlistChooser);
	        }
	    	
		    	Videos videos = controllerCutlistChooser.chooseCutlists(vFiles);
		    	JaxbUtil.info(vFiles);
		    	JaxbUtil.info(videos);
//		    	System.exit(-1);

		    	cutlistLoader.loadCuts(videos);
		    	
		    	if(videos.isSetVideo())
		    	{
		    		for(Video video : videos.getVideo())
		    		{
		    			if(video.isSetVideoFiles())
		    			{
		    				VideoFiles tmp = new VideoFiles();
			    			if(video.getVideoFiles().isSetVideoFile())
			    			{
			    				for(VideoFile vf : video.getVideoFiles().getVideoFile())
			    				{
			    					if(vf.isSetCutList()) {tmp.getVideoFile().add(vf);}
			    				}
			    			}
			    			video.setVideoFiles(tmp);
		    			}
		    		}
		    	}
		    		
		    	JaxbUtil.info(videos);
		    	
		    	BatchGenerator batch = new BatchGenerator(otrConfig,profile,cmd.hasOption(oTagFile.getOpt()),true);
		    	batch.build(videos);
        }
        
        if(cmd.hasOption(oTagMp4.getOpt()))
        {
	        	logger.info("Tagging MP4");
	        	SeriesTagger tagger = new SeriesTagger(otrConfig,profile,coverManager);
	        	tagger.tag(new Long(cmd.getOptionValue(oTagMp4.getOpt())));
	        	
	        	 if(cmd.hasOption(oTagProcessed.getOpt()))
             {
     	        	logger.info("Processed MP4");
     	        	tagger.processed(new Long(cmd.getOptionValue(oTagProcessed.getOpt())));
             }
	        	
	        	return;
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
        
       
        oScan = Option.builder("scan").required(false).desc("Scans the MP4 Library").build(); uOption.getOptions().addOption(oScan);
        
		oMp4 = new Option("mp4", "Converts AVI to MP4"); uOption.getOptions().addOption(oMp4);
		oWeb = new Option("web", "Web GUI Interface"); uOption.getOptions().addOption(oWeb);
        
        StringBuffer sb = new StringBuffer();
		for(int i=1;i<Profile.values().length;i++)
		{
			{sb.append(Profile.values()[i].toString()).append(", ");}
		}
        oProfile = Option.builder("profile").required(false)
				.hasArg(true).argName("PROFILE").desc("Use (optional) experimental PROFILE: "+sb.toString())
				.build();uOption.getOptions().addOption(oProfile);
				
		oCover = Option.builder("cover").required(false)
				.hasArg(true).argName("TYPE").desc("CoverManager: (FS) FileSystem")
				.build();uOption.getOptions().addOption(oCover);
				
		oTagMp4 = Option.builder("tagMp4").required(false)
						.hasArg(true).argName("TOKEN").desc("Tag MP4 file. Login required! (TOKEN format is ID-FILE)")
						.build(); uOption.getOptions().addOption(oTagMp4);
						
		oTagProcessed = Option.builder("tagProcessed").required(false)
				.hasArg(true).argName("TOKEN").desc("Processed MP4 file. Login required! (TOKEN format is ID-FILE)")
				.build(); uOption.getOptions().addOption(oTagProcessed);
		
		 oTagLib = Option.builder("tagLib").required(false).desc("Tags the MP4 Library").build(); uOption.getOptions().addOption(oTagLib);
		 oTagFile = Option.builder("tagFile").required(false).desc("Include tagging in Batch").build(); uOption.getOptions().addOption(oTagFile);
	}
	
	public static void main(String args[]) throws Exception
	{
		JaxbUtil.setNsPrefixMapper(new OtrCastNsPrefixMapper());
		
		JeeslCliOptionHandler uOption = new JeeslCliOptionHandler(de.kisner.otrcast.api.Version.class.getPackage().getImplementationVersion());
		uOption.setLog4jPaths("otrcast-app/config");
		
		OtrCastClient otrMc = new OtrCastClient(uOption);
		try {otrMc.parseArguments(args);}
		catch (ParseException e) {logger.error(e.getMessage());uOption.help();}
		catch (OtrConfigurationException e) {logger.error(e.getMessage());uOption.help();}
		catch (UtilsProcessingException e) {e.printStackTrace();}
	}
}