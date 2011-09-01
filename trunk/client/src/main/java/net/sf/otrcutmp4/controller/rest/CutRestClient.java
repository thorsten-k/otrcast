package net.sf.otrcutmp4.controller.rest;

import javax.ws.rs.core.UriBuilder;

import net.sf.otrcutmp4.controller.interfaces.rest.OtrCutRestService;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.util.OtrBootstrap;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class CutRestClient implements OtrCutRestService
{
	static Log logger = LogFactory.getLog(CutRestClient.class);
	
	private WebResource gae;
	
	public CutRestClient(Configuration config)
	{	
		ClientConfig cc = new DefaultClientConfig();
		Client client = Client.create(cc);
		gae = client.resource(UriBuilder.fromUri(config.getString(OtrBootstrap.cfgUrlGae)).build());
	}
	
	@Override
	public String request(VideoFiles cutRequest)
	{
		String s = gae.path("rest").path("cut/request").post(String.class,cutRequest);
		return s;
	}
}