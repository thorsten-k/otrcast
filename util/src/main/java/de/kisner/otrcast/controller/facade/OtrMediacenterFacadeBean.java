package de.kisner.otrcast.controller.facade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.web.rss.DefaultUrlGenerator;
import de.kisner.otrcast.factory.ejb.mc.EjbCoverFactory;
import de.kisner.otrcast.factory.xml.rss.XmlChannelFactory;
import de.kisner.otrcast.factory.xml.rss.XmlRssFactory;
import de.kisner.otrcast.interfaces.facade.OtrMediacenterFacade;
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
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;

public class OtrMediacenterFacadeBean<MOVIE extends Movie<IMAGE,STORAGE>,
									SERIES extends Series<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
									SEASON extends Season<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
									EPISODE extends Episode<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
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
	public List<EPISODE> episodeFinder(Class<EPISODE> cEpiosode, Long otrId, String seriesName, Integer seasonNr, Integer episodeNr, String episodeName)
	{
		if(otrId!=null)
		{
			try
			{
				EPISODE e = this.find(cEpiosode, otrId);
				List<EPISODE> list = new ArrayList<EPISODE>();
				list.add(e);
				return list;
			}
			catch (UtilsNotFoundException e) {}
		}
		
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<EPISODE> cQ = cB.createQuery(cEpiosode);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		Root<EPISODE> root = cQ.from(cEpiosode);
		
		Join<EPISODE, SEASON> season = root.join("season");
		Join<SEASON, SERIES> series = season.join("series");
		
		if(episodeName!=null)
		{
			predicates.add(root.get("name").in(episodeName));
		}
	
		if(episodeNr!=null)
		{
			predicates.add(root.get("nr").in(episodeNr));
		}
		
		if(seasonNr!=null)
		{
			predicates.add(season.get("nr").in(seasonNr));
		}
		
		if(seriesName!=null)
		{
			predicates.add(series.get("name").in(seriesName));
		}
			
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(root).distinct(true);
		
		return em.createQuery(cQ).getResultList();
	}

	@Override
	public Rss rss(SEASON season)
	{
		XmlChannelFactory<MOVIE,SERIES,SEASON,EPISODE,IMAGE,STORAGE> xfChannel = new XmlChannelFactory<MOVIE,SERIES,SEASON,EPISODE,IMAGE,STORAGE>(urlGenerator);
		
		Channel channel = xfChannel.build(season);
		return XmlRssFactory.build(channel);
	}
}