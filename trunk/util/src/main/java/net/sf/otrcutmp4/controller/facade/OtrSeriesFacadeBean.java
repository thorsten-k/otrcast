package net.sf.otrcutmp4.controller.facade;

import java.io.Serializable;

import javax.persistence.EntityManager;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.otrcutmp4.interfaces.facade.OtrSeriesFacade;
import net.sf.otrcutmp4.interfaces.model.Episode;
import net.sf.otrcutmp4.interfaces.model.Season;
import net.sf.otrcutmp4.interfaces.model.Series;

public class OtrSeriesFacadeBean <SERIES extends Series<SERIES,SEASON,EPISODE>, SEASON extends Season<SERIES,SEASON,EPISODE>, EPISODE extends Episode<SERIES,SEASON,EPISODE>>
				implements OtrSeriesFacade<SERIES,SEASON,EPISODE>,Serializable
{	
	static final long serialVersionUID=1;

	protected EntityManager em;
	private UtilsFacade ufb;
	
	public OtrSeriesFacadeBean(EntityManager em,UtilsFacade ufb)
	{
		this.em=em;
		this.ufb=ufb;
	}
	
    @Override
	public SERIES load(Class<SERIES> type, SERIES series)
	{
		series = em.find(type, series.getId());
		series.getSeasons().size();
		return series;
	}
    
	@Override public SEASON load(Class<SEASON> type, SEASON season)
	{
		season = em.find(type, season.getId());
		season.getEpisodes().size();
		return season;
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
	
}