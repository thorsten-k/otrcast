package de.kisner.otrcast.factory.io;

import java.io.File;

import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.model.Episode;
import de.kisner.otrcast.interfaces.model.Image;
import de.kisner.otrcast.interfaces.model.Season;
import de.kisner.otrcast.interfaces.model.Series;
import de.kisner.otrcast.interfaces.model.Storage;

public class IoFileFactory
{	
	final static Logger logger = LoggerFactory.getLogger(IoFileFactory.class);
	
	private File fRoot;

	public IoFileFactory(File fRoot)
	{
		this.fRoot=fRoot;
	}
	 
	public <SERIES extends Series<SERIES,SEASON,EPISODE,COVER,STORAGE>,
				SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,
				EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER,STORAGE>,
				COVER extends Image,STORAGE extends Storage>
		File build(EPISODE episode)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(episode.getSeason().getSeries().getName()).append(" - ").append(episode.getSeason().getSeries().getId());
		sb.append(SystemUtils.FILE_SEPARATOR).append(episode.getSeason().getNr());
		sb.append(SystemUtils.FILE_SEPARATOR).append(episode.getNr()).append(" ").append(episode.getName());
		sb.append(".mp4");
		return new File(fRoot,sb.toString());
	}
	
	public File build(de.kisner.otrcast.model.xml.series.Episode episode)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(episode.getSeason().getSeries().getName()).append(" - ").append(episode.getSeason().getSeries().getId());
		sb.append(SystemUtils.FILE_SEPARATOR).append(episode.getSeason().getNr());
		sb.append(SystemUtils.FILE_SEPARATOR).append(episode.getNr()).append(" ").append(episode.getName());
		sb.append(".mp4");
		return new File(fRoot,sb.toString());
	}
	
	public File getfRoot() {return fRoot;}
}