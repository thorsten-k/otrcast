package de.kisner.otrcast.controller.tag.writer;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coremedia.iso.boxes.apple.AppleItemListBox;
import com.coremedia.iso.boxes.apple.AppleShowBox;
import com.coremedia.iso.boxes.apple.AppleTrackTitleBox;
import com.coremedia.iso.boxes.apple.AppleTvEpisodeBox;
import com.coremedia.iso.boxes.apple.AppleTvSeasonBox;

import de.kisner.otrcast.controller.tag.util.Mp4BoxManager;
import de.kisner.otrcast.interfaces.controller.CoverManager;
import de.kisner.otrcast.model.xml.series.Episode;
import de.kisner.otrcast.model.xml.series.Season;

public class SeriesTagWriter extends AbstractTagWriter
{
	final static Logger logger = LoggerFactory.getLogger(SeriesTagWriter.class);

	public SeriesTagWriter()
	{
		super(null);
	}
	public SeriesTagWriter(CoverManager coverManager)
	{
		super(coverManager);
	}
	
	public void tagEpisode(String srcFileName, Episode episode, String dstFileName) throws IOException
	{
		tagEpisode(new File(srcFileName), episode, new File(dstFileName));
	}

	public void tagEpisode(File srcFile, Episode episode, File dstFile) throws IOException
	{
		readMp4Box(srcFile);
		
		writeEpisodeName(apple, episode.getName());
		writeEpisodeNr(apple, new Long(episode.getNr()).intValue());
		writeSeasonNr(apple, new Long(episode.getSeason().getNr()).intValue());
		writeSeries(apple, episode.getSeason().getSeries().getName());
		writeMediaType(apple, "10");
		writeCover(apple, episode.getSeason());
					
		writeMp4(dstFile);
	}
	
	private void writeEpisodeName(AppleItemListBox apple, String name)
	{
		AppleTrackTitleBox titleBox = Mp4BoxManager.fcAppleTrackTitleBox(apple);
		titleBox.setValue(name);
		apple.addBox(titleBox);
	}
	
	private void writeEpisodeNr(AppleItemListBox apple, int episodeNr)
	{
		AppleTvEpisodeBox episodeBox = Mp4BoxManager.fcAppleTvEpisodeBox(apple);
		episodeBox.setValue(episodeNr+"");
		apple.addBox(episodeBox);
	}
		
	private void writeSeasonNr(AppleItemListBox apple, int seasonNr)
	{
		AppleTvSeasonBox seasonBox = Mp4BoxManager.fcAppleTvSeasonBox(apple);
		seasonBox.setValue(seasonNr+"");
		apple.addBox(seasonBox);
	}
		
	private void writeSeries(AppleItemListBox apple, String show)
	{
		AppleShowBox box = Mp4BoxManager.fcAppleShowBox(apple);
		box.setValue(show);
		apple.addBox(box);
	}
	
	private void writeCover(AppleItemListBox apple, Season season) throws IOException
	{
		boolean abortNoCoverManager = (coverManager==null);
		boolean abortNoSeriesKey = !season.getSeries().isSetKey();
		
		logger.trace("Abort because no coverManager?"+abortNoCoverManager);
		logger.trace("Abort because no series@key?"+abortNoSeriesKey);
		
		if(abortNoCoverManager || abortNoSeriesKey){return;}
		
		boolean coverAvailable = coverManager.isAvailable(season);
		logger.info("Cover available: "+coverAvailable);
		if(coverAvailable)
		{
			logger.debug("Writing Cover "+season.getSeries().getKey());
			writeCover(apple, coverManager.getFormat(), coverManager.getImageStream());
		}
	}
}