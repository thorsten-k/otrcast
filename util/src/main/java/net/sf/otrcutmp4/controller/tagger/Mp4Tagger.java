package net.sf.otrcutmp4.controller.tagger;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.model.xml.series.Season;
import net.sf.otrcutmp4.model.xml.series.Series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.MetaBox;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.UserDataBox;
import com.coremedia.iso.boxes.apple.AppleItemListBox;
import com.coremedia.iso.boxes.apple.AppleShowBox;
import com.coremedia.iso.boxes.apple.AppleTrackTitleBox;
import com.coremedia.iso.boxes.apple.AppleTvEpisodeBox;
import com.coremedia.iso.boxes.apple.AppleTvSeasonBox;

public class Mp4Tagger
{
	final static Logger logger = LoggerFactory.getLogger(Mp4Tagger.class);
		
	public Mp4Tagger()
	{

	}
	
	@SuppressWarnings("resource")
	public void tagEpisode(String filename, Episode episode) throws IOException
	{
		JaxbUtil.info(episode);
		FileChannel fcr = new RandomAccessFile(filename, "r").getChannel();
		FileChannel fcw = new RandomAccessFile(filename +".new", "rw").getChannel();
		
		//TODO Write a temporary file and in the end, remove original and rename the temp file to replace the original
		
		IsoFile isoFile = new IsoFile(fcr);
		
		//Get the MovieBox
		MovieBox moov = isoFile.getBoxes(MovieBox.class).get(0);
		
		//Get the Meta Data from the UserDataBox and get the Apple meta-data
		UserDataBox udta = moov.getBoxes(UserDataBox.class).get(0);
		MetaBox meta = udta.getBoxes(MetaBox.class).get(0);
		AppleItemListBox apple = meta.getBoxes(AppleItemListBox.class).get(0);
		
		//TODO Write a method to make this code DRY-compatible!
		
		writeEpisodeName(apple, episode);
		writeEpisodeNr(apple, episode);
		writeSeason(apple, episode.getSeason());
		writeSeries(apple, episode.getSeason().getSeries());
		
		//TODO Add more information like show title
		
		isoFile.getBox(fcw);
		fcw.close();
		fcr.close();
	}
	
	private void writeEpisodeName(AppleItemListBox apple, Episode episode)
	{
		AppleTrackTitleBox titleBox = null;
		if (apple.getBoxes(AppleTrackTitleBox.class).isEmpty())
		{
			titleBox = new AppleTrackTitleBox();
		}
		else
		{
			titleBox = (AppleTrackTitleBox) apple.getBoxes(AppleTrackTitleBox.class).get(0);
		}
		titleBox.setValue(episode.getName());
		apple.addBox(titleBox);
	}
	
	private void writeEpisodeNr(AppleItemListBox apple, Episode episode)
	{
		AppleTvEpisodeBox episodeBox = null;
		if (apple.getBoxes(AppleTvEpisodeBox.class).isEmpty())
		{
			episodeBox = new AppleTvEpisodeBox();
		}
		else
		{
			episodeBox = (AppleTvEpisodeBox) apple.getBoxes(AppleTvEpisodeBox.class).get(0);
			
		}
		episodeBox.setValue(episode.getNr() +"");
		apple.addBox(episodeBox);
	}
	
	private void writeSeason(AppleItemListBox apple, Season season)
	{
		AppleTvSeasonBox seasonBox = null;
		if(apple.getBoxes(AppleTvSeasonBox.class).isEmpty())
		{
			seasonBox = new AppleTvSeasonBox();
		}
		else
		{
			seasonBox = (AppleTvSeasonBox) apple.getBoxes(AppleTvSeasonBox.class).get(0);
		}
		seasonBox.setValue(season.getNr()+"");
		apple.addBox(seasonBox);
	}
	
	private void writeSeries(AppleItemListBox apple, Series series)
	{
		AppleShowBox box = null;
		if(apple.getBoxes(AppleTvSeasonBox.class).isEmpty())
		{
			box = new AppleShowBox();
		}
		else
		{
			box = (AppleShowBox) apple.getBoxes(AppleShowBox.class).get(0);
		}
		box.setValue(series.getName());
		apple.addBox(box);
	}
}