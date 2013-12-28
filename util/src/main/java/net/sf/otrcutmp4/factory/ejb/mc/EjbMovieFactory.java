package net.sf.otrcutmp4.factory.ejb.mc;

import net.sf.otrcutmp4.interfaces.model.Image;
import net.sf.otrcutmp4.interfaces.model.Movie;
import net.sf.otrcutmp4.interfaces.model.Storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbMovieFactory<MOVIE extends Movie<IMAGE,STORAGE>,IMAGE extends Image,STORAGE extends Storage>
{	
	final static Logger logger = LoggerFactory.getLogger(EjbMovieFactory.class);
	
	final Class<MOVIE> clMovie;
	final Class<IMAGE> clImage;
	final Class<STORAGE> clStorage;
	
	public EjbMovieFactory(final Class<MOVIE> clMovie,final Class<IMAGE> clImage,final Class<STORAGE> clStorage)
	{
		this.clMovie=clMovie;
		this.clImage=clImage;
		this.clStorage=clStorage;
	}
	 
	public static <MOVIE extends Movie<IMAGE,STORAGE>,IMAGE extends Image,STORAGE extends Storage>
		EjbMovieFactory<MOVIE,IMAGE,STORAGE> factory(final Class<MOVIE> clMovie,final Class<IMAGE> clImage,final Class<STORAGE> clStorage)
	{
		return new EjbMovieFactory<MOVIE,IMAGE,STORAGE>(clMovie,clImage,clStorage);
	}
	
	public MOVIE build(net.sf.otrcutmp4.model.xml.series.Movie xml)
	{
		MOVIE ejb = null;
		
		try{ejb = clMovie.newInstance();}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		ejb.setName(xml.getName());
		ejb.setYear(xml.getYear());
		
		if(xml.isSetImage())
		{
			EjbCoverFactory<IMAGE> f = EjbCoverFactory.factory(clImage);
			ejb.setCover(f.build(xml.getImage()));
		}
		if(xml.isSetStorage())
		{
			EjbStorageFactory<STORAGE> f = EjbStorageFactory.factory(clStorage);
			ejb.setStorage(f.build(xml.getStorage()));
		}
		
		return ejb;
	}
}