package net.sf.otrcutmp4.controller.rest;

import javax.ws.rs.core.UriBuilder;

import net.sf.otrcutmp4.interfaces.rest.OtrCutRestService;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.OtrConfig.Url;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class CutRestClient implements OtrCutRestService
{
	final static Logger logger = LoggerFactory.getLogger(CutRestClient.class);
	
	private WebResource gae;
	
	public CutRestClient(OtrConfig otrConfig)
	{	
		ClientConfig cc = new DefaultClientConfig();
		Client client = Client.create(cc);
		gae = client.resource(UriBuilder.fromUri(otrConfig.getUrl(Url.GAE)).build());
	}
	
	@Override
	public String request(VideoFiles cutRequest)
	{
		return gae.path("rest").path("cut/request").post(String.class,cutRequest);
	}

	@Override
	public VideoFiles processed(String token)
	{
		return gae.path("rest").path("cut/processed/"+token).get(VideoFiles.class);
	}
}