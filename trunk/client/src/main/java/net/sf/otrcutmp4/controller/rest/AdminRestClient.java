package net.sf.otrcutmp4.controller.rest;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import net.sf.otrcutmp4.controller.interfaces.rest.OtrAdminRestService;
import net.sf.otrcutmp4.model.xml.otr.Format;
import net.sf.otrcutmp4.model.xml.otr.Quality;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class AdminRestClient implements OtrAdminRestService
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
		return gae.path("rest").path("admin/addFormat").accept(MediaType.APPLICATION_XML).post(Format.class,format);
	}
	
	@Override
	public Quality addQuality(Quality quality)
	{
		return gae.path("rest").path("admin/addQuality").accept(MediaType.APPLICATION_XML).post(Quality.class,quality);
	}
}