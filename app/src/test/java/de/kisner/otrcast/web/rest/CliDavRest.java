package de.kisner.otrcast.web.rest;

import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jeesl.util.web.RestUrlDelay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.api.rest.OtrDavRest;
import de.kisner.otrcast.controller.OtrCastBootstrap;
import de.kisner.otrcast.model.xml.container.Otr;
import net.sf.exlp.util.xml.JaxbUtil;

public class CliDavRest implements OtrDavRest
{
	final static Logger logger = LoggerFactory.getLogger(CliDavRest.class);
	
	private OtrDavRest rest;
	
	public CliDavRest(Configuration config)
	{
		ResteasyClient client = new ResteasyClientBuilder().build();
		client.register(new BasicAuthentication("myUser","myPwd"));
		ResteasyWebTarget restTarget = client.target(RestUrlDelay.getUrl(config));
        rest = restTarget.proxy(OtrDavRest.class);
	}
	
	@Override public Otr getContent(String x) {return rest.getContent(x);}
	
	public static void main(String[] args) throws Exception
	{
		Configuration config = OtrCastBootstrap.init();	
		CliDavRest cli = new CliDavRest(config);
		JaxbUtil.info(cli.getContent("x"));
	}
}