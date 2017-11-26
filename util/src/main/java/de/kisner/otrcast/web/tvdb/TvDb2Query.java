package de.kisner.otrcast.web.tvdb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uwetrottmann.thetvdb.TheTvdb;
import com.uwetrottmann.thetvdb.entities.Episode;
import com.uwetrottmann.thetvdb.entities.EpisodesResponse;
import com.uwetrottmann.thetvdb.entities.EpisodesSummary;
import com.uwetrottmann.thetvdb.entities.EpisodesSummaryResponse;
import com.uwetrottmann.thetvdb.entities.SeriesResponse;

import de.kisner.otrcast.factory.xml.otr.XmlOtrFactory;
import de.kisner.otrcast.factory.xml.series.XmlEpisodeFactory;
import de.kisner.otrcast.factory.xml.series.XmlSeasonFactory;
import de.kisner.otrcast.factory.xml.series.XmlSeriesFactory;
import de.kisner.otrcast.model.xml.container.Otr;
import de.kisner.otrcast.model.xml.series.Season;
import de.kisner.otrcast.model.xml.series.Series;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import retrofit2.Response;

public class TvDb2Query
{
	final static Logger logger = LoggerFactory.getLogger(TvDb2Query.class);

	private final TheTvdb theTvdb;
	
	public TvDb2Query(String apiKey)
	{
        this(new TheTvdb(apiKey));
	}
	
	public TvDb2Query(TheTvdb theTvdb)
	{
		this.theTvdb=theTvdb;
	}
	
	public Otr series(int id) throws UtilsProcessingException
	{
        return XmlOtrFactory.build(build(id));
	}
	
	private Series build(int id) throws UtilsProcessingException
	{
		Series xml;
		try
		{
			Response<SeriesResponse> response = theTvdb.series().series(83462, "de").execute();
			if (response.isSuccessful())
	        {
	        		xml = XmlSeriesFactory.build(response.body().data);
	        		xml.getSeason().addAll(seasons(id));
	        }
			else {throw new UtilsProcessingException("Failed: "+response.errorBody().toString());}	
		}
		catch (IOException e) {throw new UtilsProcessingException(e.getMessage());}
       
		return xml;
	}
	
	private List<Season> seasons(int id) throws UtilsProcessingException
	{
		List<Episode> episodes = new ArrayList<Episode>();
		try
		{
			Integer page = 1;
			while(page!=null)
			{
				Response<EpisodesResponse> response = theTvdb.series().episodes(id, page, "de").execute();
		        if (response.isSuccessful())
		        {
		        		page = response.body().links.next;
		        		episodes.addAll(response.body().data);          
		        }
			}
		}	
	    catch (IOException e) {e.printStackTrace();}
		
		
		List<Season> list = new ArrayList<Season>();
		List<Episode> episodesInSeason = new ArrayList<Episode>();
		try
		{
			Response<EpisodesSummaryResponse> response = theTvdb.series().episodesSummary(83462).execute();
	        if (response.isSuccessful())
	        {
	        		EpisodesSummary summary = response.body().data;
	        		List<Integer> seasonsNumbers = summary.airedSeasons;
	        		Collections.sort(seasonsNumbers);
	        		for(Integer seasonNumber : seasonsNumbers)
	        		{
	        			episodesInSeason.clear();
	        			for(Episode e : episodes) {if(e.airedSeason.intValue()==seasonNumber.intValue()) {episodesInSeason.add(e);}}
	        			list.add(season(seasonNumber,episodesInSeason));
	        		}
	        }
		}	
	    catch (IOException e) {e.printStackTrace();}
		return list;
	}
	
	private Season season(int nr, List<Episode> episodes) throws UtilsProcessingException
	{
		Season season = XmlSeasonFactory.build(nr);
		for(Episode e : episodes)
		{
			if(season.isSetId() && season.getId()!=e.airedSeasonID) {throw new UtilsProcessingException("Not all season Ids matching!!");}
			else {season.setId(e.airedSeasonID);}
			season.getEpisode().add(XmlEpisodeFactory.build(e));
		}
        
		return season;
	}
}