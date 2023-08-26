package de.kisner.otrcast.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.util.OtrBootstrap;
import de.kisner.otrcast.util.OtrConfig;
import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

public class OtrCastBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(OtrCastBootstrap.class);
	
	public static String xmlConfig = "otrcast/config/otr.xml";
	public static String logConfig = "log4j.xml";
	
	private static EntityManagerFactory emf;
	private static Configuration config;
	
	public static void initLogger(){initLogger("log4j.debug.xml");}
	public static void initLogger(String log4jConfig)
	{
		LoggerInit loggerInit = new LoggerInit(log4jConfig);
		loggerInit.addAltPath("otrcast/config");
		loggerInit.init();
//		JaxbUtil.setNsPrefixMapper(new OtrCastNsPrefixMapper());
	}
	
	public static Configuration init(){return init(xmlConfig);}
	public static Configuration init(String configFile)
	{
		initLogger();
		try
		{
			ExlpCentralConfigPointer ccp = ExlpCentralConfigPointer.instance(OtrBootstrap.appCode).jaxb(JaxbUtil.instance());
			ConfigLoader.add(ccp.toFile(OtrBootstrap.confCode));
		}
		catch (ExlpConfigurationException e) {logger.debug("No additional "+ExlpCentralConfigPointer.class.getSimpleName()+" because "+e.getMessage());}
		ConfigLoader.add(configFile);
		
		OtrCastBootstrap.config = ConfigLoader.init();			

		logger.debug("Config and Logger initialized with "+configFile);
		return OtrCastBootstrap.config;
	}
	
	public static Configuration getConfiguration(){return config;}

    public static EntityManagerFactory buildEmf(){return buildEmfForFile(null);}
    public static EntityManagerFactory buildEmf(OtrConfig config){return buildEmfForFile(config.getDir(OtrConfig.Dir.DB));}
    public static EntityManagerFactory buildEmf(Configuration config){return buildEmf(config.getString(OtrConfig.dirDb));}
    public static EntityManagerFactory buildEmf(String file){return buildEmfForFile(new File(file));}

	private static EntityManagerFactory buildEmfForFile(File f)
	{
		if(emf==null)
		{
			Map<String,String> properties = new HashMap<String,String>();
//			properties.put("javax.persistence.jdbc.url", "jdbc:hsqldb:file:"+f.getAbsolutePath()+"/mc");
//			properties.put("hibernate.hbm2ddl.auto", "update");
		
			emf = Persistence.createEntityManagerFactory("otrcast",properties);
		}

		return emf;
	}
}