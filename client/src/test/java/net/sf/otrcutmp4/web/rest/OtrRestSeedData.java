package net.sf.otrcutmp4.web.rest;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.configuration.Configuration;
import org.exlp.controller.handler.web.rest.DelayedUrlConfig;
import org.exlp.util.jx.JaxbUtil;
import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jeesl.exception.processing.UtilsProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.rest.OtrAdminRest;
import de.kisner.otrcast.model.xml.container.Otr;
import de.kisner.otrcast.model.xml.otr.Format;
import de.kisner.otrcast.model.xml.otr.Quality;
import de.kisner.otrcast.model.xml.video.tv.Category;
import de.kisner.otrcast.model.xml.video.tv.Series;
import de.kisner.otrcast.util.OtrBootstrap;
import net.sf.exlp.exception.ExlpConfigurationException;

public class OtrRestSeedData
{
	final static Logger logger = LoggerFactory.getLogger(OtrRestSeedData.class);
	
	private OtrAdminRest rest;
	private Configuration config;
	
	public OtrRestSeedData(Configuration config)
	{	
		this.config=config;
				
		ResteasyClient client = new ResteasyClientBuilder().build();
		client.register(new BasicAuthentication("user","pwd"));
		ResteasyWebTarget restTarget = client.target(DelayedUrlConfig.resolve(config));
		rest = restTarget.proxy(OtrAdminRest.class);
	}
	
	public void all()
	{
		logger.warn("NYI");
//		Otr otr = restAdmin.allSeries();
//		JaxbUtil.debug(otr, new OtrCutNsPrefixMapper());
	}
		
	public void addCategories() throws FileNotFoundException
	{	
		Otr otr = (Otr)JaxbUtil.loadJAXB(config.getString(OtrBootstrap.cfgXmlCategories), Otr.class);
		JaxbUtil.debug(otr);
		for(Category category : otr.getCategory())
		{
			rest.addCategory(category);
		}
	}
	
	public void addFormats() throws FileNotFoundException, UtilsProcessingException
	{
		Otr otr = (Otr)JaxbUtil.loadJAXB(config.getString(OtrBootstrap.cfgXmlFormats), Otr.class);
		JaxbUtil.debug(otr);
		for(Format format : otr.getFormat())
		{
			Format response = rest.addFormat(format);
			logger.debug("Updated "+response.getType());
		}
	}
	
	public void addQualities() throws FileNotFoundException, UtilsProcessingException
	{
		Otr otr = (Otr)JaxbUtil.loadJAXB(config.getString(OtrBootstrap.cfgXmlQuality), Otr.class);
		JaxbUtil.debug(otr);
		for(Quality quality : otr.getQuality())
		{
			Quality response = rest.addQuality(quality);
			JaxbUtil.debug(response);
		}
	}
	
	public void addSeries() throws FileNotFoundException, UtilsProcessingException
	{
		File dirEpisodes = new File(config.getString(OtrBootstrap.cfgXmlEpisodes));
		logger.info("Adding Episodes from "+dirEpisodes.getAbsolutePath());
		for(File f : dirEpisodes.listFiles())
		{
			if(f.getAbsolutePath().endsWith(".xml"))
			{
				logger.debug("\file: "+f.getAbsolutePath());
				Series series = (Series)JaxbUtil.loadJAXB(f.getAbsolutePath(), Series.class);
				logger.debug("\tSeasons: "+series.getSeason().size());
				series = rest.addSeries(series);
				logger.debug("Updated "+series.getName());
			}
		}
	}
	
	public static void main(String[] args) throws ExlpConfigurationException, FileNotFoundException, UtilsProcessingException
	{
		Configuration config = OtrBootstrap.init();
		OtrRestSeedData rest = new OtrRestSeedData(config);
//		rest.all();
		
//		rest.addCategories();
//		rest.addFormats();
		rest.addSeries();
//		
//		rest.addQualities();
	}
}