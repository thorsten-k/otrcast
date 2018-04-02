package de.kisner.otrcast.factory.xml.series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.factory.xml.mc.XmlImageFactory;
import de.kisner.otrcast.factory.xml.mc.XmlFileFactory;
import de.kisner.otrcast.interfaces.model.Image;
import de.kisner.otrcast.interfaces.model.Movie;
import de.kisner.otrcast.interfaces.model.Storage;
import de.kisner.otrcast.model.xml.otr.Query;

public class XmlMovieFactory<MOVIE extends Movie<COVER,STORAGE>,COVER extends Image,STORAGE extends Storage>
{	
	final static Logger logger = LoggerFactory.getLogger(XmlMovieFactory.class);
	
	private de.kisner.otrcast.model.xml.video.tv.Movie q;
	
	private XmlFileFactory xfFile;
	
	public XmlMovieFactory(Query query){this(query.getMovie());}
	public XmlMovieFactory(de.kisner.otrcast.model.xml.video.tv.Movie q)
	{
		this.q=q;
		if(q.isSetFile()) {xfFile = new XmlFileFactory(q.getFile());}
	}
	
	public de.kisner.otrcast.model.xml.video.tv.Movie build(Movie<COVER,STORAGE> ejb)
	{
		de.kisner.otrcast.model.xml.video.tv.Movie xml = new de.kisner.otrcast.model.xml.video.tv.Movie();
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetName()){xml.setName(ejb.getName());}
		if(q.isSetYear()){xml.setYear(ejb.getYear());}
		
		if(q.isSetImage() && ejb.getCover()!=null)
		{
			XmlImageFactory f = new XmlImageFactory(q.getImage());
			xml.setImage(f.build(ejb.getCover()));
		}
		
		if(q.isSetFile() && ejb.getStorage()!=null)
		{
			xml.setFile(xfFile.build(ejb.getStorage()));
		}
		
		return xml;
	}
	
	public static de.kisner.otrcast.model.xml.video.tv.Movie build(String title, int year)
	{
		de.kisner.otrcast.model.xml.video.tv.Movie xml = new de.kisner.otrcast.model.xml.video.tv.Movie();
		xml.setName(title);
		xml.setYear(year);
		return xml;
		
	}
}
