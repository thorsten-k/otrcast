package net.sf.otrcutmp4.web.rest;

import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.OtrCutMp4Bootstrap;
import de.kisner.otrcast.interfaces.rest.OtrUserRest;

public class CliUserRest
{
	final static Logger logger = LoggerFactory.getLogger(CliUserRest.class);
	
	@SuppressWarnings("unused")
	private OtrUserRest rest;
	
	public CliUserRest()
	{			
		ResteasyClient client = new ResteasyClientBuilder().build();
		client.register(new BasicAuthentication("user","pwd"));
		ResteasyWebTarget restTarget = client.target("http://localhost:8080/otr");
		rest = restTarget.proxy(OtrUserRest.class);
	}
	
	public void test()
	{
	}
	
	public static void main(String[] args)
	{
		OtrCutMp4Bootstrap.initLogger();
		CliUserRest rest = new CliUserRest();
		rest.test();
	}
}
