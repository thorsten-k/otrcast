package net.sf.otrcutmp4;

import java.io.File;

import net.sf.exlp.util.config.ConfigKey;
import net.sf.otrcutmp4.bootstrap.OtrCutMp4Bootstrap;
import net.sf.otrcutmp4.controller.exception.OtrInternalErrorException;
import net.sf.otrcutmp4.controller.processor.mc.MediaCenterScanner;
import net.sf.otrcutmp4.controller.web.rest.OtrMediacenterRestService;

import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OtrMediaCenter
{
	final static Logger logger = LoggerFactory.getLogger(OtrMediaCenter.class);
	
	private Configuration config;
	
	public OtrMediaCenter(Configuration config)
	{
		this.config=config;
	}
	
	public void scanMediathek(String path)
	{
		logger.info("Scanning for MP4");
		File f = new File("/Volumes/ramdisk/dev/otr/mp4");
		MediaCenterScanner mcs = new MediaCenterScanner(OtrCutMp4Bootstrap.buildEmf().createEntityManager());
		mcs.scan(f);
	}
	
	public void rest()
    {
    	ResteasyDeployment deployment = new ResteasyDeployment();
    	deployment.getActualResourceClasses().add(OtrMediacenterRestService.class);
    	
    	NettyJaxrsServer netty = new NettyJaxrsServer();
    	netty.setDeployment(deployment);
    	netty.setPort(config.getInt(ConfigKey.netRestPort));
    	netty.setRootResourcePath("");
    	netty.setSecurityDomain(null);
    	netty.start();
    }
	

	public static void main(String args[]) throws OtrInternalErrorException
	{		
		Configuration config = OtrCutMp4Bootstrap.init();
		OtrCutMp4Bootstrap.buildEmf().createEntityManager();
		OtrMediaCenter otrMc = new OtrMediaCenter(config);
		otrMc.scanMediathek("");
		otrMc.rest();
	}
}
