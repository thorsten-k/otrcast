package de.kisner.otrcast.web.rest;

import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.api.rest.OtrDavRest;
import de.kisner.otrcast.controller.OtrCastBootstrap;
import de.kisner.otrcast.model.xml.container.Otr;
import de.kisner.otrcast.model.xml.video.Videos;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.exlp.util.xml.JaxbUtil;

public class CliDavRest implements OtrDavRest
{
	final static Logger logger = LoggerFactory.getLogger(CliDavRest.class);
	
	private OtrDavRest rest;
	
	public CliDavRest(Configuration config)
	{
		ResteasyClient client = new ResteasyClientBuilder().build();
		client.register(new BasicAuthentication("myUser","myPwd"));
		ResteasyWebTarget restTarget = client.target("http://192.168.202.26:8080/otrcast");
        rest = restTarget.proxy(OtrDavRest.class);
	}
	
	@Override public Otr getContent(String x) {return rest.getContent(x);}
	
	public static void main(String[] args) throws Exception
	{
		OtrCastBootstrap.initLogger(OtrCastBootstrap.logConfig);
		CliDavRest cli = new CliDavRest(null);
		JaxbUtil.info(cli.getContent("x"));
	}

	@Override
	public String uploadRepository(Videos videos) throws UtilsProcessingException {
		// TODO Auto-generated method stub
		return null;
	}
}