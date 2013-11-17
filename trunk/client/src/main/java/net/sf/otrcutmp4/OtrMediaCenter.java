package net.sf.otrcutmp4;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.exlp.interfaces.util.ConfigKey;
import net.sf.otrcutmp4.controller.OtrCutMp4Bootstrap;
import net.sf.otrcutmp4.controller.exception.OtrConfigurationException;
import net.sf.otrcutmp4.controller.processor.mc.MediaCenterScanner;
import net.sf.otrcutmp4.controller.web.rest.OtrMediacenterRestService;
import net.sf.otrcutmp4.util.OtrConfig;
import org.apache.commons.cli.*;
import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class OtrMediaCenter
{
	final static Logger logger = LoggerFactory.getLogger(OtrMediaCenter.class);
	
	public static final String exeName = "OtrCutMp4-<version>.jar";
	
	private Configuration config;
	private OtrConfig otrConfig;
	
	private Options options;
	private Option oHelp,oConfig;
	
	public OtrMediaCenter(Configuration config)
	{
		otrConfig = new OtrConfig();
		this.config=config;
	}
	
	public void parseArguments(String args[]) throws Exception
	{
		options = createOptions();
		CommandLineParser parser = new PosixParser();
		CommandLine line = parser.parse(options , args); 
	     
        if(line.hasOption(oHelp.getOpt())) {printHelp();}
        
        String configFile = line.getOptionValue(oConfig.getOpt(),OtrConfig.otrConfigName);

        otrConfig.readConfig(configFile);
        otrConfig.checkMcSettings();

        scanMediathek(otrConfig.getDir(OtrConfig.Dir.MC));

//        McIncomingHotfolder hot = new McIncomingHotfolder(otrConfig);
//		hot.addRoute();
//		hot.startHotFolder();
        Thread.sleep(1000);
	}

	public void scanMediathek(File f)
	{
		logger.info("Scanning for MP4");
		MediaCenterScanner mcs = new MediaCenterScanner(OtrCutMp4Bootstrap.buildEmf().createEntityManager());
		mcs.scan(f);
	}

	public void rest()
    {
    	ResteasyDeployment deployment = new ResteasyDeployment();
    	deployment.getActualResourceClasses().add(OtrMediacenterRestService.class);
    	
    	NettyJaxrsServer netty = new NettyJaxrsServer();
    	netty.setDeployment(deployment);
    	netty.setPort(config.getInt(ConfigKey.netRestPort));
    	netty.setRootResourcePath("");
    	netty.setSecurityDomain(null);
    	netty.start();
    }
	
	private void printHelp()
	{
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp( "java -jar "+exeName, options );
		System.exit(0);
	}
	
	@SuppressWarnings("static-access")
	private Options createOptions()
	{
		oHelp = new Option("help", "Print this message" );
		
		oConfig  = OptionBuilder.withArgName("FILENAME")
				  .hasArg()
				  .withDescription( "Use configuration file FILENAME (optional, default is "+OtrConfig.otrConfigName+")")
				  .create("config"); 
		
		Options options = new Options();
		options.addOption(oHelp);
		options.addOption(oConfig);
		
		return options;
	}

	public static void main(String args[]) throws Exception
	{		
		Configuration config = OtrCutMp4Bootstrap.init();
		OtrCutMp4Bootstrap.buildEmf(config).createEntityManager();
		
		OtrMediaCenter otrMc = new OtrMediaCenter(config);
		try {otrMc.parseArguments(args);}
		catch (ParseException e) {logger.error(e.getMessage());otrMc.printHelp();}
		catch (OtrConfigurationException e) {logger.error(e.getMessage());otrMc.printHelp();}
		catch (UtilsProcessingException e) {e.printStackTrace();}
	}
}