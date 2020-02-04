package de.kisner.otrcast.web.imdb;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.util.xml.JDomUtil;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jeesl.exception.processing.UtilsProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.factory.xml.series.XmlMovieFactory;
import de.kisner.otrcast.interfaces.web.WebMovieFinder;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.video.tv.Movie;
import de.kisner.otrcast.model.xml.video.tv.Movies;

public class ImdbMovieQuery extends AbstractImdbQuery implements WebMovieFinder
{
	final static Logger logger = LoggerFactory.getLogger(ImdbMovieQuery.class);
	
	public ImdbMovieQuery()
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
			JDomUtil.debug(doc);
			System.exit(-1);
			movies.getMovie().addAll(parseResponse(doc));
		}
		catch (MalformedURLException e) {e.printStackTrace();}
		catch (URISyntaxException e) {e.printStackTrace();}
		return  movies;
	}
	
	public List<Movie> parseResponse(Document doc) throws UtilsProcessingException
	{
		List<Movie> list = new ArrayList<Movie>();
		
		
		List<Element> lMovie = doc.getRootElement().getChildren("movie");
		
        for(Element e : lMovie)
        {
        	list.add(parse(e));
        }
		
		return list;
	}
	
	private Movie parse(Element e)
	{
		String title = e.getChildText("title");
		return XmlMovieFactory.build(title, 2000);
	}
	
	
	private URL buildQuery(String title) throws URISyntaxException, MalformedURLException
	{
		// See http://www.myapifilms.com
		StringBuffer sb = new StringBuffer();
		sb.append("xml=1");
		sb.append("&nr=1");
		sb.append("&tt=on");
		sb.append("&q=").append(title);
		URI uri = new URI(httpProtocol,httpHost,httpContext,sb.toString(),null);
		return uri.toURL();
	}
}