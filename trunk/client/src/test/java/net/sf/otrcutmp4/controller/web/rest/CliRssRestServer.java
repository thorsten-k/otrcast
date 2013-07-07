package net.sf.otrcutmp4.controller.web.rest;

import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.otrcutmp4.test.OtrClientTestBootstrap;

import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

public class CliRssRestServer
{
    public CliRssRestServer()
    {

    }
    
    public void rest()
    {
    	ResteasyDeployment deployment = new ResteasyDeployment();
    	deployment.getActualResourceClasses().add(OtrPodcastRestService.class);
    	
    	NettyJaxrsServer netty = new NettyJaxrsServer();
    	netty.setDeployment(deployment);
    	netty.setPort(8080);
    	netty.setRootResourcePath("");
    	netty.setSecurityDomain(null);
    	netty.start();
    }

    public static void main(String[] args) throws ExlpConfigurationException
    {
    	Configuration config = OtrClientTestBootstrap.init();
        new CliRssRestServer().rest();
    }
}