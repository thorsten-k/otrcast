package de.kisner.otrcast.controller.hotfolder;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.processor.hotfolder.TagFromFilenameProcessor;

public class OtrHotfolder
{	
	final static Logger logger = LoggerFactory.getLogger(OtrHotfolder.class);
	
	private CamelContext context;
	
	public OtrHotfolder()
	{
		logger.info("Creating Hotfolder");
		context = new DefaultCamelContext();
	}
	
	public void addRoute() throws Exception
	{
		context.addRoutes(new RouteBuilder() {
		    public void configure() {
		        from("file://src/test/resources/hotfolder").process(new TagFromFilenameProcessor()).to("file://target");
		    }
		});
	}
	
	public void startHotFolder() throws Exception
	{
		context.start();
		while(true)
		{
			Thread.sleep(1000);
		}
	}	
}
