package de.kisner.otrcast.app;

import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jeesl.util.web.RestUrlDelay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.OtrCastNsPrefixMapper;
import de.kisner.otrcast.util.OtrBootstrap;
import de.kisner.otrcast.util.OtrConfig;
import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

public class OtrCastApp
{
	final static Logger logger = LoggerFactory.getLogger(OtrCastApp.class);
	

	private static Map<Class<?>,Object> mapRest;
	
	@SuppressWarnings("unchecked")
	public static <T extends Object> T rest(Configuration config, Class<T> c)
	{
		if(mapRest==null){mapRest = new Hashtable<Class<?>,Object>();}
		if(!mapRest.containsKey(c))
		{
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target(RestUrlDelay.getUrl(config)); 
			mapRest.put(c,target.proxy(c));
		}
		
		return (T)mapRest.get(c);
	}

}