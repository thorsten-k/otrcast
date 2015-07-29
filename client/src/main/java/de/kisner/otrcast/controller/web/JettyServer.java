package de.kisner.otrcast.controller.web;

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

import de.kisner.otrcast.controller.web.servlet.ImageServlet;
import de.kisner.otrcast.controller.web.servlet.PodcastServlet;
import de.kisner.otrcast.controller.web.servlet.StorageServlet;

public class JettyServer 
{
	final static Logger logger = LoggerFactory.getLogger(JettyServer.class);
	
	public JettyServer() throws Exception
	{
		logger.info("Starting Jetty Server");
		
		Server server = new Server(8080);

		 ResourceHandler fileHandler = new ResourceHandler();
		 fileHandler.setResourceBase("./src");
		 
		 ContextHandler ctx = new ContextHandler("/files");
		 ctx.setHandler(fileHandler);
		 	 
         WebAppContext context = new WebAppContext();
         context.setDescriptor("./src/main/webapp/WEB-INF/web.xml");
         context.setResourceBase("./src/main/webapp");
         context.setContextPath("/cast");
         context.setParentLoaderPriority(true);            
         
         ServletContextHandler ctxServlet = new ServletContextHandler(ServletContextHandler.SESSIONS);
         ctxServlet.setContextPath("/servlet");
         ctxServlet.addServlet(new ServletHolder(new PodcastServlet()),"/rss/*");
         ctxServlet.addServlet(new ServletHolder(new StorageServlet()),"/file/*");
         ctxServlet.addServlet(new ServletHolder(new ImageServlet()),"/image/*");
         
         HandlerList handlers = new HandlerList();
         handlers.setHandlers(new Handler[] { context, ctx, ctxServlet});
         server.setHandler(handlers);

         server.start();
         server.join();
	}
}