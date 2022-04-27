package net.sf.otrcutmp4.controller.web.rest;

import java.io.File;

import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.interfaces.util.ConfigKey;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.test.OtrClientTestBootstrap;

import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.rest.OtrMediacenterRest;
import de.kisner.otrcast.model.xml.container.Otr;
import de.kisner.otrcast.model.xml.video.tv.Movie;
import de.kisner.otrcast.model.xml.video.tv.Series;

public class CliMediaCenterRest
{
	final static Logger logger = LoggerFactory.getLogger(CliMediaCenterRest.class);
	
	private OtrMediacenterRest rest;
	
	private File fMcExport,fMcSeries,fMcMovies;
	
	public CliMediaCenterRest(Configuration config)
	{		
		String restUrl = config.getString(ConfigKey.netRestUrlProduction);
		logger.info("Connection to "+restUrl);
		
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(restUrl);

		rest = target.proxy(OtrMediacenterRest.class);
		fMcExport = new File(config.getString("test.mediacenter.export"));
		fMcSeries = new File(fMcExport,"series");fMcSeries.mkdir();
		fMcMovies = new File(fMcExport,"movies");fMcMovies.mkdir();
		logger.info("Export Directory: "+fMcExport.getAbsolutePath());
	}
	
	public void export()
	{
		exportMovies();
		exportSeries();
	}
	
	private void exportMovies()
	{
		logger.info("Exporting Movies");
		Otr otrMovies = rest.allMovies();
		JaxbUtil.save(new File(fMcExport,"movies.xml"), otrMovies, true);
		for(Movie movie : otrMovies.getMovie())
		{
			try
			{
				movie = rest.movie(movie.getId());
				File f = new File(fMcMovies,movie.getId()+".xml");
				JaxbUtil.save(f, movie, true);
			}
			catch (JeeslNotFoundException e) {e.printStackTrace();}
		}
	}
	
	private void exportSeries()
	{
		logger.info("Exporting Series");
		Otr otrSeries = rest.allSeries();
		JaxbUtil.save(new File(fMcExport,"series.xml"), otrSeries, true);
		for(Series series : otrSeries.getSeries())
		{
			try
			{
				series = rest.seriesAll(series.getId());
				logger.info("Series "+series.getName());
				File f = new File(fMcSeries,series.getId()+".xml");
				JaxbUtil.save(f, series, true);
			}
			catch (JeeslNotFoundException e) {e.printStackTrace();}
		}
	}
	
	public static void main(String[] args) throws ExlpConfigurationException
	{
		Configuration config = OtrClientTestBootstrap.init();
		CliMediaCenterRest rest = new CliMediaCenterRest(config);
		rest.export();
	}
}