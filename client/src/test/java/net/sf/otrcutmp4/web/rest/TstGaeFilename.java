package net.sf.otrcutmp4.web.rest;

import java.io.File;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.model.xml.ns.OtrCutNsPrefixMapper;
import net.sf.otrcutmp4.model.xml.series.Tags;
import net.sf.otrcutmp4.test.OtrClientTstBootstrap;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.SrcDirProcessor;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class TstGaeFilename
{
	static Log logger = LogFactory.getLog(TstGaeFilename.class);
	
	private WebResource gae;
	private Configuration config;
	
	public TstGaeFilename(Configuration config)
	{	
		this.config=config;
		ClientConfig cc = new DefaultClientConfig();
		Client client = Client.create(cc);
		gae = client.resource(UriBuilder.fromUri(config.getString(OtrConfig.urlOtrSeries)).build());
	}
	
	public void test()
	{
		SrcDirProcessor aviProcessor = new SrcDirProcessor();
		VideoFiles vFiles = aviProcessor.readFiles(new File(config.getString(OtrConfig.dirHqAvi)),SrcDirProcessor.VideType.avi);
		JaxbUtil.debug2(this.getClass(), vFiles, new OtrCutNsPrefixMapper());
		for(VideoFile vf : vFiles.getVideoFile())
		{
			String id = vf.getOtrId().getName()+"."+vf.getOtrId().getType();
			
			Tags tags = gae.path("rest").path("series/tags/"+id).accept(MediaType.APPLICATION_XML).get(Tags.class);
			logger.debug(id+" Response: "+tags.getTag().size());
			
		}
	}
	
	public static void main(String[] args) throws ExlpConfigurationException
	{
		Configuration config = OtrClientTstBootstrap.init();
		TstGaeFilename rest = new TstGaeFilename(config);
		rest.test();
	}
}
