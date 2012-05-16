package net.sf.otrcutmp4.web.rest;

import net.sf.ahtutils.web.rest.RestEasyPreemptiveClientExecutor;
import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.otrcutmp4.interfaces.rest.OtrTestRest;
import net.sf.otrcutmp4.test.OtrClientTstBootstrap;

import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestOtrTestRest
{
	final static Logger logger = LoggerFactory.getLogger(TestOtrTestRest.class);
	
	private OtrTestRest rest;
	
	public TestOtrTestRest()
	{		
		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
		ClientExecutor clientExecutor = RestEasyPreemptiveClientExecutor.factory("user","pwd");
		rest = ProxyFactory.create(OtrTestRest.class, "http://localhost:8080/otr",clientExecutor);
	}
	
	public void test()
	{
		logger.debug(rest.getTime());
	}
	
	public static void main(String[] args) throws ExlpConfigurationException
	{
		OtrClientTstBootstrap.init();
		TestOtrTestRest rest = new TestOtrTestRest();
		rest.test();

	}
}
