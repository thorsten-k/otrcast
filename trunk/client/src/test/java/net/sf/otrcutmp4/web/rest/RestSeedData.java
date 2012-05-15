package net.sf.otrcutmp4.web.rest;

import java.io.File;
import java.io.FileNotFoundException;

import javax.ws.rs.core.UriBuilder;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.rest.AdminRestClient;
import net.sf.otrcutmp4.controller.rest.RestSeriesClient;
import net.sf.otrcutmp4.interfaces.rest.OtrAdminRestService;
import net.sf.otrcutmp4.model.xml.container.Otr;
import net.sf.otrcutmp4.model.xml.ns.OtrCutNsPrefixMapper;
import net.sf.otrcutmp4.model.xml.otr.Format;
import net.sf.otrcutmp4.model.xml.otr.Quality;
import net.sf.otrcutmp4.model.xml.series.Category;
import net.sf.otrcutmp4.model.xml.series.Series;
import net.sf.otrcutmp4.util.OtrBootstrap;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class RestSeedData
{
	final static Logger logger = LoggerFactory.getLogger(TstCutRest.class);
	
	private RestSeriesClient restSeries;
	private OtrAdminRestService restAdmin;
	private Configuration config;
	private WebResource gae;
	
	public RestSeedData(Configuration config)
	{	
		this.config=config;
		
		restSeries = new RestSeriesClient(config);
		restAdmin = new AdminRestClient(config.getString(OtrBootstrap.cfgUrlGae));
		
		ClientConfig cc = new DefaultClientConfig();
		Client client = Client.create(cc);
		gae = client.resource(UriBuilder.fromUri(config.getString(OtrBootstrap.cfgUrlGae)).build());
	}
	
	public void all()
	{
		Otr otr = restSeries.allSeries();
		JaxbUtil.debug2(this.getClass(), otr, new OtrCutNsPrefixMapper());
	}
		
	public void addCategories() throws FileNotFoundException
	{	
		Otr otr = (Otr)JaxbUtil.loadJAXB(config.getString(OtrBootstrap.cfgXmlCategories), Otr.class);
		JaxbUtil.debug2(this.getClass(), otr, new OtrCutNsPrefixMapper());
		for(Category category : otr.getCategory())
		{
			category = gae.path("rest").path("series/addCategory").post(Category.class, category);
		}
	}
	
	public void addFormats() throws FileNotFoundException, UtilsProcessingException
	{
		Otr otr = (Otr)JaxbUtil.loadJAXB(config.getString(OtrBootstrap.cfgXmlFormats), Otr.class);
		JaxbUtil.debug2(this.getClass(), otr, new OtrCutNsPrefixMapper());
		for(Format format : otr.getFormat())
		{
			Format response = restAdmin.addFormat(format);
			logger.debug("Updated "+response.getType());
		}
	}
	
	public void addQualities() throws FileNotFoundException, UtilsProcessingException
	{
		Otr otr = (Otr)JaxbUtil.loadJAXB(config.getString(OtrBootstrap.cfgXmlQuality), Otr.class);
		JaxbUtil.debug2(this.getClass(), otr, new OtrCutNsPrefixMapper());
		for(Quality quality : otr.getQuality())
		{
			Quality response = restAdmin.addQuality(quality);
			JaxbUtil.debug2(this.getClass(), response, new OtrCutNsPrefixMapper());
		}
	}
	
	public void addSeries() throws FileNotFoundException, UtilsProcessingException
	{
		File dirEpisodes = new File(config.getString(OtrBootstrap.cfgXmlEpisodes));
		for(File f : dirEpisodes.listFiles())
		{
			if(f.getAbsolutePath().endsWith(".xml"))
			{
				logger.debug("\file: "+f.getAbsolutePath());
				Series series = (Series)JaxbUtil.loadJAXB(f.getAbsolutePath(), Series.class);
				logger.debug("\tSeasons: "+series.getSeason().size());
				series = restAdmin.addSeries(series);
				logger.debug("Updated "+series.getName());
			}
		}
	}
	
	public static void main(String[] args) throws ExlpConfigurationException, FileNotFoundException, UtilsProcessingException
	{
		Configuration config = OtrBootstrap.init();
		RestSeedData rest = new RestSeedData(config);
//		rest.all();
		
//		rest.addCategories();
		rest.addFormats();
		rest.addSeries();
//		
//		rest.addQualities();
	}
}