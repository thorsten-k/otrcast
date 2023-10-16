package de.kisner.otrcast.controller.facade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jeesl.controller.facade.jx.JeeslFacadeBean;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.model.util.UtilsRankedResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.api.facade.OtrVideoResolverFacade;
import de.kisner.otrcast.interfaces.model.Episode;
import de.kisner.otrcast.interfaces.model.Image;
import de.kisner.otrcast.interfaces.model.Movie;
import de.kisner.otrcast.interfaces.model.Season;
import de.kisner.otrcast.interfaces.model.Series;
import de.kisner.otrcast.interfaces.model.Storage;

public class OtrVideoResolverFacadeBean<MOVIE extends Movie<IMAGE,STORAGE>,
									SERIES extends Series<SERIES,SEASON,EPISODE,IMAGE>,
									SEASON extends Season<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
									EPISODE extends Episode<SEASON>,
									IMAGE extends Image,
									STORAGE extends Storage>
				extends JeeslFacadeBean
				implements Serializable,
						OtrVideoResolverFacade<MOVIE,SERIES,SEASON,EPISODE,IMAGE,STORAGE>
{	
	final static Logger logger = LoggerFactory.getLogger(OtrVideoResolverFacadeBean.class);
	
	static final long serialVersionUID=1;

	public OtrVideoResolverFacadeBean(EntityManager em)
	{
		super(em);
	}
	
	@Override
	public EPISODE fEpisode(Class<EPISODE> cEpiosode, long seriesId, long seasonNr, long episodeNr) throws JeeslNotFoundException
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<EPISODE> cQ = cB.createQuery(cEpiosode);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		Root<EPISODE> root = cQ.from(cEpiosode);
		
		Join<EPISODE,SEASON> season = root.join("season");
		Join<SEASON,SERIES> series = season.join("series");
		
		Path<Long> pSeriesId = series.get("id");
		Path<Long> pSeasonNr = season.get("nr");
		Path<Long> pEpisodeNr = root.get("nr");
		
		predicates.add(cB.equal(pSeriesId, seriesId));
		predicates.add(cB.equal(pSeasonNr, seasonNr));
		predicates.add(cB.equal(pEpisodeNr, episodeNr));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(root);
		
		TypedQuery<EPISODE> q = em.createQuery(cQ); 
		try	{return q.getSingleResult();}
		catch (NoResultException ex){throw new JeeslNotFoundException("Nothing found "+cEpiosode.getSimpleName()+" for series="+seriesId+" seasonNr="+seasonNr+" episodeNr="+episodeNr);}
		catch (NonUniqueResultException ex){throw new JeeslNotFoundException("Not Unique results for "+cEpiosode.getSimpleName()+" for series="+seriesId+" seasonNr="+seasonNr+" episodeNr="+episodeNr);}
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
			catch (JeeslNotFoundException e) {}
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
	
	@Override public List<SERIES> seriesFinder(Class<SERIES> cSeries, String seriesName)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<SERIES> cQ = cB.createQuery(cSeries);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		Root<SERIES> series = cQ.from(cSeries);
		
		if(seriesName!=null)
		{
			predicates.add(series.get("name").in(seriesName));
		}
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(series);
		
		return em.createQuery(cQ).getResultList();
	}

	@Override
	public List<UtilsRankedResult<SERIES>> rankedSeries(Class<SERIES> cSeries, String seriesName)
	{
		logger.warn("rankedSeries is currently not implemented here");
		return null;
	}
}