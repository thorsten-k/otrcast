package net.sf.otrcutmp4.interfaces.facade;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.otrcutmp4.interfaces.model.Season;
import net.sf.otrcutmp4.interfaces.model.Series;

public interface OtrSeriesFacade 
{	
	<T extends Season> T load(Class<T> type, T season);
	<T extends Series> T load(Class<T> type, T series);
	
	<SEASON extends Season, SERIES extends Series> SEASON fSeason(Class<SEASON> type,SERIES series, int nr) throws UtilsNotFoundException;
}