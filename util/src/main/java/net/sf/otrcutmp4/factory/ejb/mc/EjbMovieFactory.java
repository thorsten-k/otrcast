package net.sf.otrcutmp4.factory.ejb.mc;

import net.sf.otrcutmp4.interfaces.model.Cover;
import net.sf.otrcutmp4.interfaces.model.Episode;
import net.sf.otrcutmp4.interfaces.model.Movie;
import net.sf.otrcutmp4.interfaces.model.Season;
import net.sf.otrcutmp4.interfaces.model.Series;
import net.sf.otrcutmp4.interfaces.model.Storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbMovieFactory<MOVIE extends Movie<COVER,STORAGE>,SERIES extends Series<SERIES,SEASON,EPISODE,COVER>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER>,COVER extends Cover,STORAGE extends Storage>
{	
	final static Logger logger = LoggerFactory.getLogger(EjbMovieFactory.class);
	
	final Class<MOVIE> movieClass;
	final Class<COVER> coverClass;
	
	 public EjbMovieFactory(final Class<MOVIE> movieClass,final Class<COVER> coverClass)
	 {
		 this.movieClass=movieClass;
	     this.coverClass=coverClass;
	 }
	 
	 public static <MOVIE extends Movie<COVER,STORAGE>,SERIES extends Series<SERIES,SEASON,EPISODE,COVER>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER>,COVER extends Cover,STORAGE extends Storage>
	 	EjbMovieFactory<MOVIE,SERIES,SEASON,EPISODE,COVER,STORAGE> factory(final Class<MOVIE> movieClass,final Class<COVER> coverClass)
	 {
		 return new EjbMovieFactory<MOVIE,SERIES,SEASON,EPISODE,COVER,STORAGE>(movieClass,coverClass);
	 }
	
	public MOVIE build(net.sf.otrcutmp4.model.xml.series.Movie movie)
	{
		MOVIE ejb = null;
		
		try{ejb = movieClass.newInstance();}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		ejb.setName(movie.getName());
		ejb.setYear(movie.getYear());
		
		if(movie.isSetCover())
		{
			EjbCoverFactory<COVER> f = EjbCoverFactory.factory(coverClass);
			ejb.setCover(f.build(movie.getCover()));
		}
		
		return ejb;
	}
}