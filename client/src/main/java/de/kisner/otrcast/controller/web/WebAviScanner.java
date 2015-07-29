package de.kisner.otrcast.controller.web;

import net.sf.ahtutils.web.rest.auth.RestEasyPreemptiveClientExecutor;

import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.processor.SrcDirProcessor;
import de.kisner.otrcast.interfaces.rest.OtrUserRest;
import de.kisner.otrcast.model.xml.cut.VideoFiles;
import de.kisner.otrcast.util.OtrConfig;
import de.kisner.otrcast.util.OtrConfig.Credential;
import de.kisner.otrcast.util.OtrConfig.Dir;

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
		
		String user = otrConfig.getCredential(Credential.EMAIL,"");
		String pwd = otrConfig.getCredential(Credential.PWD,"");
		
		ClientExecutor clientExecutor = RestEasyPreemptiveClientExecutor.factory(user,pwd);
		rest = ProxyFactory.create(OtrUserRest.class, host,clientExecutor);
	}
	
	public VideoFiles scan(SrcDirProcessor srcDirProcessor)
	{
		VideoFiles vf = srcDirProcessor.scan(otrConfig.getDir(Dir.AVI),true);
		rest.scan(vf);
		return vf;
	}
}