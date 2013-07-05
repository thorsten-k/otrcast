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
import net.sf.otrcutmp4.bootstrap.OtrCutMp4Bootstrap;
import net.sf.otrcutmp4.controller.facade.OtrSeriesFacadeBean;
import net.sf.otrcutmp4.factory.xml.series.XmlSeriesFactory;
import net.sf.otrcutmp4.interfaces.rest.OtrMediacenterRest;
import net.sf.otrcutmp4.model.OtrEpisode;
import net.sf.otrcutmp4.model.OtrSeason;
import net.sf.otrcutmp4.model.OtrSeries;
import net.sf.otrcutmp4.model.xml.container.Otr;
import net.sf.otrcutmp4.model.xml.series.Series;
import net.sf.otrcutmp4.util.query.SeriesQuery;

@Path("/rest/mediacenter")
public class OtrMediacenterRestService implements OtrMediacenterRest
{
	private EntityManager em;
	private UtilsFacadeBean ufb;
	private OtrSeriesFacadeBean osfb;
	
	private void init()
	{
		if(em==null)
		{
			EntityManagerFactory emf = OtrCutMp4Bootstrap.buildEmf();
			em = emf.createEntityManager();
		}
		if(ufb==null){ufb = new UtilsFacadeBean(em);}
		if(osfb==null){osfb = new OtrSeriesFacadeBean(em,ufb);}
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
	public Series getSeason(@PathParam("id") long seriesId) throws UtilsNotFoundException
	{
		init();
		XmlSeriesFactory<OtrSeries,OtrSeason,OtrEpisode> f = new XmlSeriesFactory<OtrSeries,OtrSeason,OtrEpisode>(SeriesQuery.get(SeriesQuery.Key.SeriesWithSeason));
		OtrSeries ejb = ufb.find(OtrSeries.class, seriesId);
		ejb = osfb.load(OtrSeries.class, ejb);
		Series series = f.build(ejb);
		return series;
	}

	@Override
	@GET @Path("/last/restart")
	@Produces(MediaType.TEXT_PLAIN)
	public Date lastRestart()
	{
		// TODO Auto-generated method stub
		return new Date();
	}	
}