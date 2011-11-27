package net.sf.otrcutmp4.controller.rest;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import net.sf.otrcutmp4.controller.interfaces.rest.OtrSeriesRestService;
import net.sf.otrcutmp4.model.xml.container.Otr;
import net.sf.otrcutmp4.model.xml.series.Category;
import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.model.xml.series.Series;
import net.sf.otrcutmp4.model.xml.series.Tag;
import net.sf.otrcutmp4.model.xml.series.Tags;
import net.sf.otrcutmp4.util.OtrBootstrap;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class RestSeriesClient implements OtrSeriesRestService
{
	final static Logger logger = LoggerFactory.getLogger(RestSeriesClient.class);
	
	private WebResource gae;
	
	public RestSeriesClient(Configuration config)
	{	
		ClientConfig cc = new DefaultClientConfig();
		Client client = Client.create(cc);
		gae = client.resource(UriBuilder.fromUri(config.getString(OtrBootstrap.cfgUrlGae)).build());
	}
	
	@Override
	public Tags getTags(String otrId)
	{
		Tags tags = gae.path("rest").path("series/tags/"+otrId).accept(MediaType.APPLICATION_XML).get(Tags.class);
		return tags;
	}

	@Override
	public Tag tag(long episodeId, String otrId)
	{
		Tag tag = gae.path("rest").path("series/tag/"+episodeId+"/"+otrId).accept(MediaType.APPLICATION_XML).get(Tag.class);
		return tag;
	}

	@Override
	public Otr allSeries()
	{
		Otr otr = gae.path("rest").path("series/all").get(Otr.class);
		return otr;
	}

	@Override
	public Series series(long seriesId)
	{
		Series series = gae.path("rest").path("series/series/"+seriesId).get(Series.class);
		return series;
	}
	
	@Override
	public Category addCategory(Category category)
	{
		logger.warn("NYI");
		return null;
	}

	@Override
	public Series addSeries(Series series)
	{
		logger.warn("NYI");
		return null;
	}

	@Override
	public Episode addEpisode(Episode episode)
	{
		return gae.path("rest").path("series/addEpisode").post(Episode.class, episode);
	}
}