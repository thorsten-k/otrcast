package de.kisner.otrcast.web;

import java.io.IOException;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.exlp.util.jx.JaxbUtil;
import org.jeesl.exception.processing.UtilsProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uwetrottmann.thetvdb.TheTvdb;
import com.uwetrottmann.thetvdb.entities.Episode;
import com.uwetrottmann.thetvdb.entities.EpisodesResponse;
import com.uwetrottmann.thetvdb.entities.EpisodesSummary;
import com.uwetrottmann.thetvdb.entities.EpisodesSummaryResponse;
import com.uwetrottmann.thetvdb.entities.SeriesImageQueryResult;
import com.uwetrottmann.thetvdb.entities.SeriesImageQueryResultResponse;
import com.uwetrottmann.thetvdb.entities.SeriesImagesQueryParam;
import com.uwetrottmann.thetvdb.entities.SeriesImagesQueryParamResponse;

import de.kisner.otrcast.controller.OtrCastBootstrap;
import de.kisner.otrcast.model.xml.container.Otr;
import de.kisner.otrcast.model.xml.tvdb.Banners;
import de.kisner.otrcast.web.tvdb.TvDbJsonQuery;
import retrofit2.Response;

public class CliTvDbApi
{
    final static Logger logger = LoggerFactory.getLogger(CliTvDbApi.class);

    private TheTvdb theTvdb;
    private final int seriesId = 77398;
    
    private CliTvDbApi( Configuration config)
    {
        theTvdb = new TheTvdb(config.getString("tvDbApiKey"));
    }
    
    public void episodeSummary() throws IOException
    {
    	Response<EpisodesSummaryResponse> r2 = theTvdb.series().episodesSummary(seriesId).execute();
        if (r2.isSuccessful())
        {
    		EpisodesSummary summary = r2.body().data;
    		System.out.println(summary.airedEpisodes);
    		for(Integer i : summary.airedSeasons)
    		{
    			logger.info(i.toString());
    		}
        }
    }
    
    public void episodes() throws IOException
    {
    	Response<EpisodesResponse> r2 = theTvdb.series().episodes(seriesId, 1, "de").execute();
        if (r2.isSuccessful())
        {
    		if(r2.body().links.next!=null)
    		{
    			logger.info("Next "+r2.body().links.next);
    		}
    		for(Episode e : r2.body().data)
    		{
    			System.out.println(e.airedSeason+"."+e.airedEpisodeNumber+" "+e.episodeName);
    		}
        }
    }
    
    public void images() throws IOException
    {
    	Response<SeriesImagesQueryParamResponse> r1 = theTvdb.series().imagesQueryParams(seriesId).execute();
        if (r1.isSuccessful())
        {
    		List<SeriesImagesQueryParam> list = r1.body().data;
    		for(SeriesImagesQueryParam i : list)
    		{
    			System.out.println(i.keyType);
    			for(String sub : i.subKey)
    			{
    				logger.info("\t"+sub);
    			}
    		}
        }
        Response<SeriesImageQueryResultResponse> r2 = theTvdb.series().imagesQuery(seriesId, "season", null, "1", null).execute();
        if (r2.isSuccessful())
        {
    		List<SeriesImageQueryResult> list = r2.body().data;
    		logger.info("Images: "+list.size());
    		for(SeriesImageQueryResult i : list)
    		{
    			System.out.println(i.fileName+" "+i.thumbnail);
    		}
        }
        else
        {
        	logger.info(r2.message());
        }
    }
    
    public void api() throws UtilsProcessingException
    {
		TvDbJsonQuery query = new TvDbJsonQuery(theTvdb);
		Otr series = query.series(seriesId);
		JaxbUtil.info(series);
		
		Banners covers = query.seasonCovers(seriesId, 1);
		JaxbUtil.info(covers);
    }
    
    public static void main(String args[]) throws Exception
    {
		Configuration config = OtrCastBootstrap.init();
		CliTvDbApi cli = new CliTvDbApi(config);
//		cli.episodeSummary();
//        cli.episodes();
        
       cli.api();
//        cli.images();
    }
 }