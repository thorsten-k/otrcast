package de.kisner.otrcast.dav;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.app.OtrWebDavServer;
import io.milton.annotations.Name;

public class DavSeries
{
	final static Logger logger = LoggerFactory.getLogger(OtrWebDavServer.class);
	
	private List<DavEpisode> episodes;
	
	public List<DavEpisode> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(List<DavEpisode> episodes) {
		this.episodes = episodes;
	}

	public DavSeries(String code)
    {
        this.code=code;
        episodes = new ArrayList<DavEpisode>();
        episodes.add(new DavEpisode("a"));
    }
	


	private String code;

	@Name
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
