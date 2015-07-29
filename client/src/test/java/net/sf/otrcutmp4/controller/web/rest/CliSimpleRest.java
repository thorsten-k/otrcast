package net.sf.otrcutmp4.controller.web.rest;

import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.rest.OtrSimpleRest;
import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.interfaces.util.ConfigKey;
import net.sf.otrcutmp4.test.OtrClientTestBootstrap;

public class CliSimpleRest
{
	final static Logger logger = LoggerFactory.getLogger(CliSimpleRest.class);
	
	private OtrSimpleRest rest;

	public CliSimpleRest(Configuration config)
	{		
		String restUrl = config.getString(ConfigKey.netRestUrl);
		logger.info("Connection to "+restUrl);
		
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(restUrl);

		rest = target.proxy(OtrSimpleRest.class);
	}
	
	public void test()
	{
		logger.info("Hello: "+rest.hello());
		logger.info("Books: "+rest.getBooks());
		logger.info("Book: "+rest.getBook("test"));
	}
	
	public static void main(String[] args) throws ExlpConfigurationException
	{
		Configuration config = OtrClientTestBootstrap.init();
		CliSimpleRest rest = new CliSimpleRest(config);
		rest.test();
	}
}