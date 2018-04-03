package de.kisner.otrcast.app;

import java.io.File;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.api.rest.OtrDavRest;
import de.kisner.otrcast.controller.OtrCastBootstrap;
import de.kisner.otrcast.controller.tag.OtrLibraryScanner;
import de.kisner.otrcast.model.xml.video.Videos;
import net.sf.exlp.util.xml.JaxbUtil;

public class OtrWebDavServer 
{
	final static Logger logger = LoggerFactory.getLogger(OtrWebDavServer.class);
	
	public OtrWebDavServer() throws Exception
	{
		Server server = new Server(8080);

		WebAppContext context = new WebAppContext();
		context.setDescriptor("WEB-INF/web.xml");
		context.setResourceBase("src/main/webapp");
		context.setContextPath("/");
		context.setParentLoaderPriority(true);
		
		server.setHandler(context);
		server.start();
		server.join();
	}
	
	public static void main(String args[]) throws Exception
	{		
        OtrCastBootstrap.initLogger(OtrCastBootstrap.logConfig);
		
        OtrLibraryScanner scanner = new OtrLibraryScanner();
        Videos videos = scanner.scan(new File("/Users/thorsten/Dropbox/tmp/mp4"));
        JaxbUtil.info(videos);
        
        ResteasyClient client = new ResteasyClientBuilder().build();
		client.register(new BasicAuthentication("myUser","myPwd"));
		ResteasyWebTarget restTarget = client.target("http://192.168.202.26:8080/otrcast");
		OtrDavRest rest = restTarget.proxy(OtrDavRest.class);
		rest.uploadRepository(videos);
        
		new OtrWebDavServer();
	}
}