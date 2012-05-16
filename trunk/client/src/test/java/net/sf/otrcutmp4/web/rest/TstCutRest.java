package net.sf.otrcutmp4.web.rest;

import java.io.FileNotFoundException;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.ahtutils.web.rest.RestEasyPreemptiveClientExecutor;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.ns.NsPrefixMapperInterface;
import net.sf.otrcutmp4.interfaces.rest.OtrCutRest;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.test.OtrClientTstBootstrap;
import net.sf.otrcutmp4.util.OtrConfig;

import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstCutRest
{
	final static Logger logger = LoggerFactory.getLogger(TstCutRest.class);
	
	private OtrConfig otrConfig;
	private OtrCutRest rest;
	private NsPrefixMapperInterface nsPrefixMapper;
	
	public TstCutRest(OtrConfig otrConfig)
	{	
		this.otrConfig=otrConfig;
		
		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
		ClientExecutor clientExecutor = RestEasyPreemptiveClientExecutor.factory("user","pwd");
		rest = ProxyFactory.create(OtrCutRest.class, "http://localhost:8080/otr",clientExecutor);
	}
	
	public void upload() throws FileNotFoundException, UtilsProcessingException
	{
		String xmlIn = otrConfig.getKey("test.xml.cutlistfinder");
		VideoFiles vFiles = (VideoFiles)JaxbUtil.loadJAXB(xmlIn, VideoFiles.class);
		JaxbUtil.debug(this.getClass(), vFiles, nsPrefixMapper);
		String s = rest.addCutPackage(vFiles);
		logger.debug("Saved Request with token: "+s);
	}
	
	public static void main(String[] args) throws Exception
	{
		OtrConfig otrConfig = OtrClientTstBootstrap.initOtr();
		TstCutRest test = new TstCutRest(otrConfig);
		test.upload();
	}
}