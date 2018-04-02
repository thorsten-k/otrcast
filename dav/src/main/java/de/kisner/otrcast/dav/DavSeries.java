package de.kisner.otrcast.dav;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.app.OtrWebDavServer;
import de.kisner.otrcast.factory.xml.series.XmlSeasonFactory;
import de.kisner.otrcast.model.xml.series.Episode;
import de.kisner.otrcast.model.xml.series.Season;
import de.kisner.otrcast.model.xml.series.Series;
import io.milton.annotations.Name;

public class DavSeries
{
	final static Logger logger = LoggerFactory.getLogger(OtrWebDavServer.class);
	
	private final Series series;
	private final List<DavEpisode> episodes; public List<DavEpisode> getEpisodes() {return episodes;}

	public DavSeries(Series series)
    {
        this.series=series;
        episodes = new ArrayList<DavEpisode>();
        for(Season season : series.getSeason())
        {
        	for(Episode episode : season.getEpisode())
        	{
        		episode.setSeason(XmlSeasonFactory.build(season.getNr()));
        		episodes.add(new DavEpisode(episode));
        	}
        }
        
        
    }

	@Name public String getName() {return series.getName();}	
}
