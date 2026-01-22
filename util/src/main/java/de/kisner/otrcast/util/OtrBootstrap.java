package de.kisner.otrcast.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.exlp.controller.handler.io.log.LoggerBootstrap;
import org.exlp.util.io.config.ExlpCentralConfigPointer;
import org.exlp.util.jx.JaxbUtil;
import org.jeesl.controller.handler.system.property.ConfigBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.OtrCastNsPrefixMapper;

public class OtrBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(OtrBootstrap.class);
	
	public enum AppCode {otr};
	public static String xmlConfig = "otrcast/config/otr.xml";
	
	public static final String appCode="otr";
	public static final String confCode="cast.client";
	
	public static final String cfgXmlCategories = "xml.categories";
	public static final String cfgXmlSeries = "xml.series";
	public static final String cfgXmlEpisodes = "xml.episodes";
	public static final String cfgXmlFormats = "xml.formats";
	public static final String cfgXmlQuality = "xml.quality";
	
	public static org.exlp.interfaces.system.property.Configuration wrap()
	{
		LoggerBootstrap.instance("otr.log4j2.xml").path("otr/system/io/log").init();
			
		JaxbUtil.setNsPrefixMapper(new OtrCastNsPrefixMapper());
		
		ExlpCentralConfigPointer ccp = ExlpCentralConfigPointer.instance(OtrBootstrap.AppCode.otr).jaxb(JaxbUtil.instance());
		
		return ConfigBootstrap.instance().add(ccp.toPath(confCode)).add(xmlConfig).wrap();
	}
	
    public static EntityManagerFactory buildEmf(){return buildEmfForFile(null);}
    public static EntityManagerFactory buildEmf(OtrConfig config){return buildEmfForFile(config.getDir(OtrConfig.Dir.DB));}
    public static EntityManagerFactory buildEmf(org.exlp.interfaces.system.property.Configuration config){return buildEmf(config.getString(OtrConfig.dirDb));}
    public static EntityManagerFactory buildEmf(String file){return buildEmfForFile(new File(file));}

    private static EntityManagerFactory emf;
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
