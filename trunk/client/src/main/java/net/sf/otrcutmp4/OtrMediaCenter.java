package net.sf.otrcutmp4;

import java.io.File;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import net.sf.otrcutmp4.controller.exception.OtrInternalErrorException;
import net.sf.otrcutmp4.controller.processor.mc.MediaCenterScanner;
import net.sf.otrcutmp4.controller.web.rest.OtrMediacenterRestService;

import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OtrMediaCenter
{
	final static Logger logger = LoggerFactory.getLogger(OtrMediaCenter.class);
	
	private EntityManagerFactory emf;
	
	public OtrMediaCenter()
	{
		emf = OtrCutMp4Bootstrap.buildEmf();
        EntityManager em = emf.createEntityManager();
	}
	
	public void scanMediathek()
	{
		logger.info("Scanning for MP4");
		File f = new File("/Volumes/Volume/Series");
		MediaCenterScanner mcs = new MediaCenterScanner(emf.createEntityManager());
		mcs.scan(f);
	}
	
	public void rest()
    {
    	ResteasyDeployment deployment = new ResteasyDeployment();
    	deployment.getActualResourceClasses().add(OtrMediacenterRestService.class);
    	
    	NettyJaxrsServer netty = new NettyJaxrsServer();
    	netty.setDeployment(deployment);
    	netty.setPort(18080);
    	netty.setRootResourcePath("");
    	netty.setSecurityDomain(null);
    	netty.start();
    }
	
	

	public static void main(String args[]) throws OtrInternalErrorException
	{		
		OtrCutMp4Bootstrap.initLogger("log4j.debug.xml");
		OtrMediaCenter otrMc = new OtrMediaCenter();
		otrMc.scanMediathek();
		otrMc.rest();
	}
}
