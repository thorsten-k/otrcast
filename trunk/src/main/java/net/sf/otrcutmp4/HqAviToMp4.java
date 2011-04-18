package net.sf.otrcutmp4;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.cutlist.CutlistChooser;
import net.sf.otrcutmp4.cutlist.CutlistFinder;
import net.sf.otrcutmp4.data.jaxb.VideoFiles;
import net.sf.otrcutmp4.exception.OtrConfigurationException;

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


public class HqAviToMp4
{
	static Log logger = LogFactory.getLog(HqAviToMp4.class);
	
	public static final String exeName = "CutHqAviToMp4";
	private Options options;
	private OtrConfig otrConfig;
	
	public HqAviToMp4()
	{
		otrConfig = new OtrConfig();
	}
	
	public void parseArguments(String args[]) throws ParseException, OtrConfigurationException
	{
		options = createOptions();
		CommandLineParser parser = new PosixParser();
		CommandLine line = null;
	    line = parser.parse(options , args); 
	    
        String configFile = line.getOptionValue("config",OtrConfig.otrConfigName);
        
        if(line.hasOption("help")) {printHelp();}
 //       initLogger("log4j.debug.xml");
        initLogger("log4j.xml");
        
        if(line.hasOption("createConfig")){otrConfig.createDefault(configFile);}
        
        Configuration config = otrConfig.readConfig(configFile);
        
        if(line.hasOption("createDirs")){otrConfig.createDirs(config);}
        otrConfig.checkDirs(config);
        otrConfig.checkTools(config);
        
        AviProcessor aviProcessor = new AviProcessor(config);
        CutlistFinder clFinder = new CutlistFinder();
        CutlistChooser clChooser = new CutlistChooser();
        BatchGenerator batch = new BatchGenerator(config);
        
        VideoFiles vFiles = aviProcessor.readFiles();
        vFiles = clFinder.searchCutlist(vFiles);
        vFiles = clChooser.chooseCutlists(vFiles);
        batch.create(vFiles);
        
        logger.info("... finished.");
	}
	
	@SuppressWarnings("static-access")
	private Options createOptions()
	{
		Option oHelp = new Option("help", "Print this message" );
		Option oCreate = new Option("createConfig", "Create a default properties file");
		Option oDir = new Option("createDirs", "Create directories specified in configuration file");
		
		Option oConfig  = OptionBuilder.withArgName("FILENAME")
						  .hasArg()
						  .withDescription( "Use configuration file FILENAME (optional, default is "+OtrConfig.otrConfigName+")")
						  .create("config"); 
		
		Options options = new Options();
		options.addOption(oHelp);
		options.addOption(oCreate);
		options.addOption(oDir);
		options.addOption(oConfig);
		
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
		loggerInit.addAltPath("src/main/resources/config");
		loggerInit.addAltPath("config");
		loggerInit.setAllLoadTypes(LoggerInit.LoadType.File,LoggerInit.LoadType.Resource);
		loggerInit.init();
	}
	
	public static void main(String args[])
	{		
		HqAviToMp4 hqToMp4 = new HqAviToMp4();	
		try {hqToMp4.parseArguments(args);}
		catch (ParseException e) {logger.error(e.getMessage());hqToMp4.printHelp();}
		catch (OtrConfigurationException e) {logger.error(e.getMessage());hqToMp4.printHelp();}
	}
}
