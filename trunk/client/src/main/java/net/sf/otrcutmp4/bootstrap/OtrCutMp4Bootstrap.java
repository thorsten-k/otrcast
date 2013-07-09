package net.sf.otrcutmp4.bootstrap;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.model.xml.OtrCutNsPrefixMapper;
import net.sf.otrcutmp4.util.OtrBootstrap;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OtrCutMp4Bootstrap
{
	final static Logger logger = LoggerFactory.getLogger(OtrCutMp4Bootstrap.class);
	
	private static EntityManagerFactory emf;
	public static String configXml = "config.otrcutmp4-client/otr.xml";
	
	public static Configuration init()
	{;
		return init(configXml);
	}
	
	public static Configuration init(String configFile)
	{
		initLogger();
		try
		{
			String cfn = ExlpCentralConfigPointer.getFile(OtrBootstrap.appCode,OtrBootstrap.confCode).getAbsolutePath();
			ConfigLoader.add(cfn);
			logger.info("Using additional config in: "+cfn );
		}
		catch (ExlpConfigurationException e) {logger.debug("No additional "+ExlpCentralConfigPointer.class.getSimpleName()+" because "+e.getMessage());}
		ConfigLoader.add(configFile);
		
		Configuration config = ConfigLoader.init();			
						
		
		logger.debug("Config and Logger initialized");
		return config;
	}
	
	public static void initLogger(){initLogger("log4j.debug.xml");}
	public static void initLogger(String log4jConfig)
	{
		LoggerInit loggerInit = new LoggerInit(log4jConfig);
		loggerInit.addAltPath("config.otrcutmp4-client");
		loggerInit.init();
		JaxbUtil.setNsPrefixMapper(new OtrCutNsPrefixMapper());
	}
	
	public static EntityManagerFactory buildEmf()
	{
		if(emf==null)
		{
			Map<String,String> properties = new HashMap<String,String>();
			properties.put("javax.persistence.jdbc.url", "jdbc:hsqldb:mem:db");	
			properties.put("hibernate.hbm2ddl.auto", "create-drop");
		
			emf = Persistence.createEntityManagerFactory("otr-mediacenter");
		}
		return emf;
	}
}