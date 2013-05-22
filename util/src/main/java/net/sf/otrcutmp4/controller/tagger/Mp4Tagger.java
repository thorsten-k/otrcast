package net.sf.otrcutmp4.controller.tagger;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.model.xml.series.Episode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.MetaBox;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.UserDataBox;
import com.coremedia.iso.boxes.apple.AppleItemListBox;
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
		
		//Write or update the episodes name
		AppleTrackTitleBox titleBox = null;
		if (!apple.getBoxes(AppleTrackTitleBox.class).isEmpty())
		{
			titleBox = (AppleTrackTitleBox) apple.getBoxes(AppleTrackTitleBox.class).get(0);
		}
		else
		{
			titleBox = new AppleTrackTitleBox();
		}
		titleBox.setValue(episode.getName());
		apple.addBox(titleBox);
		
		//Write or update the episodes number
		AppleTvEpisodeBox episodeBox = null;
		if (!apple.getBoxes(AppleTvEpisodeBox.class).isEmpty())
		{
			episodeBox = (AppleTvEpisodeBox) apple.getBoxes(AppleTvEpisodeBox.class).get(0);
		}
		else
		{
			episodeBox = new AppleTvEpisodeBox();
		}
		episodeBox.setValue(episode.getNr() +"");
		apple.addBox(episodeBox);
		
		//Write or update the episodes number
		AppleTvSeasonBox seasonBox = null;
		if (!apple.getBoxes(AppleTvSeasonBox.class).isEmpty())
		{
			seasonBox = (AppleTvSeasonBox) apple.getBoxes(AppleTvSeasonBox.class).get(0);
		}
		else
		{
			seasonBox = new AppleTvSeasonBox();
		}
		seasonBox.setValue(episode.getSeason() +"");
		apple.addBox(seasonBox);
		
		//TODO Add more information like show title
		
		isoFile.getBox(fcw);
		fcw.close();
		fcr.close();
	}
}