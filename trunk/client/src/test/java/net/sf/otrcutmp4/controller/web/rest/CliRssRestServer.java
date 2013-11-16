package net.sf.otrcutmp4.controller.web.rest;

import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.OtrCutMp4Bootstrap;
import net.sf.otrcutmp4.model.xml.rss.Rss;
import net.sf.otrcutmp4.test.OtrClientTestBootstrap;
import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

public class CliRssRestServer
{
    public CliRssRestServer(Configuration config)
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

    public void rss()
    {
        OtrPodcastRestService podcastService = new OtrPodcastRestService();
        Rss rss = podcastService.rss();
        JaxbUtil.info(rss);
    }

    public static void main(String[] args) throws ExlpConfigurationException
    {
    	Configuration config = OtrClientTestBootstrap.init();
        OtrCutMp4Bootstrap.buildEmf(config);

    	CliRssRestServer cli = new CliRssRestServer(config);
    	cli.rest();
        cli.rss();
    }
}