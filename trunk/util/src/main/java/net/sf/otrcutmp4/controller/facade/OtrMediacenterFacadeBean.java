package net.sf.otrcutmp4.controller.facade;

import java.io.Serializable;

import javax.persistence.EntityManager;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.otrcutmp4.factory.ejb.mc.EjbCoverFactory;
import net.sf.otrcutmp4.interfaces.facade.OtrMediacenterFacade;
import net.sf.otrcutmp4.interfaces.model.Image;
import net.sf.otrcutmp4.interfaces.model.Episode;
import net.sf.otrcutmp4.interfaces.model.Movie;
import net.sf.otrcutmp4.interfaces.model.Season;
import net.sf.otrcutmp4.interfaces.model.Series;
import net.sf.otrcutmp4.interfaces.model.Storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OtrMediacenterFacadeBean<MOVIE extends Movie<IMAGE,STORAGE>,SERIES extends Series<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,SEASON extends Season<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,EPISODE extends Episode<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,IMAGE extends Image,STORAGE extends Storage>
				implements OtrMediacenterFacade<MOVIE,SERIES,SEASON,EPISODE,IMAGE,STORAGE>,Serializable
{	
	final static Logger logger = LoggerFactory.getLogger(OtrMediacenterFacadeBean.class);
	
	static final long serialVersionUID=1;

	protected EntityManager em;
	private UtilsFacade ufb;
	
	public OtrMediacenterFacadeBean(EntityManager em,UtilsFacade ufb)
	{
		this.em=em;
		this.ufb=ufb;
	}
	
    @Override
	public SERIES load(Class<SERIES> type, SERIES series,boolean withEpisodes)
	{
		series = em.find(type, series.getId());
		if(series.getSeasons().size()>0)
		{
			for(SEASON season : series.getSeasons())
			{
				season.getEpisodes().size();
			}
		}
		return series;
	}
    
	@Override public SEASON load(Class<SEASON> type, SEASON season)
	{
		season = em.find(type, season.getId());
		season.getEpisodes().size();
		return season;
	}
	
	@Override public MOVIE fMovie(Class<MOVIE> type, String name, int year) throws UtilsNotFoundException
	{
		return ufb.fByName(type, name);
	}

	@Override
	public SEASON fSeason(Class<SEASON> type, SERIES series, long nr) throws UtilsNotFoundException
	{
		return ufb.fByNr(type, "series", series, nr);
	}

	@Override
	public EPISODE fEpisode(Class<EPISODE> type, SEASON season, long nr) throws UtilsNotFoundException
	{
		return ufb.fByNr(type, "season", season, nr);
	}

	@Override
	public SERIES fSeries(Class<SERIES> type, String name) throws UtilsNotFoundException
	{
		return ufb.fByName(type, name);
	}

	@Override
	public EPISODE fcEpisode(Class<SERIES> clSeries, Class<SEASON> clSeason, Class<EPISODE> clEpisode, Class<IMAGE> clImage, net.sf.otrcutmp4.model.xml.series.Episode xmlEpisode)
	{
		SERIES series = fcSeries(clSeries,xmlEpisode.getSeason().getSeries());
		SEASON season = fcSeason(clSeason, series, xmlEpisode.getSeason());

		EPISODE episode=null;
		try
		{
			episode = fEpisode(clEpisode, season, xmlEpisode.getNr());
		}
		catch (UtilsNotFoundException e)
		{
			try
			{
				episode = clEpisode.newInstance();
				episode.setName(xmlEpisode.getName());
				episode.setNr(xmlEpisode.getNr());
				episode.setSeason(season);

		        em.persist(episode);
			}
			catch (InstantiationException e1) {e1.printStackTrace();}
			catch (IllegalAccessException e1) {e1.printStackTrace();}
		}
		
		season.getEpisodes().add(episode);
		
		if(xmlEpisode.isSetImage() && season.getCover()==null)
		{
			EjbCoverFactory<IMAGE> efCover = EjbCoverFactory.factory(clImage);
			IMAGE cover = efCover.build(xmlEpisode.getImage());
			em.persist(cover);
			season.setCover(cover);
			em.merge(season);
		}
		
		return episode;	
	}

	@Override
	public SERIES fcSeries(Class<SERIES> clSeries, net.sf.otrcutmp4.model.xml.series.Series xmlSeries)
	{
		SERIES series = null;
		try
		{
			series = ufb.fByName(clSeries, xmlSeries.getName());
		}
		catch (UtilsNotFoundException e)
		{
			try
			{
				series = clSeries.newInstance();
				series.setName(xmlSeries.getName());
		        em.persist(series);
			}
			catch (InstantiationException e1) {e1.printStackTrace();}
			catch (IllegalAccessException e1) {e1.printStackTrace();}
		}
		return series;
	}
	
	@Override
	public SEASON fcSeason(Class<SEASON> clSeason, SERIES series, net.sf.otrcutmp4.model.xml.series.Season xmlSeason)
	{
		SEASON season=null;
		try
		{
			season = fSeason(clSeason, series, xmlSeason.getNr());
		}
		catch (UtilsNotFoundException e)
		{
			try
			{
				season = clSeason.newInstance();
				season.setName(xmlSeason.getName());
				season.setNr(xmlSeason.getNr());
				season.setSeries(series);

		        em.persist(season);	       
			}
			catch (InstantiationException e1) {e1.printStackTrace();}
			catch (IllegalAccessException e1) {e1.printStackTrace();}
		}
		return season;	
	}
}