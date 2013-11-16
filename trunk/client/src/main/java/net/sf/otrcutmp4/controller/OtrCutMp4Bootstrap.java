package net.sf.otrcutmp4.controller;

import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.model.xml.OtrCutNsPrefixMapper;
import net.sf.otrcutmp4.util.OtrBootstrap;
import net.sf.otrcutmp4.util.OtrConfig;
import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
			properties.put("javax.persistence.jdbc.url", "jdbc:hsqldb:file:"+f.getAbsolutePath()+"/db");
//			properties.put("hibernate.hbm2ddl.auto", "update");
		
			emf = Persistence.createEntityManagerFactory("otr-mediacenter",properties);
		}

		return emf;
	}
}