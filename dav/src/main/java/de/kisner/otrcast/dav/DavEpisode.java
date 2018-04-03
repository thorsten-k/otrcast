package de.kisner.otrcast.dav;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.app.OtrWebDavServer;
import de.kisner.otrcast.model.xml.video.tv.Episode;
import io.milton.annotations.ContentLength;
import io.milton.annotations.Name;

public class DavEpisode
{
	final static Logger logger = LoggerFactory.getLogger(OtrWebDavServer.class);
	
	private final Episode episode;
	
	public DavEpisode(Episode episode)
    {
        this.episode=episode;
    }

	@Name
	public String getCode()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(episode.getSeason().getNr()).append("x").append(episode.getNr()).append(" ");
		sb.append(episode.getName());
		sb.append(".").append("mp4");
		return sb.toString();
	}
	
	@ContentLength
	public long read()
	{
		return toFile().length();
	}
	
	public File toFile()
	{

		logger.info("Creating File ");
		return new File(episode.getFile().getPath(),episode.getFile().getName());
	}
}