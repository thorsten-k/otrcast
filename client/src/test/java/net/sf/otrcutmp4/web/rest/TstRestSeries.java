package net.sf.otrcutmp4.web.rest;

import javax.ws.rs.core.UriBuilder;

import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.model.xml.ns.OtrCutNsPrefixMapper;
import net.sf.otrcutmp4.model.xml.otr.Otr;
import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.model.xml.series.Season;
import net.sf.otrcutmp4.model.xml.series.Series;
import net.sf.otrcutmp4.test.OtrClientTstBootstrap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class TstRestSeries
{
	static Log logger = LogFactory.getLog(TstRestSeries.class);
	
	private WebResource gae;
	
	public TstRestSeries()
	{	
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		gae = client.resource(UriBuilder.fromUri("http://otr-series.appspot.com").build());
	}
	
	public void all()
	{
		Otr otr = gae.path("rest").path("series/all").get(Otr.class);
		JaxbUtil.debug2(this.getClass(), otr, new OtrCutNsPrefixMapper());
	}
	
	public void addSeries()
	{
		Series series = new Series();
		series.setName("Tatort");
		
		Series response = gae.path("rest").path("series/addSeries").post(Series.class, series);
		JaxbUtil.debug2(this.getClass(), response, new OtrCutNsPrefixMapper());
	}
	
	public void addEpisode()
	{
		Series series = new Series();
		series.setId(1);
		
		Season season = new Season();
		season.setSeries(series);
		season.setNr(1);
		
		Episode episode = new Episode();
		episode.setName("Test");
		episode.setNr(1);
		episode.setSeason(season);
		
		Series response = gae.path("rest").path("series/addEpisode").post(Series.class, episode);
		JaxbUtil.debug2(this.getClass(), response, new OtrCutNsPrefixMapper());
	}
	
	public static void main(String[] args) throws ExlpConfigurationException
	{
		OtrClientTstBootstrap.init();
		TstRestSeries rest = new TstRestSeries();
		rest.all();
		rest.addSeries();
		rest.addEpisode();
	}
}