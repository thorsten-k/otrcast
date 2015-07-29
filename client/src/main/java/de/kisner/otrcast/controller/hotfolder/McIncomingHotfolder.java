package de.kisner.otrcast.controller.hotfolder;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.OtrCutMp4Bootstrap;
import de.kisner.otrcast.controller.processor.mc.McImportProcessor;
import de.kisner.otrcast.util.OtrConfig;
import de.kisner.otrcast.util.OtrConfig.Dir;

public class McIncomingHotfolder
{	
	final static Logger logger = LoggerFactory.getLogger(McIncomingHotfolder.class);
	
	private CamelContext context;
	private OtrConfig config;
	
	public McIncomingHotfolder(OtrConfig config)
	{
		this.config=config;
		logger.info("Creating Hotfolder");
		context = new DefaultCamelContext();
	}
	
	public void addRoute() throws Exception
	{
		final StringBuffer sb = new StringBuffer();
		sb.append("file://");
		sb.append(config.getDir(Dir.IN));
		sb.append("?delete=true");
		
		context.addRoutes(new RouteBuilder()
		{
		    public void configure() {
		        from(sb.toString()).process(new McImportProcessor(config,OtrCutMp4Bootstrap.buildEmf(config).createEntityManager()));
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
