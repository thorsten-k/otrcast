package de.kisner.otrcast.web.tvdb;

import java.io.IOException;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uwetrottmann.thetvdb.TheTvdb;
import com.uwetrottmann.thetvdb.entities.Episode;
import com.uwetrottmann.thetvdb.entities.EpisodesResponse;
import com.uwetrottmann.thetvdb.entities.EpisodesSummary;
import com.uwetrottmann.thetvdb.entities.EpisodesSummaryResponse;
import com.uwetrottmann.thetvdb.entities.Series;
import com.uwetrottmann.thetvdb.entities.SeriesResponse;

import de.kisner.otrcast.model.xml.container.Otr;
import de.kisner.otrcast.test.AbstractOtrcastTest;
import de.kisner.otrcast.test.OtrCastUtilTestBootstrap;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.exlp.util.xml.JaxbUtil;
import retrofit2.Response;

public class CliTvDbApi extends AbstractOtrcastTest
{
    final static Logger logger = LoggerFactory.getLogger(CliTvDbApi.class);

    private TheTvdb theTvdb;
    private final int seriesId = 83462;
    
    private CliTvDbApi( Configuration config)
    {
        theTvdb = new TheTvdb(config.getString("tvDbApiKey"));
    }
    
    public void episodeSummary() throws IOException
    {
    		Response<EpisodesSummaryResponse> r2 = theTvdb.series().episodesSummary(83462).execute();
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
    
    public void others() throws IOException
    {
    		Response<SeriesResponse> response = theTvdb.series().series(83462, "de").execute();
        if (response.isSuccessful())
        {
            Series series = response.body().data;
           
            System.out.println(series.seriesName + " is awesome!");
        }
        
        Response<EpisodesResponse> r3 = theTvdb.series().episodesQuery(83462, null, 1, null, null, null, null, null, null, "de").execute();
        if (r3.isSuccessful())
        {
        		List<Episode> summary = r3.body().data;
        		for(Episode e : summary)
        		{
        			System.out.println(e.airedSeason+" "+e.airedEpisodeNumber+" "+e.episodeName);
        		}
            
        }
    }
    
    public void api() throws UtilsProcessingException
    {
    		TvDb2Query query = new TvDb2Query(theTvdb);
    		Otr series = query.series(seriesId);
    		JaxbUtil.info(series);
    }
    
    public static void main(String args[]) throws Exception
    {
    		Configuration config = OtrCastUtilTestBootstrap.init();
    		CliTvDbApi cli = new CliTvDbApi(config);
//    		cli.episodeSummary();
//        cli.episodes();
        
        cli.api();
    }
 }