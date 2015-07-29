package de.kisner.otrcast.web.omdbapi;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.factory.xml.db.XmlDbFactory;
import de.kisner.otrcast.factory.xml.series.XmlMovieFactory;
import de.kisner.otrcast.interfaces.web.WebMovieFinder;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.series.Movie;
import de.kisner.otrcast.model.xml.series.Movies;

public class OmdbMovieSearch extends AbstractOmdbQuery implements WebMovieFinder
{
	final static Logger logger = LoggerFactory.getLogger(OmdbMovieSearch.class);
	
	public OmdbMovieSearch()
	{
        super();
	}
	
	@Override
	public Movies find(VideoFile videoFile) throws UtilsProcessingException
	{
		return find(videoFile.getOtrId().getTv().getName());
	}
	
	@Override
	public Movies find(String title) throws UtilsProcessingException
	{
		Movies movies = new Movies();
		try
		{
			Document doc = this.fetch(buildQuery(title));
//			JDomUtil.debug(doc);
			movies.getMovie().addAll(parseResponse(doc));
		}
		catch (MalformedURLException e) {e.printStackTrace();}
		catch (URISyntaxException e) {e.printStackTrace();}
		return  movies;
	}
	
	public List<Movie> parseResponse(Document doc) throws UtilsProcessingException
	{
		List<Movie> list = new ArrayList<Movie>();
		
		List<Element> lMovie = doc.getRootElement().getChildren("Movie");
        for(Element e : lMovie)
        {
        	if(e.getAttributeValue("Type").equals("movie"))
        	{
        		list.add(parse(e));
        	}
        }
		
		return list;
	}
	
	private Movie parse(Element e)
	{
		String title = e.getAttributeValue("Title");
		int year = new Integer(e.getAttributeValue("Year"));
		
		Movie movie = XmlMovieFactory.build(title, year);
		movie.setDb(XmlDbFactory.build("imdb", e.getAttributeValue("imdbID")));
		
		return movie;
	}
	
	
	private URL buildQuery(String title) throws URISyntaxException, MalformedURLException
	{
		// See http://www.myapifilms.com
		StringBuffer sb = new StringBuffer();
		sb.append("s=").append(title);
		sb.append("&r=xml");
		URI uri = new URI(httpProtocol,httpHost,httpContext,sb.toString(),null);
		return uri.toURL();
	}
}