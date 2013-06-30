package net.sf.otrcutmp4.controller.facade;

import java.io.Serializable;

import javax.persistence.EntityManager;

import net.sf.otrcutmp4.interfaces.facade.OtrSeriesFacade;
import net.sf.otrcutmp4.interfaces.model.Season;
import net.sf.otrcutmp4.interfaces.model.Series;

public class OtrSeriesFacadeBean implements OtrSeriesFacade,Serializable
{	
	static final long serialVersionUID=1;

	protected EntityManager em;
	
	public OtrSeriesFacadeBean(EntityManager em)
	{
		this.em=em;
	}
	
    @Override
	public <T extends Series> T load(Class<T> type, T series)
	{
		series = em.find(type, series.getId());
		series.getSeasons().size();
		return series;
	}
    
	@Override public <T extends Season> T load(Class<T> type, T season)
	{
		season = em.find(type, season.getId());
		season.getEpisodes().size();
		return season;
	}
}