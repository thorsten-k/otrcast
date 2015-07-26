package net.sf.otrcutmp4.controller.web.rest;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.interfaces.util.ConfigKey;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.OtrCutMp4Bootstrap;
import net.sf.otrcutmp4.controller.facade.OtrMediacenterFacadeBean;
import net.sf.otrcutmp4.controller.processor.RssXmlProcessor;
import net.sf.otrcutmp4.controller.web.rss.OtrCastUrlGenerator;
import net.sf.otrcutmp4.interfaces.facade.OtrMediacenterFacade;
import net.sf.otrcutmp4.interfaces.rest.OtrPodcastRest;
import net.sf.otrcutmp4.interfaces.web.UrlGenerator;
import net.sf.otrcutmp4.model.OtrEpisode;
import net.sf.otrcutmp4.model.OtrImage;
import net.sf.otrcutmp4.model.OtrMovie;
import net.sf.otrcutmp4.model.OtrSeason;
import net.sf.otrcutmp4.model.OtrSeries;
import net.sf.otrcutmp4.model.OtrStorage;
import net.sf.otrcutmp4.model.xml.rss.Rss;

public class CliPodcastRest
{
	final static Logger logger = LoggerFactory.getLogger(CliSimpleRest.class);
	
	private Configuration config;
	private OtrPodcastRest rest;
	
    public CliPodcastRest(Configuration config)
	{		
    	this.config=config;
		String restUrl = config.getString(ConfigKey.netRestUrl);
		logger.info("Connection to "+restUrl);
		
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(restUrl);

		rest = target.proxy(OtrPodcastRest.class);
	}
	
	public void remote()
	{
		Rss rss = rest.rss();
		JaxbUtil.info(rss);
	}
	
	public void reference() throws MalformedURLException, IOException
	{		
		String url = config.getString("test.url.podcast.reference");
		logger.info("Showing Reference for URL:"+url);
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = httpclient.execute(httpGet);
		
		try
		{
		    for(Header header : response.getAllHeaders())
		    {
		    	logger.debug(header.toString());
		    }
		    HttpEntity entity = response.getEntity();
		    
		    InputStream in = entity.getContent();
//		    IOUtils.copy(in,System.out);
		    EntityUtils.consume(entity);
		}
		finally {response.close();}
	}
	
	public void local() throws UtilsNotFoundException
	{
		OtrCutMp4Bootstrap.buildEmf(config);
		UrlGenerator urlGenerator = new OtrCastUrlGenerator();

		
		OtrMediacenterFacade<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage> fMc = new OtrMediacenterFacadeBean<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage>(OtrCutMp4Bootstrap.buildEmf().createEntityManager(),urlGenerator);
		for(OtrSeason s : fMc.all(OtrSeason.class))
		{
			logger.info(s.toString());
		}
		
		OtrSeason season = fMc.find(OtrSeason.class, 2);
		
		Rss rss = fMc.rss(season);
       
        JaxbUtil.info(rss);
//		RssXmlProcessor rssProcessor = new RssXmlProcessor();
//		JDomUtil.debug(rssProcessor.transform(rss));
	}
	
	public static void main(String[] args) throws ExlpConfigurationException, MalformedURLException, IOException, UtilsNotFoundException
	{
		Configuration config = OtrCutMp4Bootstrap.init();		
		CliPodcastRest rest = new CliPodcastRest(config);
		rest.reference();
//		rest.remote();
//		rest.local();
	}
}