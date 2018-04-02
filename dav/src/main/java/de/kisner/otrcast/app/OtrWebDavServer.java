package de.kisner.otrcast.app;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.OtrCastBootstrap;

public class OtrWebDavServer 
{
	final static Logger logger = LoggerFactory.getLogger(OtrWebDavServer.class);
	
	public OtrWebDavServer() throws Exception
	{
		Server server = new Server(8080);

		WebAppContext context = new WebAppContext();
		context.setDescriptor("WEB-INF/web.xml");
		context.setResourceBase("./src/main/webapp");
		context.setContextPath("/");
		context.setParentLoaderPriority(true);
		
		server.setHandler(context);
		server.start();
		server.join();
	}
	
	public static void main(String args[]) throws Exception
	{		
        OtrCastBootstrap.initLogger(OtrCastBootstrap.logConfig);
		
		logger.info("Test with:");
		logger.info("\thttp://localhost:8080/app/index.jsf");
		
		new OtrWebDavServer();
	}
}