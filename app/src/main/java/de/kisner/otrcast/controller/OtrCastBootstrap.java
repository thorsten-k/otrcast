package de.kisner.otrcast.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.OtrCutNsPrefixMapper;
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
	
	public static String xmlConfig = "config.otrcast-client/otr.xml";
	
	private static EntityManagerFactory emf;
	private static Configuration config;
	
	public static void initLogger(){initLogger("log4j.debug.xml");}
	public static void initLogger(String log4jConfig)
	{
		LoggerInit loggerInit = new LoggerInit(log4jConfig);
		loggerInit.addAltPath("config.otrcutmp4-client");
		loggerInit.init();
		JaxbUtil.setNsPrefixMapper(new OtrCutNsPrefixMapper());
	}
	
	public static Configuration init(){return init(xmlConfig);}
	public static Configuration init(String configFile)
	{
		initLogger();
		try
		{
			String cfn = ExlpCentralConfigPointer.getFile(OtrBootstrap.appCode,OtrBootstrap.confCode).getAbsolutePath();
			ConfigLoader.add(cfn);
			logger.info("Using additional config in: "+cfn);
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
            if(f==null)
            {
                logger.error("f is null !!");
            }
			Map<String,String> properties = new HashMap<String,String>();
			properties.put("javax.persistence.jdbc.url", "jdbc:hsqldb:file:"+f.getAbsolutePath()+"/mc");
//			properties.put("hibernate.hbm2ddl.auto", "update");
		
			emf = Persistence.createEntityManagerFactory("otr-mediacenter",properties);
		}

		return emf;
	}
}