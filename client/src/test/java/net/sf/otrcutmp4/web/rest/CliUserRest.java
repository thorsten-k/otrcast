package net.sf.otrcutmp4.web.rest;

import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.OtrCutMp4Bootstrap;
import de.kisner.otrcast.interfaces.rest.OtrUserRest;
import net.sf.ahtutils.web.rest.auth.RestEasyPreemptiveClientExecutor;

public class CliUserRest
{
	final static Logger logger = LoggerFactory.getLogger(CliUserRest.class);
	
	private OtrUserRest rest;
	
	public CliUserRest()
	{	
		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
		ClientExecutor clientExecutor = RestEasyPreemptiveClientExecutor.factory("user","pwd");
		rest = ProxyFactory.create(OtrUserRest.class, "http://localhost:8080/otr",clientExecutor);
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
