package net.sf.otrcutmp4.interfaces.facade;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.otrcutmp4.interfaces.model.Cover;
import net.sf.otrcutmp4.interfaces.model.Episode;
import net.sf.otrcutmp4.interfaces.model.Season;
import net.sf.otrcutmp4.interfaces.model.Series;

public interface OtrMediacenterFacade<SERIES extends Series<SERIES,SEASON,EPISODE,COVER>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER>,COVER extends Cover>
{	
	SEASON load(Class<SEASON> type, SEASON season);
	SERIES load(Class<SERIES> type, SERIES series);
	
	SEASON fSeason(Class<SEASON> type, SERIES series, int nr) throws UtilsNotFoundException;
	EPISODE fEpisode(Class<EPISODE> type, SEASON season, int nr) throws UtilsNotFoundException;
}