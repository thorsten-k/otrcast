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

import de.kisner.otrcast.api.rest.OtrVideoRest;
import de.kisner.otrcast.controller.OtrCastBootstrap;
import de.kisner.otrcast.controller.exception.OtrConfigurationException;
import de.kisner.otrcast.controller.media.Mp4LibraryScanner;
import de.kisner.otrcast.controller.processor.mc.McLibraryTagger;
import de.kisner.otrcast.model.xml.OtrCutNsPrefixMapper;
import de.kisner.otrcast.util.OtrConfig;
import de.kisner.otrcast.util.OtrConfig.Dir;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.ahtutils.util.cli.UtilsCliOption;
import net.sf.exlp.interfaces.util.ConfigKey;
import net.sf.exlp.util.xml.JaxbUtil;

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
       
        otrConfig.readConfig(uOption.initConfig(cmd, OtrCastBootstrap.xmlConfig));
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
        	
        	OtrCastBootstrap.buildEmf(otrConfig).createEntityManager();
        	scanMediathek(otrConfig.getDir(OtrConfig.Dir.MC));
        }
        else if(cmd.hasOption(oRetagger.getOpt()) && uOption.allowAppStart())
        {
    		ResteasyClient client = new ResteasyClientBuilder().build();
    		ResteasyWebTarget target = client.target(otrConfig.getKey(ConfigKey.netRestUrl)); 
    		OtrVideoRest rest = target.proxy(OtrVideoRest.class);
    		
        	McLibraryTagger tagger = new McLibraryTagger(otrConfig.getDir(Dir.TMP),otrConfig.getDir(Dir.BACKUP));
        	tagger.scan(otrConfig.getDir(OtrConfig.Dir.MC));
        	tagger.saveToXml(otrConfig.getFile(OtrConfig.fileMcXmlLib));
        }
        
        if(!uOption.isAppStarted())
        {
        	uOption.help();
        }
	}

	private void scanMediathek(File f)
	{
		OtrCastBootstrap.buildEmf(otrConfig).createEntityManager();
		logger.info("Scanning for MP4 in "+f.getAbsolutePath());
		Mp4LibraryScanner mcs = new Mp4LibraryScanner(OtrCastBootstrap.buildEmf().createEntityManager());
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
		JaxbUtil.setNsPrefixMapper(new OtrCutNsPrefixMapper());
		
		UtilsCliOption uOption = new UtilsCliOption(de.kisner.otrcast.api.Version.class.getPackage().getImplementationVersion());
		uOption.setLog4jPaths("config.otrcast-client");
		
		OtrMediaCenter otrMc = new OtrMediaCenter(uOption);
		try {otrMc.parseArguments(args);}
		catch (ParseException e) {logger.error(e.getMessage());uOption.help();}
		catch (OtrConfigurationException e) {logger.error(e.getMessage());uOption.help();}
		catch (UtilsProcessingException e) {e.printStackTrace();}
	}
}