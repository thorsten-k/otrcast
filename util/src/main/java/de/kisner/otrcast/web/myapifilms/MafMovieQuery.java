package de.kisner.otrcast.web.myapifilms;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class MafMovieQuery extends AbstractMafQuery implements WebMovieFinder
{
	final static Logger logger = LoggerFactory.getLogger(MafMovieQuery.class);

	private Pattern pYear,pYearDate;
	
	
	public MafMovieQuery()
	{
        super();
        
        pYear = Pattern.compile("(\\d{4})");
        pYearDate = Pattern.compile("(\\d+)+ (\\w+)\\. (\\d{4})");
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
			movies.getMovie().addAll(parseResponse(doc));
		}
		catch (MalformedURLException e) {e.printStackTrace();}
		catch (URISyntaxException e) {e.printStackTrace();}
		return  movies;
	}
	
	public List<Movie> parseResponse(Document doc) throws UtilsProcessingException
	{
		super.checkForError(doc);
		
		List<Movie> list = new ArrayList<Movie>();
		
//		JDomUtil.debug(doc);
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
		int year = year(e.getChildText("year"));
		return XmlMovieFactory.build(title, year);
	}
	
	public int year(String year)
	{
		Matcher mYear = pYear.matcher(year);
		Matcher mDate = pYearDate.matcher(year);
		
		if(mYear.matches())
		{
			return new Integer(year);
		}
		else if(mDate.matches())
		{
			return new Integer(mDate.group(3));
		}
		else
		{
			logger.warn("Unknown pattern for year: "+year);
			return 0;
		}
	}
	
	private URL buildQuery(String title) throws URISyntaxException, MalformedURLException
	{
		// See http://www.myapifilms.com
		StringBuffer sb = new StringBuffer();
		sb.append("title=").append(title);
		sb.append("&format=XML");
		sb.append("&aka=0");
		sb.append("&business=0");
		sb.append("&seasons=0");
		sb.append("&technical=0");
		sb.append("&filter=N");
		sb.append("&exactFilter=0");
		sb.append("&limit=10");
		sb.append("&lang=de-de");
		sb.append("&actors=N");
		sb.append("&biography=0");
		sb.append("&trailer=0");
		sb.append("&uniqueName=0");
		sb.append("&filmography=0&");
		sb.append("bornDied=0");
		sb.append("&starSign=0");
		sb.append("&actorActress=0");
		sb.append("&actorTrivia=0");
		sb.append("&movieTrivia=0");
		URI uri = new URI(httpProtocol,httpHost,httpContext,sb.toString(),null);
		return uri.toURL();
	}
}