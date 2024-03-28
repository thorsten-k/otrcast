package de.kisner.otrcast.web.rest;

import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jeesl.api.rest.i.system.JeeslTestRestInterface;
import org.jeesl.api.rest.rs.jx.system.JeeslTestRest;
import org.jeesl.model.json.io.ssi.update.JsonSsiUpdate;
import org.jeesl.model.json.system.job.JsonSystemJob;
import org.jeesl.model.json.util.JsonTime;
import org.jeesl.model.xml.system.test.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.OtrCastBootstrap;

public class CliTestRest implements JeeslTestRestInterface
{
	final static Logger logger = LoggerFactory.getLogger(CliTestRest.class);
	
	private JeeslTestRestInterface rest;
	
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
	@Override public JsonSystemJob jsonJob() {return rest.jsonJob();}
	@Override public JsonTime jsonTimeDownload() {return rest.jsonTimeDownload();}
	@Override public JsonTime jsonTimeUpload(JsonTime time) {return rest.jsonTimeUpload(time);}
	@Override public Test jaxb() {return rest.jaxb();}
	
	public static void main(String[] args) throws Exception
	{
		Configuration config = OtrCastBootstrap.init();	
		CliTestRest rest = new CliTestRest(config);
		logger.info(rest.dateTimePublic());
		logger.info(rest.dateTimeRestricted());
	}
}