package de.kisner.otrcast.api.facade;

import java.util.List;

import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.util.UtilsRankedResult;

import de.kisner.otrcast.interfaces.model.Episode;
import de.kisner.otrcast.interfaces.model.Image;
import de.kisner.otrcast.interfaces.model.Movie;
import de.kisner.otrcast.interfaces.model.Season;
import de.kisner.otrcast.interfaces.model.Series;
import de.kisner.otrcast.interfaces.model.Storage;

public interface OtrVideoResolverFacade<MOVIE extends Movie<COVER,STORAGE>,
										SERIES extends Series<SERIES,SEASON,EPISODE,COVER>,
										SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,
										EPISODE extends Episode<SEASON>,
										COVER extends Image,STORAGE extends Storage>
		extends JeeslFacade
{	
	List<EPISODE> episodeFinder(Class<EPISODE> cEpiosode, Long otrId, String seriesName, Integer seasonNr, Integer episodeNr, String episodeName);
	EPISODE fEpisode(Class<EPISODE> cEpiosode, long seriesId, long seasonNr, long episodeNr) throws JeeslNotFoundException;
	
	List<UtilsRankedResult<SERIES>> rankedSeries(Class<SERIES> cSeries, String seriesName);
	List<SERIES> seriesFinder(Class<SERIES> cSeries, String seriesName);
}