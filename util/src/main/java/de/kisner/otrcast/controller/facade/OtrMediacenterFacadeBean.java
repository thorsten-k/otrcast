package de.kisner.otrcast.controller.facade;

import java.io.File;
import java.io.Serializable;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.api.facade.OtrMediacenterFacade;
import de.kisner.otrcast.controller.web.rss.DefaultUrlGenerator;
import de.kisner.otrcast.factory.ejb.mc.EjbCoverFactory;
import de.kisner.otrcast.factory.ejb.mc.EjbStorageFactory;
import de.kisner.otrcast.factory.xml.rss.XmlChannelFactory;
import de.kisner.otrcast.factory.xml.rss.XmlRssFactory;
import de.kisner.otrcast.interfaces.model.Episode;
import de.kisner.otrcast.interfaces.model.Image;
import de.kisner.otrcast.interfaces.model.Movie;
import de.kisner.otrcast.interfaces.model.Season;
import de.kisner.otrcast.interfaces.model.Series;
import de.kisner.otrcast.interfaces.model.Storage;
import de.kisner.otrcast.interfaces.web.UrlGenerator;
import de.kisner.otrcast.model.xml.rss.Channel;
import de.kisner.otrcast.model.xml.rss.Rss;
import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;

public class OtrMediacenterFacadeBean<MOVIE extends Movie<IMAGE,STORAGE>,
									SERIES extends Series<SERIES,SEASON,EPISODE,IMAGE>,
									SEASON extends Season<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
									EPISODE extends Episode<SEASON>,
									IMAGE extends Image,
									STORAGE extends Storage>
				extends UtilsFacadeBean
				implements Serializable,
							OtrMediacenterFacade<MOVIE,SERIES,SEASON,EPISODE,IMAGE,STORAGE>
{	
	final static Logger logger = LoggerFactory.getLogger(OtrMediacenterFacadeBean.class);
	
	static final long serialVersionUID=1;
	
	private UrlGenerator urlGenerator;
	
	public OtrMediacenterFacadeBean(EntityManager em){this(em,new DefaultUrlGenerator());}
	public OtrMediacenterFacadeBean(EntityManager em,UrlGenerator urlGenerator)
	{
		super(em);
		this.urlGenerator=urlGenerator;
	}
	
	@Override
	public STORAGE fcStorage(Class<STORAGE> cStorage, File f)
	{
		STORAGE storage = null;
		try {storage = this.fByName(cStorage, f.getAbsolutePath());}
		catch (UtilsNotFoundException e)
		{
			try
			{
				EjbStorageFactory<STORAGE> efStorage = EjbStorageFactory.factory(cStorage);
				storage = efStorage.build(f);
				storage = this.persist(storage);
			}
			catch (UtilsConstraintViolationException e1) {e1.printStackTrace();}
		}
		return storage;
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
		return this.fByName(type, name);
	}

	@Override
	public SEASON fSeason(Class<SEASON> type, SERIES series, long nr) throws UtilsNotFoundException
	{
		return this.fByNr(type, "series", series, nr);
	}

	@Override
	public EPISODE fEpisode(Class<EPISODE> type, SEASON season, long nr) throws UtilsNotFoundException
	{
		return this.fByNr(type, "season", season, nr);
	}

	@Override
	public SERIES fSeries(Class<SERIES> type, String name) throws UtilsNotFoundException
	{
		return this.fByName(type, name);
	}
	
	@Override
	public SERIES fcSeries(Class<SERIES> clSeries, de.kisner.otrcast.model.xml.series.Series xmlSeries)
	{
		SERIES series = null;
		try
		{
			series = this.fByName(clSeries, xmlSeries.getName());
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
	public SEASON fcSeason(Class<SEASON> clSeason, SERIES series, de.kisner.otrcast.model.xml.series.Season xmlSeason)
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

	@Override public EPISODE fcEpisode(Class<SERIES> clSeries, Class<SEASON> clSeason, Class<EPISODE> clEpisode, Class<IMAGE> clImage, de.kisner.otrcast.model.xml.series.Episode xmlEpisode)
	{
		SERIES series = fcSeries(clSeries,xmlEpisode.getSeason().getSeries());
		SEASON season = fcSeason(clSeason, series, xmlEpisode.getSeason());
		EPISODE episode = fcEpisode(clEpisode,season,xmlEpisode);
		
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
	
	@Override public EPISODE fcEpisode(Class<EPISODE> cEpisode, SEASON season, de.kisner.otrcast.model.xml.series.Episode xmlEpisode)
	{
		EPISODE episode=null;
		try
		{
			episode = fEpisode(cEpisode, season, xmlEpisode.getNr());
		}
		catch (UtilsNotFoundException e)
		{
			try
			{
				episode = cEpisode.newInstance();
				episode.setName(xmlEpisode.getName());
				episode.setNr(xmlEpisode.getNr());
				episode.setSeason(season);
		        em.persist(episode);
			}
			catch (InstantiationException e1) {e1.printStackTrace();}
			catch (IllegalAccessException e1) {e1.printStackTrace();}
		}
		
		return episode;	
	}

	@Override
	public Rss rss(SEASON season)
	{
		XmlChannelFactory<MOVIE,SERIES,SEASON,EPISODE,IMAGE,STORAGE> xfChannel = new XmlChannelFactory<MOVIE,SERIES,SEASON,EPISODE,IMAGE,STORAGE>(urlGenerator);
		
		Channel channel = xfChannel.build(season);
		return XmlRssFactory.build(channel);
	}
}