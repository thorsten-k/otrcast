package de.kisner.otrcast.api.facade;

import java.io.File;

import de.kisner.otrcast.interfaces.model.Episode;
import de.kisner.otrcast.interfaces.model.Image;
import de.kisner.otrcast.interfaces.model.Movie;
import de.kisner.otrcast.interfaces.model.Season;
import de.kisner.otrcast.interfaces.model.Series;
import de.kisner.otrcast.interfaces.model.Storage;
import de.kisner.otrcast.model.xml.rss.Rss;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;

public interface OtrMediacenterFacade<MOVIE extends Movie<COVER,STORAGE>,
										SERIES extends Series<SERIES,SEASON,EPISODE,COVER>,
										SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,
										EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER,STORAGE>,
										COVER extends Image,STORAGE extends Storage>
		extends UtilsFacade
{	
	STORAGE fcStorage(Class<STORAGE> cStorage, File f);
	
	SEASON load(Class<SEASON> type, SEASON season);
	SERIES load(Class<SERIES> type, SERIES series, boolean withEpisodes);
	
	MOVIE fMovie(Class<MOVIE> type, String name, int year) throws UtilsNotFoundException;
	
	SERIES fSeries(Class<SERIES> type, String name) throws UtilsNotFoundException;
	SEASON fSeason(Class<SEASON> type, SERIES series, long nr) throws UtilsNotFoundException;
	EPISODE fEpisode(Class<EPISODE> type, SEASON season, long nr) throws UtilsNotFoundException;
	
	SERIES fcSeries(Class<SERIES> clSeries, de.kisner.otrcast.model.xml.series.Series series);
	SEASON fcSeason(Class<SEASON> clSeason, SERIES series, de.kisner.otrcast.model.xml.series.Season season);
	EPISODE fcEpisode(Class<SERIES> clSeries, Class<SEASON> clSeason, Class<EPISODE> clEpisode, Class<COVER> clCover, de.kisner.otrcast.model.xml.series.Episode episode);
	EPISODE fcEpisode(Class<EPISODE> clEpisode, SEASON season, de.kisner.otrcast.model.xml.series.Episode episode);
		
	Rss rss(SEASON season);
}