package net.sf.otrcutmp4.controller.tagger;

import net.sf.otrcutmp4.test.AbstractUtilTest;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHotFolder extends AbstractUtilTest{
	
	final static Logger logger = LoggerFactory.getLogger(TestHotFolder.class);
	
	public static void main(String[] args) throws Exception
	{
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
		    public void configure() {
		        from("file://src/test/resources/hotfolder").process(new ProcessTagging()).to("file://target");
		    }
		});
		context.start();
		while(true)
		{
			
		}
	}
	
	
}
