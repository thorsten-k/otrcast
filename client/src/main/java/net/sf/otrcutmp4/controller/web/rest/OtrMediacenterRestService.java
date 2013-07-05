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
import net.sf.otrcutmp4.controller.facade.OtrSeriesFacadeBean;
import net.sf.otrcutmp4.factory.xml.series.XmlSeasonFactory;
import net.sf.otrcutmp4.factory.xml.series.XmlSeriesFactory;
import net.sf.otrcutmp4.interfaces.rest.OtrMediacenterRest;
import net.sf.otrcutmp4.model.OtrEpisode;
import net.sf.otrcutmp4.model.OtrSeason;
import net.sf.otrcutmp4.model.OtrSeries;
import net.sf.otrcutmp4.model.xml.container.Otr;
import net.sf.otrcutmp4.model.xml.mc.ServerStatus;
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
	private OtrSeriesFacadeBean<OtrSeries,OtrSeason,OtrEpisode> osfb;
	
	private void init()
	{
		if(em==null)
		{
			EntityManagerFactory emf = OtrCutMp4Bootstrap.buildEmf();
			em = emf.createEntityManager();
		}
		if(ufb==null){ufb = new UtilsFacadeBean(em);}
		if(osfb==null){osfb = new OtrSeriesFacadeBean<OtrSeries,OtrSeason,OtrEpisode>(em,ufb);}
	}
	
	@Override
	@GET @Path("/series/all")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.TEXT_PLAIN)
	public Otr allSeries()
	{
		init();
		XmlSeriesFactory<OtrSeries,OtrSeason,OtrEpisode> f = new XmlSeriesFactory<OtrSeries,OtrSeason,OtrEpisode>(SeriesQuery.get(SeriesQuery.Key.Series));
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
	public Series seriesWithSeason(@PathParam("id") long seriesId) throws UtilsNotFoundException
	{
		init();
		XmlSeriesFactory<OtrSeries,OtrSeason,OtrEpisode> f = new XmlSeriesFactory<OtrSeries,OtrSeason,OtrEpisode>(SeriesQuery.get(SeriesQuery.Key.SeriesWithSeason));
		OtrSeries ejb = ufb.find(OtrSeries.class, seriesId);
		ejb = osfb.load(OtrSeries.class, ejb);
		Series series = f.build(ejb);
		return series;
	}
	
	@Override
	@GET @Path("/series/season/{id}")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.TEXT_PLAIN)
	public Season seasonWithEpisode(@PathParam("id") long seasonId) throws UtilsNotFoundException
	{
		init();
		XmlSeasonFactory<OtrSeries,OtrSeason,OtrEpisode> f = new XmlSeasonFactory<OtrSeries,OtrSeason,OtrEpisode>(SeriesQuery.get(SeriesQuery.Key.SeasonWithEpisodes));
		OtrSeason ejb = ufb.find(OtrSeason.class, seasonId);
		ejb = osfb.load(OtrSeason.class, ejb);
		Season season = f.build(ejb);
		return season;
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
}