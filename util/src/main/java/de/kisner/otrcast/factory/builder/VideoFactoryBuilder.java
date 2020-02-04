package de.kisner.otrcast.factory.builder;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.factory.ejb.tv.EjbEpisodeFactory;
import de.kisner.otrcast.interfaces.model.Episode;
import de.kisner.otrcast.interfaces.model.Image;
import de.kisner.otrcast.interfaces.model.Movie;
import de.kisner.otrcast.interfaces.model.Season;
import de.kisner.otrcast.interfaces.model.Series;
import de.kisner.otrcast.interfaces.model.Storage;

public class VideoFactoryBuilder<L extends JeeslLang, D extends JeeslDescription,
									MOVIE extends Movie<COVER,STORAGE>,
									SERIES extends Series<SERIES,SEASON,EPISODE,COVER>,
									SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,
									EPISODE extends Episode<SEASON>,
									COVER extends Image,
									STORAGE extends Storage>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(VideoFactoryBuilder.class);

	private final Class<EPISODE> cEpisode;
   
	public VideoFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<EPISODE> cEpisode)
	{
		super(cL,cD);
		this.cEpisode=cEpisode;
	}
	
	public EjbEpisodeFactory<MOVIE,SERIES,SEASON,EPISODE,COVER,STORAGE> factory()
	{
		return new EjbEpisodeFactory<MOVIE,SERIES,SEASON,EPISODE,COVER,STORAGE>(cEpisode);
	}
}