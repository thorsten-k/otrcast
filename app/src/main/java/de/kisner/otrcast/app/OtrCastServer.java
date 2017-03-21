package de.kisner.otrcast.app;

import org.apache.commons.cli.ParseException;
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
import de.kisner.otrcast.controller.exception.OtrConfigurationException;
import de.kisner.otrcast.controller.web.servlet.ImageServlet;
import de.kisner.otrcast.controller.web.servlet.PodcastServlet;
import de.kisner.otrcast.controller.web.servlet.StorageServlet;
import de.kisner.otrcast.model.xml.OtrCastNsPrefixMapper;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.ahtutils.util.cli.UtilsCliOption;
import net.sf.exlp.util.xml.JaxbUtil;

public class OtrCastServer 
{
	final static Logger logger = LoggerFactory.getLogger(OtrCastServer.class);
	
	public OtrCastServer() throws Exception
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
	
	public static void main(String args[]) throws Exception
	{
		OtrCastBootstrap.init();
		
		new OtrCastServer();
	}
}