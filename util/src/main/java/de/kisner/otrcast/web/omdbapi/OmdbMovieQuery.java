package de.kisner.otrcast.web.omdbapi;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.exlp.util.xml.JDomUtil;

import org.jdom2.Document;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.factory.xml.db.XmlDbFactory;
import de.kisner.otrcast.factory.xml.mc.XmlImageFactory;
import de.kisner.otrcast.factory.xml.series.XmlMovieFactory;
import de.kisner.otrcast.model.xml.series.Movie;
import de.kisner.otrcast.model.xml.series.Movies;

public class OmdbMovieQuery extends AbstractOmdbQuery
{
	final static Logger logger = LoggerFactory.getLogger(OmdbMovieQuery.class);
	
	public OmdbMovieQuery()
	{
        super();
	}
	
	public Movies details(Movies movies) throws UtilsProcessingException
	{
		Movies xml = new Movies();
		for(Movie movie : movies.getMovie())
		{
			xml.getMovie().add(find(movie.getDb().getId()));
		}
		return xml;
	}
	
	public Movie find(String id) throws UtilsProcessingException
	{
		try
		{
			Document doc = this.fetch(buildQuery(id));
//			JDomUtil.debug(doc);
			return parseResponse(doc);
		}
		catch (MalformedURLException e) {throw new UtilsProcessingException(e.getMessage());}
		catch (URISyntaxException e) {throw new UtilsProcessingException(e.getMessage());}
	}
	
	public Movie parseResponse(Document doc) throws UtilsProcessingException
	{
		Element e = doc.getRootElement().getChild("movie");
		
		String title = e.getAttributeValue("title");
		int year = new Integer(e.getAttributeValue("year"));
		
		Movie movie = XmlMovieFactory.build(title, year);
		movie.setDb(XmlDbFactory.build("imdb", e.getAttributeValue("imdbID")));
		
		String post = e.getAttributeValue("poster");
		if(!post.equals("N/A"))
		{
			movie.setImage(XmlImageFactory.build(e.getAttributeValue("poster")));
		}
		
		return movie;
	}

	
	private URL buildQuery(String id) throws URISyntaxException, MalformedURLException
	{
		// See http://www.myapifilms.com
		StringBuffer sb = new StringBuffer();
		sb.append("i=").append(id);
		sb.append("&r=xml");
		URI uri = new URI(httpProtocol,httpHost,httpContext,sb.toString(),null);
		return uri.toURL();
	}
}