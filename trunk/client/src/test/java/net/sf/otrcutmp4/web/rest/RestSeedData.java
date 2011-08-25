package net.sf.otrcutmp4.web.rest;

import java.io.File;
import java.io.FileNotFoundException;

import javax.ws.rs.core.UriBuilder;

import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.rest.RestSeriesClient;
import net.sf.otrcutmp4.model.xml.ns.OtrCutNsPrefixMapper;
import net.sf.otrcutmp4.model.xml.otr.Otr;
import net.sf.otrcutmp4.model.xml.series.Category;
import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.model.xml.series.Season;
import net.sf.otrcutmp4.model.xml.series.Series;
import net.sf.otrcutmp4.test.OtrClientTstBootstrap;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class RestSeedData
{
	static Log logger = LogFactory.getLog(RestSeedData.class);
	
	private RestSeriesClient rest;
	private Configuration config;
	private WebResource gae;
	
	public RestSeedData(Configuration config)
	{	
		this.config=config;
		
		rest = new RestSeriesClient(config);
		
		ClientConfig cc = new DefaultClientConfig();
		Client client = Client.create(cc);
		gae = client.resource(UriBuilder.fromUri(config.getString(OtrClientTstBootstrap.cfgUrlGae)).build());
	}
	
	public void all()
	{
		Otr otr = rest.allSeries();
		JaxbUtil.debug2(this.getClass(), otr, new OtrCutNsPrefixMapper());
	}
		
	public void addCategories() throws FileNotFoundException
	{	
		Otr otr = (Otr)JaxbUtil.loadJAXB(config.getString(OtrClientTstBootstrap.cfgXmlCategories), Otr.class);
		JaxbUtil.debug2(this.getClass(), otr, new OtrCutNsPrefixMapper());
		for(Category category : otr.getCategory())
		{
			category = gae.path("rest").path("series/addCategory").post(Category.class, category);
		}
	}
	
	public void addSeries() throws FileNotFoundException
	{
		Otr otr = (Otr)JaxbUtil.loadJAXB(config.getString(OtrClientTstBootstrap.cfgXmlSeries), Otr.class);
		JaxbUtil.debug2(this.getClass(), otr, new OtrCutNsPrefixMapper());
		for(Series series : otr.getSeries())
		{
			Series response = gae.path("rest").path("series/addSeries").post(Series.class, series);
			JaxbUtil.debug2(this.getClass(), response, new OtrCutNsPrefixMapper());
		}
	}
	
	public void addEpisode() throws FileNotFoundException
	{
		File dirEpisodes = new File(config.getString(OtrClientTstBootstrap.cfgXmlEpisodes));
		for(File f : dirEpisodes.listFiles())
		{
			Series series = (Series)JaxbUtil.loadJAXB(f.getAbsolutePath(), Series.class);
			
			Series seriesId = new Series();
			seriesId.setId(series.getId());
			for(Season season : series.getSeason())
			{
				Season seasonId = new Season();
				seasonId.setNr(season.getNr());
				seasonId.setSeries(seriesId);
				for(Episode episode : season.getEpisode())
				{
					episode.setSeason(seasonId);
					JaxbUtil.debug(episode);
					rest.addEpisode(episode);
				}
			}
		}
	}
	
	public static void main(String[] args) throws ExlpConfigurationException, FileNotFoundException
	{
		Configuration config = OtrClientTstBootstrap.init();
		RestSeedData rest = new RestSeedData(config);
//		rest.all();
		
//		rest.addCategories();
//		rest.addSeries();
		rest.addEpisode();
	}
}