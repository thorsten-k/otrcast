package net.sf.otrcutmp4.web.rest;

import net.sf.ahtutils.web.rest.auth.RestEasyPreemptiveClientExecutor;

import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.rest.OtrUserRest;

public class TstUserRest
{
	final static Logger logger = LoggerFactory.getLogger(TstUserRest.class);
	
	private OtrUserRest rest;
	
	public TstUserRest()
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
//		OtrCutMp4Bootstrap.initLogger();
		TstUserRest rest = new TstUserRest();
		rest.test();

	}
}
