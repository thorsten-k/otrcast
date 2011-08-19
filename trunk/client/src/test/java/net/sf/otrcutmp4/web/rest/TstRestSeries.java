package net.sf.otrcutmp4.web.rest;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import net.sf.otrcutmp4.OtrCutMp4Bootstrap;
import net.sf.otrcutmp4.model.xml.user.User;

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
	
	public void test()
	{
		String s = gae.path("rest").path("series/resolve/xx").get(String.class);
		logger.debug("Response: "+s);
	}
	
	public static void main(String[] args)
	{
		OtrCutMp4Bootstrap.initLogger();
		TstRestSeries rest = new TstRestSeries();
		rest.test();

	}
}
