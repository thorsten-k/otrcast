package net.sf.otrcutmp4.controller.web.rest;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.exlp.util.DateUtil;
import net.sf.otrcutmp4.bootstrap.OtrCutMp4Bootstrap;
import net.sf.otrcutmp4.controller.facade.OtrMediacenterFacadeBean;
import net.sf.otrcutmp4.factory.xml.series.XmlMovieFactory;
import net.sf.otrcutmp4.factory.xml.series.XmlSeasonFactory;
import net.sf.otrcutmp4.factory.xml.series.XmlSeriesFactory;
import net.sf.otrcutmp4.interfaces.rest.OtrMediacenterRest;
import net.sf.otrcutmp4.model.OtrCover;
import net.sf.otrcutmp4.model.OtrEpisode;
import net.sf.otrcutmp4.model.OtrMovie;
import net.sf.otrcutmp4.model.OtrSeason;
import net.sf.otrcutmp4.model.OtrSeries;
import net.sf.otrcutmp4.model.OtrStorage;
import net.sf.otrcutmp4.model.xml.container.Otr;
import net.sf.otrcutmp4.model.xml.mc.ServerStatus;
import net.sf.otrcutmp4.model.xml.series.Movie;
import net.sf.otrcutmp4.model.xml.series.Season;
import net.sf.otrcutmp4.model.xml.series.Series;
import net.sf.otrcutmp4.util.query.SeriesQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/rest/mediacenter")
public class OtrMediacenterRestService implements OtrMediacenterRest
{
	final static Logger logger = LoggerFactory.getLogger(OtrMediacenterRestService.class);
	
	private EntityManager em;
	private UtilsFacadeBean ufb;
	private OtrMediacenterFacadeBean<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrCover,OtrStorage> osfb;
	
	private void init()
	{
		if(em==null)
		{
			EntityManagerFactory emf = OtrCutMp4Bootstrap.buildEmf();
			em = emf.createEntityManager();
		}
		if(ufb==null){ufb = new UtilsFacadeBean(em);}
		if(osfb==null){osfb = new OtrMediacenterFacadeBean<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrCover,OtrStorage>(em,ufb);}
	}
	
	@Override
	@GET @Path("/status")
	@Produces(MediaType.APPLICATION_XML)
	public ServerStatus status()
	{
		ServerStatus status = new ServerStatus();
		status.setLastRestart(DateUtil.toXmlGc(new Date()));
		return status;
	}
	
	// ---------------------- Movies ----------------------
	@Override
	@GET @Path("/movies")
	@Produces(MediaType.APPLICATION_XML)
	public Otr allMovies()
	{
		init();
		XmlMovieFactory<OtrMovie,OtrCover,OtrStorage> fMovie = new XmlMovieFactory<OtrMovie,OtrCover,OtrStorage>(SeriesQuery.get(SeriesQuery.Key.Movie));
		Otr otr = new Otr();
		for(OtrMovie ejb : ufb.all(OtrMovie.class))
		{
			otr.getMovie().add(fMovie.build(ejb));
		}
		return otr;
	}
	
	@Override
	@GET @Path("/movies/{id}")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.TEXT_PLAIN)
	public Movie movie(@PathParam("id") long movieId) throws UtilsNotFoundException
	{
		init();
		XmlMovieFactory<OtrMovie,OtrCover,OtrStorage> f = new XmlMovieFactory<OtrMovie,OtrCover,OtrStorage>(SeriesQuery.get(SeriesQuery.Key.MovieAll));
		OtrMovie ejb = ufb.find(OtrMovie.class, movieId);
		return f.build(ejb);
	}
	
	// ---------------------- Series ----------------------
	@Override
	@GET @Path("/series")
	@Produces(MediaType.APPLICATION_XML)
	public Otr allSeries()
	{
		init();
		XmlSeriesFactory<OtrSeries,OtrSeason,OtrEpisode,OtrCover,OtrStorage> f = new XmlSeriesFactory<OtrSeries,OtrSeason,OtrEpisode,OtrCover,OtrStorage>(SeriesQuery.get(SeriesQuery.Key.Series));
		Otr otr = new Otr();
		for(OtrSeries ejb : ufb.all(OtrSeries.class))
		{
			otr.getSeries().add(f.build(ejb));
		}
		return otr;
	}
	
	@Override
	@GET @Path("/series/{id}")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.TEXT_PLAIN)
	public Series seriesAll(@PathParam("id") long seriesId) throws UtilsNotFoundException
	{
		init();
		XmlSeriesFactory<OtrSeries,OtrSeason,OtrEpisode,OtrCover,OtrStorage> f = new XmlSeriesFactory<OtrSeries,OtrSeason,OtrEpisode,OtrCover,OtrStorage>(SeriesQuery.get(SeriesQuery.Key.SeriesAll));
		OtrSeries ejb = ufb.find(OtrSeries.class, seriesId);
		ejb = osfb.load(OtrSeries.class, ejb, false);
		return f.build(ejb);
	}

	@Override
	@GET @Path("/series/{id}/season")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.TEXT_PLAIN)
	public Series seriesWithSeason(@PathParam("id") long seriesId) throws UtilsNotFoundException
	{
		init();
		XmlSeriesFactory<OtrSeries,OtrSeason,OtrEpisode,OtrCover,OtrStorage> f = new XmlSeriesFactory<OtrSeries,OtrSeason,OtrEpisode,OtrCover,OtrStorage>(SeriesQuery.get(SeriesQuery.Key.SeriesWithSeason));
		OtrSeries ejb = ufb.find(OtrSeries.class, seriesId);
		ejb = osfb.load(OtrSeries.class, ejb, false);
		return f.build(ejb);
	}
	
	@Override
	@GET @Path("/series/{id}/season/episode")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.TEXT_PLAIN)
	public Season seasonWithEpisode(@PathParam("id") long seasonId) throws UtilsNotFoundException
	{
		init();
		XmlSeasonFactory<OtrSeries,OtrSeason,OtrEpisode,OtrCover,OtrStorage> f = new XmlSeasonFactory<OtrSeries,OtrSeason,OtrEpisode,OtrCover,OtrStorage>(SeriesQuery.get(SeriesQuery.Key.SeasonWithEpisodes));
		OtrSeason ejb = ufb.find(OtrSeason.class, seasonId);
		ejb = osfb.load(OtrSeason.class, ejb);
		Season season = f.build(ejb);
		return season;
	}

}