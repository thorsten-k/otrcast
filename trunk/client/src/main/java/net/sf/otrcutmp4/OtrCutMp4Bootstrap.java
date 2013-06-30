package net.sf.otrcutmp4;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.io.LoggerInit;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OtrCutMp4Bootstrap
{
	final static Logger logger = LoggerFactory.getLogger(OtrCutMp4Bootstrap.class);
	
	private static EntityManagerFactory emf;
	
	public static Configuration init()
	{
		String configFile = "src/main/resources/otrcutmp4-client/tg.xml";
		return init(configFile);
	}
	
	public static Configuration init(String configFile)
	{
		initLogger();
		Configuration config = 	initConfig(configFile);
							
		logger.debug("Config and Logger initialized");
		return config;
	}
	
	public static Configuration initConfig(String configFile)
	{
		ConfigLoader.add(configFile);
		return ConfigLoader.init();
	}
	
	public static EntityManagerFactory buildEmf()
	{
		if(emf==null)
		{
			Map<String,String> properties = new HashMap<String,String>();
			properties.put("javax.persistence.jdbc.url", "jdbc:hsqldb:mem:db");
		
			properties.put("hibernate.hbm2ddl.auto", "create-drop");
		
			emf = Persistence.createEntityManagerFactory("otr-mediacenter", properties);
		}
		return emf;
	}
	
	public static void initLogger(){initLogger("log4j.xml");}
	public static void initLogger(String log4jConfig)
	{
		LoggerInit loggerInit = new LoggerInit(log4jConfig);
		loggerInit.addAltPath("config.otrcutmp4-client");
		loggerInit.init();
	}
}