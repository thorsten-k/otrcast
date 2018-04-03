package de.kisner.otrcast.app;

import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jeesl.util.web.RestUrlDelay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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