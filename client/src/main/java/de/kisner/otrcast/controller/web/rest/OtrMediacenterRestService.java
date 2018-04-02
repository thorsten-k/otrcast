package de.kisner.otrcast.controller.web.rest;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.exlp.util.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.factory.xml.series.XmlMovieFactory;
import de.kisner.otrcast.factory.xml.series.XmlSeasonFactory;
import de.kisner.otrcast.factory.xml.video.tv.XmlSeriesFactory;
import de.kisner.otrcast.interfaces.rest.OtrMediacenterRest;
import de.kisner.otrcast.model.*;
import de.kisner.otrcast.model.ejb.OtrEpisode;
import de.kisner.otrcast.model.ejb.OtrImage;
import de.kisner.otrcast.model.ejb.OtrMovie;
import de.kisner.otrcast.model.ejb.OtrSeason;
import de.kisner.otrcast.model.ejb.OtrSeries;
import de.kisner.otrcast.model.ejb.OtrStorage;
import de.kisner.otrcast.model.xml.container.Otr;
import de.kisner.otrcast.model.xml.mc.ServerStatus;
import de.kisner.otrcast.model.xml.video.tv.Movie;
import de.kisner.otrcast.model.xml.video.tv.Season;
import de.kisner.otrcast.model.xml.video.tv.Series;
import de.kisner.otrcast.util.query.xml.XmlTvQuery;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.Date;

@Path("/rest/mc")
public class OtrMediacenterRestService extends AbstractOtrRestService implements OtrMediacenterRest
{
	final static Logger logger = LoggerFactory.getLogger(OtrMediacenterRestService.class);
	
	@Override
	public ServerStatus status()
	{
		ServerStatus status = new ServerStatus();
		status.setLastRestart(DateUtil.toXmlGc(new Date()));
		return status;
	}
	
	// ---------------------- Movies ----------------------

    @Override
    public Otr allMovies()
	{
		init();
		XmlMovieFactory<OtrMovie,OtrImage,OtrStorage> fMovie = new XmlMovieFactory<OtrMovie,OtrImage,OtrStorage>(XmlTvQuery.get(XmlTvQuery.Key.Movie));
		Otr otr = new Otr();
		for(OtrMovie ejb : ufb.all(OtrMovie.class))
		{
			otr.getMovie().add(fMovie.build(ejb));
		}
		return otr;
	}
	
	@Override
	public Movie movie(@PathParam("id") long movieId) throws UtilsNotFoundException
	{
		init();
		XmlMovieFactory<OtrMovie,OtrImage,OtrStorage> f = new XmlMovieFactory<OtrMovie,OtrImage,OtrStorage>(XmlTvQuery.get(XmlTvQuery.Key.MovieAll));
		OtrMovie ejb = ufb.find(OtrMovie.class, movieId);
		return f.build(ejb);
	}
	
	// ---------------------- Series ----------------------

    @Override
	public Otr allSeries()
	{
		init();
		XmlSeriesFactory<OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage> f = new XmlSeriesFactory<OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage>(XmlTvQuery.get(XmlTvQuery.Key.Series));
		Otr otr = new Otr();
		for(OtrSeries ejb : ufb.all(OtrSeries.class))
		{
			otr.getSeries().add(f.build(ejb));
		}
		return otr;
	}
	
	@Override
	public Series seriesAll(@PathParam("id") long seriesId) throws UtilsNotFoundException
	{
		init();
		XmlSeriesFactory<OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage> f = new XmlSeriesFactory<OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage>(XmlTvQuery.get(XmlTvQuery.Key.SeriesAll));
		OtrSeries ejb = ufb.find(OtrSeries.class, seriesId);
		ejb = osfb.load(OtrSeries.class, ejb, false);
		return f.build(ejb);
	}

	@Override
	public Series seriesWithSeason(@PathParam("id") long seriesId) throws UtilsNotFoundException
	{
		init();
		XmlSeriesFactory<OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage> f = new XmlSeriesFactory<OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage>(XmlTvQuery.get(XmlTvQuery.Key.SeriesWithSeason));
		OtrSeries ejb = ufb.find(OtrSeries.class, seriesId);
		ejb = osfb.load(OtrSeries.class, ejb, false);
		return f.build(ejb);
	}
	
	@Override
	public Season seasonWithEpisode(@PathParam("id") long seasonId) throws UtilsNotFoundException
	{
		init();
		XmlSeasonFactory<OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage> f = new XmlSeasonFactory<OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage>(XmlTvQuery.get(XmlTvQuery.Key.SeasonWithEpisodes));
		OtrSeason ejb = ufb.find(OtrSeason.class, seasonId);
		ejb = osfb.load(OtrSeason.class, ejb);
		Season season = f.build(ejb);
		return season;
	}

}