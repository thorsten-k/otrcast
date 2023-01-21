package de.kisner.otrcast.web.rest;

import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jeesl.api.rest.rs.system.JeeslTestRest;
import org.jeesl.model.json.io.ssi.update.JsonSsiUpdate;
import org.jeesl.model.json.system.job.JsonJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.OtrCastBootstrap;

public class CliTestRest implements JeeslTestRest
{
	final static Logger logger = LoggerFactory.getLogger(CliTestRest.class);
	
	private JeeslTestRest rest;
	
	public CliTestRest(Configuration config)
	{
		ResteasyClient client = new ResteasyClientBuilder().build();
		client.register(new BasicAuthentication("myUser","myPwd"));
		ResteasyWebTarget restTarget = client.target("http://localhost:8080/otrcast");
        rest = restTarget.proxy(JeeslTestRest.class);
	}
	
	@Override public String dateTimePublic() {return rest.dateTimePublic();}
	@Override public String dateTimeRestricted() {return rest.dateTimeRestricted();}
	@Override public JsonSsiUpdate jsonUpdate() {return rest.jsonUpdate();}
	@Override public JsonJob jsonJob() {return rest.jsonJob();}
	
	public static void main(String[] args) throws Exception
	{
		Configuration config = OtrCastBootstrap.init();	
		CliTestRest rest = new CliTestRest(config);
		logger.info(rest.dateTimePublic());
		logger.info(rest.dateTimeRestricted());
	}
}