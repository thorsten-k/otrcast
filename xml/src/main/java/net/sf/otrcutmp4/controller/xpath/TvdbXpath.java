package net.sf.otrcutmp4.controller.xpath;

import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.otrcutmp4.model.xml.series.Season;
import net.sf.otrcutmp4.model.xml.series.Series;
import net.sf.otrcutmp4.model.xml.tvdb.Banner;
import net.sf.otrcutmp4.model.xml.tvdb.Banners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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