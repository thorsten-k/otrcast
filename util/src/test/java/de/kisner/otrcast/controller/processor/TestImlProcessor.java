package de.kisner.otrcast.controller.processor;

import java.io.File;

import org.apache.commons.configuration.Configuration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.AbstractOtrcastTest;
import de.kisner.otrcast.OtrUtilTestBootstrap;
import de.kisner.otrcast.controller.processor.ImlProcessor;

public class TestImlProcessor extends AbstractOtrcastTest
{
	final static Logger logger = LoggerFactory.getLogger(TestImlProcessor.class);
	
	private ImlProcessor imlProcessor;
	
	@Before
	public void init()
	{
		imlProcessor = new ImlProcessor();
	}
	
	public void hello(String fPlist)
	{
		imlProcessor.laod(new File(fPlist));
		imlProcessor.parse();
	}
	
	@Test @Ignore public void dummy(){}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = OtrUtilTestBootstrap.init();
		
		String iml = config.getString("xml.itunes.lib");
		
		logger.info("Lib: "+iml);
		
		TestImlProcessor test = new TestImlProcessor();
		test.init();
		test.hello(iml);
	}
}