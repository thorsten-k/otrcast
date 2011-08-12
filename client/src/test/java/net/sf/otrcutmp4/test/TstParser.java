package net.sf.otrcutmp4.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.exlp.event.LogEventHandler;
import net.sf.exlp.event.handler.EhDebug;
import net.sf.exlp.listener.LogListener;
import net.sf.exlp.listener.impl.LogListenerFile;
import net.sf.exlp.parser.LogParser;
import net.sf.exlp.util.io.ConfigLoader;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.otrcutmp4.exlp.parser.CutlistParser;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TstParser
{
	static Log logger = LogFactory.getLog(TstParser.class);
	
	private Configuration config;
	
	public TstParser(Configuration config)
	{
		this.config=config;
	}
	
	public void cutlist()
	{
		String txt = config.getString("txt.test.cutlist");
		logger.debug("Loading from file: "+txt);
		
		LogEventHandler leh = new EhDebug();
		LogParser lp = new CutlistParser(leh);
		
		LogListener ll = new LogListenerFile(txt,lp);
		ll.processSingle();
	}
	
	public void pattern()
	{	
		String sPattern = "^\\|([a-zA-Z]*)=(.*)";
		String sTest    = "|Goal=blabla bla blablub";
		
		logger.debug("Pattern: "+sPattern);
		logger.debug("Test:    "+sTest);
		
		Pattern p = Pattern.compile(sPattern);
		Matcher m = p.matcher(sTest);
		logger.debug(m.matches());
		if(m.matches())
		{
			logger.debug("Group Count "+m.groupCount());
			for(int i=0;i<=m.groupCount();i++)
			{
				logger.debug(i+" "+m.group(i));
			}
		}
	}
	
	public static void main(String args[]) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();
		
		ConfigLoader.add("src/test/resources/properties/user.otr.properties");
		ConfigLoader.add("src/test/resources/properties/user.properties");
		Configuration config = ConfigLoader.init();
		
		TstParser test = new TstParser(config);
		test.cutlist();
	}
}
