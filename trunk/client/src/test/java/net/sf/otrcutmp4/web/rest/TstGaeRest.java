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

public class TstGaeRest
{
	static Log logger = LogFactory.getLog(TstGaeRest.class);
	
	private WebResource gae;
	
	public TstGaeRest()
	{	
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		gae = client.resource(UriBuilder.fromUri("http://otr-series.appspot.com").build());
	}
	
	public void test()
	{
		User user = new User();
		user.setEmail("t.kisner@web.de");
		
		gae.path("rest").path("user/register").post(user);
	}
	
	public static void main(String[] args)
	{
//		OtrCutMp4Bootstrap.initLogger();
		TstGaeRest rest = new TstGaeRest();
		rest.test();

	}
}
