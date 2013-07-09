package net.sf.otrcutmp4.controller.web.rest;

import java.io.File;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.config.ConfigKey;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.interfaces.rest.OtrMediacenterRest;
import net.sf.otrcutmp4.model.xml.container.Otr;
import net.sf.otrcutmp4.model.xml.series.Movie;
import net.sf.otrcutmp4.model.xml.series.Series;
import net.sf.otrcutmp4.test.OtrClientTestBootstrap;

import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliMediaCenterRest
{
	final static Logger logger = LoggerFactory.getLogger(CliMediaCenterRest.class);
	
	private OtrMediacenterRest rest;
	
	private File fMcExport,fMcSeries,fMcMovies;
	
	public CliMediaCenterRest(Configuration config)
	{		
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(config.getString(ConfigKey.netRestUrl));

		rest = target.proxy(OtrMediacenterRest.class);
		fMcExport = new File(config.getString("test.mediacenter.export"));
		fMcSeries = new File(fMcExport,"series");fMcSeries.mkdir();
		fMcMovies = new File(fMcExport,"movies");fMcMovies.mkdir();
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
			catch (UtilsNotFoundException e) {e.printStackTrace();}
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
				File f = new File(fMcSeries,series.getId()+".xml");
				JaxbUtil.save(f, series, true);
			}
			catch (UtilsNotFoundException e) {e.printStackTrace();}
		}
	}
	
	public static void main(String[] args) throws ExlpConfigurationException
	{
		Configuration config = OtrClientTestBootstrap.init();
		CliMediaCenterRest rest = new CliMediaCenterRest(config);
		rest.export();
	}
}