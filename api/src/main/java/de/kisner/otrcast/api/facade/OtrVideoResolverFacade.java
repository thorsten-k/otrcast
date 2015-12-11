package de.kisner.otrcast.api.facade;

import java.util.List;

import de.kisner.otrcast.interfaces.model.Episode;
import de.kisner.otrcast.interfaces.model.Image;
import de.kisner.otrcast.interfaces.model.Movie;
import de.kisner.otrcast.interfaces.model.Season;
import de.kisner.otrcast.interfaces.model.Series;
import de.kisner.otrcast.interfaces.model.Storage;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.ranking.UtilsRankedResult;

public interface OtrVideoResolverFacade<MOVIE extends Movie<COVER,STORAGE>,
										SERIES extends Series<SERIES,SEASON,EPISODE,COVER,STORAGE>,
										SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,
										EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER,STORAGE>,
										COVER extends Image,STORAGE extends Storage>
		extends UtilsFacade
{	
	List<EPISODE> episodeFinder(Class<EPISODE> cEpiosode, Long otrId, String seriesName, Integer seasonNr, Integer episodeNr, String episodeName);
	
	List<UtilsRankedResult<SERIES>> rankedSeries(Class<SERIES> cSeries, String seriesName);
	List<SERIES> seriesFinder(Class<SERIES> cSeries, String seriesName);
}