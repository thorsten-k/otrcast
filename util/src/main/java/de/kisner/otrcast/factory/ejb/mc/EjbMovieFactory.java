package de.kisner.otrcast.factory.ejb.mc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.model.Image;
import de.kisner.otrcast.interfaces.model.Movie;
import de.kisner.otrcast.interfaces.model.Storage;

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
	
	public MOVIE build(de.kisner.otrcast.model.xml.video.tv.Movie xml)
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
		if(xml.isSetFile())
		{
			EjbStorageFactory<STORAGE> f = EjbStorageFactory.factory(clStorage);
			ejb.setStorage(f.build(xml.getFile()));
		}
		
		return ejb;
	}
}