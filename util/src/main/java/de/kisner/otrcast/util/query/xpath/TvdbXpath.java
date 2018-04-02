package de.kisner.otrcast.util.query.xpath;

import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.tvdb.Banner;
import de.kisner.otrcast.model.xml.tvdb.Banners;
import de.kisner.otrcast.model.xml.video.tv.Season;
import de.kisner.otrcast.model.xml.video.tv.Series;

public class TvdbXpath
{
	final static Logger logger = LoggerFactory.getLogger(TvdbXpath.class);
	
	public static List<Banner> getBannersForSeason(Banners banners, long nr)
	{	
		Series series = new Series();
		series.getSeason().addAll(banners.getSeason());
	
		List<Banner> result = new ArrayList<Banner>();
		
		try
		{
			Season season = SeriesXpath.getSeason(series, nr);

			if(season.isSetBanners())
			{
				result.addAll(season.getBanners().getBanner());
			}
		}
		catch (ExlpXpathNotFoundException e) {}
		catch (ExlpXpathNotUniqueException e) {}
		
		return result;
	}
}