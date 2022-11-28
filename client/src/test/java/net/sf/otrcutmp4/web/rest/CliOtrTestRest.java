package net.sf.otrcutmp4.web.rest;

import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jeesl.api.rest.rs.system.JeeslTestRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.otrcutmp4.test.OtrClientTestBootstrap;

public class CliOtrTestRest
{
	final static Logger logger = LoggerFactory.getLogger(CliOtrTestRest.class);
	
	private JeeslTestRest rest;
	
	public CliOtrTestRest()
	{		
		ResteasyClient client = new ResteasyClientBuilder().build();
		client.register(new BasicAuthentication("user","pwd"));
		ResteasyWebTarget restTarget = client.target("http://localhost:8080/otrcast");
		rest = restTarget.proxy(JeeslTestRest.class);
	}
	
	public void test()
	{
		logger.debug(rest.getTime());
	}
	
	public static void main(String[] args) throws ExlpConfigurationException
	{
		OtrClientTestBootstrap.init();
		CliOtrTestRest rest = new CliOtrTestRest();
		rest.test();

	}
}
