package net.sf.otrcutmp4.controller.hotfolder;

import net.sf.otrcutmp4.controller.processor.hotfolder.TagFromFilenameProcessor;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestOtrHotFolder
{	
	final static Logger logger = LoggerFactory.getLogger(TestOtrHotFolder.class);
	
	
	public static void main(String[] args) throws Exception
	{
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
		    public void configure() {
		        from("file://src/test/resources/hotfolder").process(new TagFromFilenameProcessor()).to("file://target");
		    }
		});
		context.start();
		while(true)
		{
			
		}
	}
	
	
}
