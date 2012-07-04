package net.sf.otrcutmp4.controller.web;

import net.sf.ahtutils.web.rest.RestEasyPreemptiveClientExecutor;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.interfaces.rest.OtrCutRest;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.test.AbstractClientTest;
import net.sf.otrcutmp4.test.OtrClientTstBootstrap;

import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstRestCutlistDownloader extends AbstractClientTest
{ 
	final static Logger logger = LoggerFactory.getLogger(TstRestCutlistDownloader.class);
			
	public static void main(String args[]) throws Exception
	{
		Configuration config = OtrClientTstBootstrap.init();
		
		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
		ClientExecutor clientExecutor = RestEasyPreemptiveClientExecutor.factory("user","pwd");
		OtrCutRest rest = ProxyFactory.create(OtrCutRest.class, config.getString("url.otrseries"), clientExecutor);
		
		VideoFiles vFiles = rest.findCutPackage("1341384741423");
	//	new TstRestCutlistDownloader(vFiles);
		logger.info("JAXB: "+vFiles);
		JaxbUtil.debug(vFiles);
		
	}
}