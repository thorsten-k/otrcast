package net.sf.otrcutmp4.controller.processor;

import java.io.File;
import java.io.IOException;

import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.otrcutmp4.controller.exception.OtrConfigurationException;
import net.sf.otrcutmp4.controller.exception.OtrProcessingException;
import net.sf.otrcutmp4.test.OtrClientTstBootstrap;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliOtrDownloader
{
	final static Logger logger = LoggerFactory.getLogger(CliOtrDownloader.class);
	
	private OtrFileDownloader otrDownloader;
	
	public CliOtrDownloader(Configuration config) throws OtrConfigurationException, ExlpConfigurationException
	{
		File fDownload = new File("/Volumes/ramdisk/dev/otr/key");
		otrDownloader = new OtrFileDownloader(fDownload);
	}
	
	public void test() throws OtrProcessingException, IOException
	{
		otrDownloader.download("url");
	}
		
	public static void main(String args[]) throws Exception
	{
		Configuration config = OtrClientTstBootstrap.init();
				
		CliOtrDownloader cli = new CliOtrDownloader(config);
		cli.test();
	}
}