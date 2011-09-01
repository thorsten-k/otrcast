package net.sf.otrcutmp4.web.rest;

import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.otrcutmp4.controller.rest.CutRestClient;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.util.OtrBootstrap;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TstCutRest
{
	static Log logger = LogFactory.getLog(TstCutRest.class);
	
	private CutRestClient rest;
	
	public TstCutRest(Configuration config)
	{	
		rest = new CutRestClient(config);
	}
	
	public void test()
	{
		VideoFiles vf = new VideoFiles();
		String s = rest.request(vf);
		logger.debug(s);
	}
	
	public static void main(String[] args) throws ExlpConfigurationException
	{
		Configuration config = OtrBootstrap.init();
		TstCutRest rest = new TstCutRest(config);
		rest.test();
	}
}