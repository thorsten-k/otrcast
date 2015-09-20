package de.kisner.otrcast.app;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.OtrCutMp4Bootstrap;
import de.kisner.otrcast.controller.exception.OtrConfigurationException;
import de.kisner.otrcast.controller.processor.mc.McLibraryTagger;
import de.kisner.otrcast.controller.processor.mc.McScanner;
import de.kisner.otrcast.interfaces.rest.OtrSeriesRest;
import de.kisner.otrcast.util.OtrConfig;
import de.kisner.otrcast.util.OtrConfig.Dir;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.ahtutils.util.cli.UtilsCliOption;

public class OtrMediaCenter
{
	final static Logger logger = LoggerFactory.getLogger(OtrMediaCenter.class);
	
	public static final String exeName = "OtrMediaCenter-<version>.jar";

	private static enum McMode {managed,unmanged}
	
	private UtilsCliOption uOption;
	private OtrConfig otrConfig;
	
	private Option oMediaCenter,oMediaCenterMode,oRetagger;
	
	public OtrMediaCenter(UtilsCliOption uOption)
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
       
        otrConfig.readConfig(uOption.initConfig(cmd, OtrCutMp4Bootstrap.xmlConfig));
        otrConfig.checkMcSettings();
        
        if(cmd.hasOption(oMediaCenter.getOpt()) && uOption.allowAppStart())
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
        else if(cmd.hasOption(oRetagger.getOpt()) && uOption.allowAppStart())
        {
    		ResteasyClient client = new ResteasyClientBuilder().build();
    		ResteasyWebTarget target = client.target(otrConfig.getKey(OtrConfig.urlOtrSeries)); 
    		OtrSeriesRest rest = target.proxy(OtrSeriesRest.class);
    		
        	McLibraryTagger tagger = new McLibraryTagger(rest,otrConfig.getDir(Dir.TMP),otrConfig.getDir(Dir.BACKUP));
        	tagger.scan(otrConfig.getDir(OtrConfig.Dir.MC));
        }
        
        if(!uOption.isAppStarted())
        {
        	uOption.help();
        }
	}

	public void scanMediathek(File f)
	{
		OtrCutMp4Bootstrap.buildEmf(otrConfig).createEntityManager();
		logger.info("Scanning for MP4 in "+f.getAbsolutePath());
		McScanner mcs = new McScanner(OtrCutMp4Bootstrap.buildEmf().createEntityManager());
		mcs.scan(f);
	}

	private void createOptions()
	{
		uOption.buildHelp();
        uOption.buildDebug();
        uOption.buildConfig();
        
        oMediaCenter  = Option.builder("mc").required(false).desc("Starts Mediacenter").build();
        oRetagger  = Option.builder("tag").required(false).desc("Re-Tags the MP4 Library").build();
        
        oMediaCenterMode  = Option.builder("mode").required(false)
				.hasArg(true).argName("MODE").desc("only for mc, MODE can be "+McMode.managed+" or "+McMode.unmanged)
				.build();
        
        uOption.getOptions().addOption(oMediaCenter);
        uOption.getOptions().addOption(oMediaCenterMode);
        uOption.getOptions().addOption(oRetagger);
	}

	public static void main(String args[]) throws Exception
	{
		UtilsCliOption uOption = new UtilsCliOption(de.kisner.otrcast.interfaces.Version.class.getPackage().getImplementationVersion());
		uOption.setLog4jPaths("config.otrcast-client");
		
		OtrMediaCenter otrMc = new OtrMediaCenter(uOption);
		try {otrMc.parseArguments(args);}
		catch (ParseException e) {logger.error(e.getMessage());uOption.help();}
		catch (OtrConfigurationException e) {logger.error(e.getMessage());uOption.help();}
		catch (UtilsProcessingException e) {e.printStackTrace();}
	}
}