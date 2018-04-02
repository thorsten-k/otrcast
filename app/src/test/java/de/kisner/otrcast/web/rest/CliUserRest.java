package de.kisner.otrcast.web.rest;

import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jeesl.util.web.RestUrlDelay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.OtrCastBootstrap;
import de.kisner.otrcast.interfaces.rest.OtrUserRest;
import de.kisner.otrcast.model.xml.cut.VideoFiles;
import de.kisner.otrcast.model.xml.video.tv.Tags;
import net.sf.exlp.util.xml.JaxbUtil;

public class CliUserRest implements OtrUserRest
{
	final static Logger logger = LoggerFactory.getLogger(CliUserRest.class);
	
	private OtrUserRest rest;
	
	public CliUserRest(Configuration config)
	{
		ResteasyClient client = new ResteasyClientBuilder().build();
		client.register(new BasicAuthentication("myUser","myPwd"));
		ResteasyWebTarget restTarget = client.target(RestUrlDelay.getUrl(config));
        rest = restTarget.proxy(OtrUserRest.class);
	}
	
	@Override public String scan(VideoFiles vFiles) {return rest.scan(vFiles);}
	@Override public Tags processedCutlist(long cutlistId) {return rest.processedCutlist(cutlistId);}
	
	public static void main(String[] args) throws Exception
	{
		Configuration config = OtrCastBootstrap.init();	
		CliUserRest rest = new CliUserRest(config);
//		logger.info(rest.scan(XmlVideoFilesFactory.build()));
		JaxbUtil.info(rest.processedCutlist(14862));
	}
}