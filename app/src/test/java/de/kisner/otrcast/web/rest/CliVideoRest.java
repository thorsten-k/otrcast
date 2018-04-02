package de.kisner.otrcast.web.rest;

import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jeesl.util.web.RestUrlDelay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.api.rest.OtrVideoRest;
import de.kisner.otrcast.controller.OtrCastBootstrap;
import de.kisner.otrcast.model.xml.container.Otr;
import de.kisner.otrcast.model.xml.video.tv.Episode;
import de.kisner.otrcast.model.xml.video.tv.Series;
import de.kisner.otrcast.model.xml.video.tv.Tags;
import net.sf.exlp.util.xml.JaxbUtil;

public class CliVideoRest implements OtrVideoRest
{
	final static Logger logger = LoggerFactory.getLogger(CliVideoRest.class);
	
	private OtrVideoRest rest;
	
	public CliVideoRest(Configuration config)
	{			
		ResteasyClient client = new ResteasyClientBuilder().build();
		client.register(new BasicAuthentication("user","pwd"));
		ResteasyWebTarget restTarget = client.target(RestUrlDelay.getUrl(config));
		rest = restTarget.proxy(OtrVideoRest.class);
	}
	
	@Override public Tags getTags(String fileName) {return null;}

	@Override public Episode getEpisode(long episodeId) {return rest.getEpisode(episodeId);}
	@Override public Episode getEpisode(long seriesId, long seasonNr, long episodeNr) {return null;}
	@Override public Otr resolveEpisode(Episode episode) {return null;}
	@Override public Otr resolveSeries(Series series) {return null;}
	
	public static void main(String[] args)
	{
		Configuration config = OtrCastBootstrap.init();
		CliVideoRest rest = new CliVideoRest(config);
		JaxbUtil.info(rest.getEpisode(15561));

	}
}