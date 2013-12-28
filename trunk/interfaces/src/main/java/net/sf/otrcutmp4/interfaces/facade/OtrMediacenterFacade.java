package net.sf.otrcutmp4.interfaces.facade;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.otrcutmp4.interfaces.model.Image;
import net.sf.otrcutmp4.interfaces.model.Episode;
import net.sf.otrcutmp4.interfaces.model.Movie;
import net.sf.otrcutmp4.interfaces.model.Season;
import net.sf.otrcutmp4.interfaces.model.Series;
import net.sf.otrcutmp4.interfaces.model.Storage;

public interface OtrMediacenterFacade<MOVIE extends Movie<COVER,STORAGE>,SERIES extends Series<SERIES,SEASON,EPISODE,COVER,STORAGE>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER,STORAGE>,COVER extends Image,STORAGE extends Storage>
{	
	SEASON load(Class<SEASON> type, SEASON season);
	SERIES load(Class<SERIES> type, SERIES series, boolean withEpisodes);
	
	MOVIE fMovie(Class<MOVIE> type, String name, int year) throws UtilsNotFoundException;
	
	SERIES fSeries(Class<SERIES> type, String name) throws UtilsNotFoundException;
	SEASON fSeason(Class<SEASON> type, SERIES series, long nr) throws UtilsNotFoundException;
	EPISODE fEpisode(Class<EPISODE> type, SEASON season, long nr) throws UtilsNotFoundException;
	
	EPISODE fcEpisode(Class<SERIES> clSeries, Class<SEASON> clSeason, Class<EPISODE> clEpisode, Class<COVER> clCover, net.sf.otrcutmp4.model.xml.series.Episode episode);
	SEASON fcSeason(Class<SEASON> clSeason, SERIES series, net.sf.otrcutmp4.model.xml.series.Season season);
	SERIES fcSeries(Class<SERIES> clSeries, net.sf.otrcutmp4.model.xml.series.Series series);
}