package de.kisner.otrcast.app;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.OtrCastBootstrap;
import de.kisner.otrcast.controller.web.servlet.ImageServlet;
import de.kisner.otrcast.controller.web.servlet.PodcastServlet;
import de.kisner.otrcast.controller.web.servlet.StorageServlet;

public class OtrCastServer 
{
	final static Logger logger = LoggerFactory.getLogger(OtrCastServer.class);
	
	public OtrCastServer() throws Exception
	{
		logger.info("Starting Jetty Server");
		
		Server server = new Server(8080);
		 	 
         WebAppContext ctxJsf = new WebAppContext();
         ctxJsf.setDescriptor("./src/main/webapp/WEB-INF/web.xml");
         ctxJsf.setResourceBase("./src/main/webapp");
         ctxJsf.setContextPath("/cast");
         ctxJsf.setParentLoaderPriority(true);            
                  
         HandlerList handlers = new HandlerList();
         handlers.setHandlers(new Handler[] { ctxJsf});
         server.setHandler(handlers);

         server.start();
         server.join();
	}
	
	public static void main(String args[]) throws Exception
	{
		OtrCastBootstrap.init();
		
		new OtrCastServer();
	}
}