package net.sf.otrcutmp4.web.rest;

import java.io.FileNotFoundException;

import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.ns.NsPrefixMapperInterface;
import net.sf.otrcutmp4.controller.rest.CutRestClient;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.model.xml.ns.OtrCutNsPrefixMapper;
import net.sf.otrcutmp4.util.OtrBootstrap;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstCutRest
{
	final static Logger logger = LoggerFactory.getLogger(TstCutRest.class);
	
	private CutRestClient rest;
	private Configuration config;
	private NsPrefixMapperInterface nsPrefixMapper;
	
	public TstCutRest(Configuration config)
	{	
		this.config=config;
		rest = new CutRestClient(config);
		nsPrefixMapper = new OtrCutNsPrefixMapper();
	}
	
	public void upload() throws FileNotFoundException
	{
		String xmlIn = config.getString("test.xml.cutlistfinder");
		VideoFiles vFiles = (VideoFiles)JaxbUtil.loadJAXB(xmlIn, VideoFiles.class);
		JaxbUtil.debug2(this.getClass(), vFiles, nsPrefixMapper);
		String s = rest.request(vFiles);
		logger.debug("Saved Request with token: "+s);
	}
	
	public static void main(String[] args) throws ExlpConfigurationException, FileNotFoundException
	{
		Configuration config = OtrBootstrap.init();
		TstCutRest test = new TstCutRest(config);
		test.upload();
	}
}