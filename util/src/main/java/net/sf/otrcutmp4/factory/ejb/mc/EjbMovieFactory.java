package net.sf.otrcutmp4.factory.ejb.mc;

import net.sf.otrcutmp4.interfaces.model.Cover;
import net.sf.otrcutmp4.interfaces.model.Movie;
import net.sf.otrcutmp4.interfaces.model.Storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbMovieFactory<MOVIE extends Movie<COVER,STORAGE>,COVER extends Cover,STORAGE extends Storage>
{	
	final static Logger logger = LoggerFactory.getLogger(EjbMovieFactory.class);
	
	final Class<MOVIE> clMovie;
	final Class<COVER> clCover;
	final Class<STORAGE> clStorage;
	
	public EjbMovieFactory(final Class<MOVIE> clMovie,final Class<COVER> clCover,final Class<STORAGE> clStorage)
	{
		this.clMovie=clMovie;
		this.clCover=clCover;
		this.clStorage=clStorage;
	}
	 
	public static <MOVIE extends Movie<COVER,STORAGE>,COVER extends Cover,STORAGE extends Storage>
		EjbMovieFactory<MOVIE,COVER,STORAGE> factory(final Class<MOVIE> clMovie,final Class<COVER> clCover,final Class<STORAGE> clStorage)
	{
		return new EjbMovieFactory<MOVIE,COVER,STORAGE>(clMovie,clCover,clStorage);
	}
	
	public MOVIE build(net.sf.otrcutmp4.model.xml.series.Movie xml)
	{
		MOVIE ejb = null;
		
		try{ejb = clMovie.newInstance();}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		ejb.setName(xml.getName());
		ejb.setYear(xml.getYear());
		
		if(xml.isSetCover())
		{
			EjbCoverFactory<COVER> f = EjbCoverFactory.factory(clCover);
			ejb.setCover(f.build(xml.getCover()));
		}
		if(xml.isSetStorage())
		{
			EjbStorageFactory<STORAGE> f = EjbStorageFactory.factory(clStorage);
			ejb.setStorage(f.build(xml.getStorage()));
		}
		
		return ejb;
	}
}