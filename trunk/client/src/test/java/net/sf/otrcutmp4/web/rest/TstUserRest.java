package net.sf.otrcutmp4.web.rest;

import javax.ws.rs.core.UriBuilder;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.model.xml.ns.OtrCutNsPrefixMapper;
import net.sf.otrcutmp4.model.xml.user.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class TstUserRest
{
	final static Logger logger = LoggerFactory.getLogger(TstUserRest.class);
	
	private WebResource gae;
	
	public TstUserRest()
	{	
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		gae = client.resource(UriBuilder.fromUri("http://otr-series.appspot.com").build());
	}
	
	public void test()
	{
		User user = new User();
		user.setEmail("t.kisner@web.de");
		
		user = gae.path("rest").path("user/register").post(User.class, user);
		JaxbUtil.debug2(this.getClass(), user, new OtrCutNsPrefixMapper());
	}
	
	public static void main(String[] args)
	{
//		OtrCutMp4Bootstrap.initLogger();
		TstUserRest rest = new TstUserRest();
		rest.test();

	}
}
