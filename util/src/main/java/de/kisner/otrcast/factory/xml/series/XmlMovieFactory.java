package de.kisner.otrcast.factory.xml.series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.factory.xml.mc.XmlImageFactory;
import de.kisner.otrcast.factory.xml.mc.XmlStorageFactory;
import de.kisner.otrcast.interfaces.model.Image;
import de.kisner.otrcast.interfaces.model.Movie;
import de.kisner.otrcast.interfaces.model.Storage;
import de.kisner.otrcast.model.xml.otr.Query;

public class XmlMovieFactory<MOVIE extends Movie<COVER,STORAGE>,COVER extends Image,STORAGE extends Storage>
{	
	final static Logger logger = LoggerFactory.getLogger(XmlMovieFactory.class);
	
	private de.kisner.otrcast.model.xml.series.Movie q;
	
	public XmlMovieFactory(Query query){this(query.getMovie());}
	public XmlMovieFactory(de.kisner.otrcast.model.xml.series.Movie q){this.q=q;}
	
	public de.kisner.otrcast.model.xml.series.Movie build(Movie<COVER,STORAGE> ejb)
	{
		de.kisner.otrcast.model.xml.series.Movie xml = new de.kisner.otrcast.model.xml.series.Movie();
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetName()){xml.setName(ejb.getName());}
		if(q.isSetYear()){xml.setYear(ejb.getYear());}
		
		if(q.isSetImage() && ejb.getCover()!=null)
		{
			XmlImageFactory f = new XmlImageFactory(q.getImage());
			xml.setImage(f.build(ejb.getCover()));
		}
		
		if(q.isSetStorage() && ejb.getStorage()!=null)
		{
			XmlStorageFactory f = new XmlStorageFactory(q.getStorage());
			xml.setStorage(f.build(ejb.getStorage()));
		}
		
		return xml;
	}
	
	public static de.kisner.otrcast.model.xml.series.Movie build(String title, int year)
	{
		de.kisner.otrcast.model.xml.series.Movie xml = new de.kisner.otrcast.model.xml.series.Movie();
		xml.setName(title);
		xml.setYear(year);
		return xml;
		
	}
}
