package de.kisner.otrcast.app;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.OtrCastNsPrefixMapper;
import de.kisner.otrcast.util.OtrConfig;

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

        oConfig  = Option.builder("config").required(false)
				.hasArg(true).argName("FILENAME").desc("Use configuration file FILENAME (optional, default is "+ OtrConfig.otrConfigName+")")
				.build();
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
		formatter.printHelp(100, "java -jar "+exeName, "", options, "",true);
		System.exit(0);
	}

    protected void initLogger(String logConfig)
	{
		LoggerInit loggerInit = new LoggerInit(logConfig);
		loggerInit.addAltPath("otrcast-client/config");
		loggerInit.setAllLoadTypes(LoggerInit.LoadType.File,LoggerInit.LoadType.Resource);
		loggerInit.init();
		JaxbUtil.setNsPrefixMapper(new OtrCastNsPrefixMapper());
	}
}