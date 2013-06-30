package net.sf.otrcutmp4;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import net.sf.otrcutmp4.controller.exception.OtrInternalErrorException;
import net.sf.otrcutmp4.controller.web.rest.OtrMediacenterRestService;

import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

public class OtrMediaCenter
{
	public OtrMediaCenter()
	{
		EntityManagerFactory emf = OtrCutMp4Bootstrap.buildEmf();
        EntityManager em = emf.createEntityManager();
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
		otrMc.rest();
	}
}
