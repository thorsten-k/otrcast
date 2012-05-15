package net.sf.otrcutmp4.controller.rest;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import net.sf.otrcutmp4.interfaces.rest.OtrAdminRest;
import net.sf.otrcutmp4.model.xml.otr.Format;
import net.sf.otrcutmp4.model.xml.otr.Quality;
import net.sf.otrcutmp4.model.xml.series.Series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class AdminRestClient implements OtrAdminRest
{
	final static Logger logger = LoggerFactory.getLogger(AdminRestClient.class);
	
	private WebResource gae;
	
	public AdminRestClient(String url)
	{	
		ClientConfig cc = new DefaultClientConfig();
		Client client = Client.create(cc);
		gae = client.resource(UriBuilder.fromUri(url).build());
	}
	
	@Override
	public Format addFormat(Format format)
	{
		return gae.path("rest").path("admin/add/format").accept(MediaType.APPLICATION_XML).post(Format.class,format);
	}
	
	@Override
	public Series addSeries(Series series)
	{
		return gae.path("rest").path("admin/add/series").accept(MediaType.APPLICATION_XML).post(Series.class,series);
	}
	
	
	@Override
	public Quality addQuality(Quality quality)
	{
		return gae.path("rest").path("admin/add/Quality").accept(MediaType.APPLICATION_XML).post(Quality.class,quality);
	}
}