package net.sf.otrcutmp4;

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
	
	public static final String exeName = "OtrCutMp4-<version>.jar";

	private OtrConfig otrConfig;
	
	public OtrMediaCenter()
	{
		otrConfig = new OtrConfig();
	}
	
	public void parseArguments(String args[]) throws Exception
	{
		createOptions();
		CommandLineParser parser = new PosixParser();
		CommandLine line = parser.parse(options , args);

        super.parseArguments(line);
        
        String configFile = line.getOptionValue(oConfig.getOpt(),OtrConfig.otrConfigName);

        otrConfig.readConfig(configFile);
        otrConfig.checkMcSettings();
        OtrCutMp4Bootstrap.buildEmf(otrConfig).createEntityManager();

        scanMediathek(otrConfig.getDir(OtrConfig.Dir.MC));

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

	public void rest()
    {
        logger.warn("NYI config");
/*    	ResteasyDeployment deployment = new ResteasyDeployment();
    	deployment.getActualResourceClasses().add(OtrMediacenterRestService.class);
    	
    	NettyJaxrsServer netty = new NettyJaxrsServer();
    	netty.setDeployment(deployment);
    	netty.setPort(config.getInt(ConfigKey.netRestPort));
    	netty.setRootResourcePath("");
    	netty.setSecurityDomain(null);
    	netty.start();
*/  }

	private void createOptions()
	{
        super.buildOptions();

		options.addOption(oHelp);
        options.addOption(oConfig);
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