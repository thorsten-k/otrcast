package net.sf.otrcutmp4.web.rest;

import net.sf.ahtutils.web.rest.RestEasyPreemptiveClientExecutor;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.interfaces.rest.OtrUserRest;
import net.sf.otrcutmp4.model.xml.ns.OtrCutNsPrefixMapper;
import net.sf.otrcutmp4.model.xml.user.User;

import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstUserRest
{
	final static Logger logger = LoggerFactory.getLogger(TstUserRest.class);
	
	private OtrUserRest rest;
	
	public TstUserRest()
	{	
		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
		ClientExecutor clientExecutor = RestEasyPreemptiveClientExecutor.factory("user","pwd");
		rest = ProxyFactory.create(OtrUserRest.class, "http://localhost:8080/otr",clientExecutor);
	}
	
	public void test()
	{
		User user = new User();
		user.setEmail("t.kisner@web.de");
		
		user = rest.register(user);
		JaxbUtil.debug(this.getClass(), user, new OtrCutNsPrefixMapper());
	}
	
	public static void main(String[] args)
	{
//		OtrCutMp4Bootstrap.initLogger();
		TstUserRest rest = new TstUserRest();
		rest.test();

	}
}
