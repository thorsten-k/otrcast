package net.sf.otrcutmp4;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.model.xml.OtrCutNsPrefixMapper;
import net.sf.otrcutmp4.util.OtrConfig;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractCommandLine
{
	final static Logger logger = LoggerFactory.getLogger(AbstractCommandLine.class);

	public static final String exeName = "OtrCutMp4-<version>.jar";

	protected Option oHelp,oDebug,oConfig;

    protected Options options;

	protected void buildOptions()
	{
        options = new Options();

        oHelp = new Option("help", "Prints this message" );
        oDebug = new Option("debug", "Debug output");

        oConfig  = OptionBuilder.withArgName("FILENAME")
                .hasArg()
                .withDescription( "Use configuration file FILENAME (optional, default is "+ OtrConfig.otrConfigName+")")
                .create("config");
    }

    protected void parseArguments(CommandLine line)
    {
        if(line.hasOption(oHelp.getOpt())) {printHelp();}

        if(line.hasOption(oDebug.getOpt())) {initLogger("log4j.debug.xml");}
        else{initLogger("log4j.xml");}
    }

    protected void printHelp()
	{
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp( "java -jar "+exeName, options );
		System.exit(0);
	}

    protected void initLogger(String logConfig)
	{
		LoggerInit loggerInit = new LoggerInit(logConfig);	
		loggerInit.addAltPath("src/main/resources/config.otrcutmp4-client");
		loggerInit.addAltPath("config.otrcutmp4-client");
		loggerInit.setAllLoadTypes(LoggerInit.LoadType.File,LoggerInit.LoadType.Resource);
		loggerInit.init();
		JaxbUtil.setNsPrefixMapper(new OtrCutNsPrefixMapper());
	}
}