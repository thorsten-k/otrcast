package net.sf.otrcutmp4.factory.io;

import java.io.File;

import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.otrcutmp4.interfaces.model.Episode;
import net.sf.otrcutmp4.interfaces.model.Image;
import net.sf.otrcutmp4.interfaces.model.Season;
import net.sf.otrcutmp4.interfaces.model.Series;
import net.sf.otrcutmp4.interfaces.model.Storage;

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
		sb.append(SystemUtils.FILE_SEPARATOR).append(episode.getNr()).append(" ").append(episode.getName()).append(".mp4");
		return new File(fRoot,sb.toString());
	}
	
	public File build(net.sf.otrcutmp4.model.xml.series.Episode episode)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(episode.getSeason().getSeries().getName()).append(" - ").append(episode.getSeason().getSeries().getId());
		sb.append(SystemUtils.FILE_SEPARATOR).append(episode.getSeason().getNr());
		sb.append(SystemUtils.FILE_SEPARATOR).append(episode.getNr()).append(" ").append(episode.getName());
		return new File(fRoot,sb.toString());
	}
}