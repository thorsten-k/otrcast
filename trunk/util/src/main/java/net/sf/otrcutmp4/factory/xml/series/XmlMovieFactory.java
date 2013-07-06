package net.sf.otrcutmp4.factory.xml.series;

import net.sf.otrcutmp4.factory.xml.mc.XmlCoverFactory;
import net.sf.otrcutmp4.interfaces.model.Cover;
import net.sf.otrcutmp4.interfaces.model.Movie;
import net.sf.otrcutmp4.model.xml.otr.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlMovieFactory<MOVIE extends Movie<COVER>,COVER extends Cover>
{	
	final static Logger logger = LoggerFactory.getLogger(XmlMovieFactory.class);
	
	private net.sf.otrcutmp4.model.xml.series.Movie q;
	
	public XmlMovieFactory(Query query){this(query.getMovie());}
	public XmlMovieFactory(net.sf.otrcutmp4.model.xml.series.Movie q){this.q=q;}
	
	public net.sf.otrcutmp4.model.xml.series.Movie build(Movie<COVER> ejb)
	{
		net.sf.otrcutmp4.model.xml.series.Movie xml = new net.sf.otrcutmp4.model.xml.series.Movie();
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetName()){xml.setName(ejb.getName());}
		if(q.isSetYear()){xml.setYear(ejb.getYear());}
		
		if(q.isSetCover() && ejb.getCover()!=null)
		{
			XmlCoverFactory f = new XmlCoverFactory(q.getCover());
			xml.setCover(f.build(ejb.getCover()));
		}
		
		return xml;
	}
}
