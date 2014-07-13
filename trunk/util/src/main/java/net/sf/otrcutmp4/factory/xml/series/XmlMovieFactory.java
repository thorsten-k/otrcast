package net.sf.otrcutmp4.factory.xml.series;

import net.sf.otrcutmp4.factory.xml.mc.XmlImageFactory;
import net.sf.otrcutmp4.factory.xml.mc.XmlStorageFactory;
import net.sf.otrcutmp4.interfaces.model.Image;
import net.sf.otrcutmp4.interfaces.model.Movie;
import net.sf.otrcutmp4.interfaces.model.Storage;
import net.sf.otrcutmp4.model.xml.otr.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlMovieFactory<MOVIE extends Movie<COVER,STORAGE>,COVER extends Image,STORAGE extends Storage>
{	
	final static Logger logger = LoggerFactory.getLogger(XmlMovieFactory.class);
	
	private net.sf.otrcutmp4.model.xml.series.Movie q;
	
	public XmlMovieFactory(Query query){this(query.getMovie());}
	public XmlMovieFactory(net.sf.otrcutmp4.model.xml.series.Movie q){this.q=q;}
	
	public net.sf.otrcutmp4.model.xml.series.Movie build(Movie<COVER,STORAGE> ejb)
	{
		net.sf.otrcutmp4.model.xml.series.Movie xml = new net.sf.otrcutmp4.model.xml.series.Movie();
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
	
	public static net.sf.otrcutmp4.model.xml.series.Movie build(String title, int year)
	{
		net.sf.otrcutmp4.model.xml.series.Movie xml = new net.sf.otrcutmp4.model.xml.series.Movie();
		xml.setName(title);
		xml.setYear(year);
		return xml;
		
	}
}
