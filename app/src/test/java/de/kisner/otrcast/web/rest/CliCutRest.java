package de.kisner.otrcast.web.rest;

import org.apache.commons.configuration.Configuration;
import org.exlp.controller.handler.web.rest.DelayedUrlConfig;
import org.exlp.util.jx.JaxbUtil;
import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jeesl.exception.processing.UtilsProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.api.rest.OtrCutRest;
import de.kisner.otrcast.controller.OtrCastBootstrap;
import de.kisner.otrcast.model.xml.cut.VideoFiles;
import de.kisner.otrcast.model.xml.video.Videos;

public class CliCutRest implements OtrCutRest
{
	final static Logger logger = LoggerFactory.getLogger(CliCutRest.class);
	
	private OtrCutRest rest;
	
	public CliCutRest(Configuration config)
	{
		ResteasyClient client = new ResteasyClientBuilder().build();
		client.register(new BasicAuthentication("myUser","myPwd"));
		ResteasyWebTarget restTarget = client.target(DelayedUrlConfig.resolve(config));
        rest = restTarget.proxy(OtrCutRest.class);
	}
	
	@Override
	public String addCutPackage(VideoFiles vFiles) throws UtilsProcessingException {return null;}

	@Override public Videos findCutPackage(String token) throws UtilsProcessingException {return rest.findCutPackage(token);}
	
	public static void main(String[] args) throws Exception
	{
		Configuration config = OtrCastBootstrap.init();	
		CliCutRest rest = new CliCutRest(config);
		JaxbUtil.info(rest.findCutPackage("mihaefe3ja"));
	}
}