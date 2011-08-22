package net.sf.otrcutmp4.controller;

import javax.ws.rs.core.UriBuilder;

import net.sf.otrcutmp4.model.xml.series.OtrId;
import net.sf.otrcutmp4.util.OtrConfig;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class SeriesTagger
{
	static Log logger = LogFactory.getLog(SeriesTagger.class);
	
	private WebResource gae;
	
	public SeriesTagger(Configuration config)
	{
		ClientConfig cc = new DefaultClientConfig();
		Client client = Client.create(cc);
		gae = client.resource(UriBuilder.fromUri(config.getString(OtrConfig.urlOtrSeries)).build());
	}
	
	
	public void tag(OtrId otrId)
	{
		
	}
}