package net.sf.otrcutmp4.controller.web.rest;

import net.sf.exlp.util.config.ConfigKey;
import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.interfaces.rest.OtrMediacenterRest;
import net.sf.otrcutmp4.model.xml.container.Otr;
import net.sf.otrcutmp4.test.OtrClientTestBootstrap;

import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliMediaCenterRest
{
	final static Logger logger = LoggerFactory.getLogger(CliMediaCenterRest.class);
	
	private OtrMediacenterRest rest;
	
	public CliMediaCenterRest(Configuration config)
	{		
		 ResteasyClient client = new ResteasyClientBuilder().build();
         ResteasyWebTarget target = client.target(config.getString(ConfigKey.netRestUrl));

         rest = target.proxy(OtrMediacenterRest.class);
	}
	
	public void all()
	{
		Otr otr = rest.all();
		JaxbUtil.info(otr);
	}
	
	public static void main(String[] args) throws ExlpConfigurationException
	{
		Configuration config = OtrClientTestBootstrap.init();
		CliMediaCenterRest rest = new CliMediaCenterRest(config);
		rest.all();
	}
}