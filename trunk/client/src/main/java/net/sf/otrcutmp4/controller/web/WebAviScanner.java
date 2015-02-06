package net.sf.otrcutmp4.controller.web;

import net.sf.ahtutils.web.rest.auth.RestEasyPreemptiveClientExecutor;
import net.sf.otrcutmp4.controller.processor.SrcDirProcessor;
import net.sf.otrcutmp4.interfaces.rest.OtrUserRest;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.OtrConfig.Credential;
import net.sf.otrcutmp4.util.OtrConfig.Dir;

import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebAviScanner
{
	final static Logger logger = LoggerFactory.getLogger(WebAviScanner.class);

	private OtrUserRest rest;
	private OtrConfig otrConfig;
	
	public WebAviScanner(OtrConfig otrConfig)
	{
		this.otrConfig=otrConfig;
		String host = otrConfig.getUrl(OtrConfig.Url.OTR);
		logger.info("Connecting to "+host);
		
		ClientExecutor clientExecutor = RestEasyPreemptiveClientExecutor.factory(
				otrConfig.getCredential(Credential.EMAIL,""),
				otrConfig.getCredential(Credential.PWD,""));
		rest = ProxyFactory.create(OtrUserRest.class, host,clientExecutor);
	}
	
	public void scan(SrcDirProcessor srcDirProcessor)
	{
		VideoFiles vf = srcDirProcessor.scan(otrConfig.getDir(Dir.AVI),true);
		rest.scan(vf);
	}
}