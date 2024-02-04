package net.sf.otrcutmp4.controller.web.rest;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.configuration.Configuration;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.exlp.util.jx.JaxbUtil;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.api.facade.OtrMediacenterFacade;
import de.kisner.otrcast.controller.OtrCastBootstrap;
import de.kisner.otrcast.controller.facade.OtrMediacenterFacadeBean;
import de.kisner.otrcast.factory.txt.TxtUrlFactory;
import de.kisner.otrcast.interfaces.rest.OtrPodcastRest;
import de.kisner.otrcast.interfaces.web.UrlGenerator;
import de.kisner.otrcast.model.ejb.OtrEpisode;
import de.kisner.otrcast.model.ejb.OtrImage;
import de.kisner.otrcast.model.ejb.OtrMovie;
import de.kisner.otrcast.model.ejb.OtrSeason;
import de.kisner.otrcast.model.ejb.OtrSeries;
import de.kisner.otrcast.model.ejb.OtrStorage;
import de.kisner.otrcast.model.xml.rss.Rss;
import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.interfaces.util.ConfigKey;

public class CliPodcastRest
{
	final static Logger logger = LoggerFactory.getLogger(CliSimpleRest.class);
	
	private Configuration config;
	private OtrPodcastRest rest;
	
    public CliPodcastRest(Configuration config)
	{		
    	this.config=config;
		String restUrl = config.getString(ConfigKey.netRestUrlProduction);
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
		    
//		    InputStream in = entity.getContent();
//		    IOUtils.copy(in,System.out);
		    EntityUtils.consume(entity);
		}
		finally {response.close();}
	}
	
	public void local() throws JeeslNotFoundException
	{
		OtrCastBootstrap.buildEmf(config);
		UrlGenerator urlGenerator = new TxtUrlFactory();

		
		OtrMediacenterFacade<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage> fMc = new OtrMediacenterFacadeBean<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage>(OtrCastBootstrap.buildEmf().createEntityManager(),urlGenerator);
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
	
	public static void main(String[] args) throws ExlpConfigurationException, MalformedURLException, IOException, JeeslNotFoundException
	{
		Configuration config = OtrCastBootstrap.init();		
		CliPodcastRest rest = new CliPodcastRest(config);
		rest.reference();
//		rest.remote();
//		rest.local();
	}
}