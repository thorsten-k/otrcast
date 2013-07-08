package net.sf.otrcutmp4.controller.facade;

import java.io.Serializable;

import javax.persistence.EntityManager;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.otrcutmp4.interfaces.facade.OtrMediacenterFacade;
import net.sf.otrcutmp4.interfaces.model.Cover;
import net.sf.otrcutmp4.interfaces.model.Episode;
import net.sf.otrcutmp4.interfaces.model.Movie;
import net.sf.otrcutmp4.interfaces.model.Season;
import net.sf.otrcutmp4.interfaces.model.Series;
import net.sf.otrcutmp4.interfaces.model.Storage;

public class OtrMediacenterFacadeBean<MOVIE extends Movie<COVER,STORAGE>,SERIES extends Series<SERIES,SEASON,EPISODE,COVER>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER>,COVER extends Cover,STORAGE extends Storage>
				implements OtrMediacenterFacade<MOVIE,SERIES,SEASON,EPISODE,COVER,STORAGE>,Serializable
{	
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
	public SEASON fSeason(Class<SEASON> type, SERIES series, int nr) throws UtilsNotFoundException
	{
		return ufb.fByNr(type, "series", series, nr);
	}

	@Override
	public EPISODE fEpisode(Class<EPISODE> type, SEASON season, int nr) throws UtilsNotFoundException
	{
		return ufb.fByNr(type, "season", season, nr);
	}

	@Override
	public SERIES fSeries(Class<SERIES> type, String name) throws UtilsNotFoundException
	{
		return ufb.fByName(type, name);
	}
}