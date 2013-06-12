package net.sf.otrcutmp4.controller.processor;

import java.io.File;

import net.sf.otrcutmp4.test.AbstractUtilTest;
import net.sf.otrcutmp4.test.OtrUtilTestBootstrap;

import org.apache.commons.configuration.Configuration;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestImlProcessor extends AbstractUtilTest
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