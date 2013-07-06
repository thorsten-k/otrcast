package net.sf.otrcutmp4.controller.tag;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import net.sf.otrcutmp4.controller.tagger.Mp4BoxManager;
import net.sf.otrcutmp4.model.xml.mc.Cover;
import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.model.xml.series.Movie;
import net.sf.otrcutmp4.model.xml.series.Season;
import net.sf.otrcutmp4.model.xml.series.Series;
import net.sf.otrcutmp4.model.xml.series.Video;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.MetaBox;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.UserDataBox;
import com.coremedia.iso.boxes.apple.AppleCoverBox;
import com.coremedia.iso.boxes.apple.AppleDataBox;
import com.coremedia.iso.boxes.apple.AppleItemListBox;
import com.coremedia.iso.boxes.apple.AppleMediaTypeBox;
import com.coremedia.iso.boxes.apple.AppleShowBox;
import com.coremedia.iso.boxes.apple.AppleTrackTitleBox;
import com.coremedia.iso.boxes.apple.AppleTvSeasonBox;

public class Mp4TagReader
{
	final static Logger logger = LoggerFactory.getLogger(Mp4TagReader.class);

	private static enum Type {UNKNOWN,MOVIE,SERIES}
	
	private boolean withCover;
	
	public Mp4TagReader(boolean withCover)
	{
		this.withCover=withCover;
	}
	
	public Video read(File fSource) throws IOException
	{
		RandomAccessFile rafR = new RandomAccessFile(fSource, "r");		
		FileChannel fcr = rafR.getChannel();
		IsoFile isoFile = new IsoFile(fcr);
		
		MovieBox moov = Mp4BoxManager.movieBox(isoFile);		
		UserDataBox udta = Mp4BoxManager.userDataBox(moov);
		MetaBox meta = Mp4BoxManager.metaBox(udta);
		AppleItemListBox apple = Mp4BoxManager.appleItemListBox(meta);
		
		Type type = getMediaType(apple);

		Video video = new Video();
		switch(type)
		{
			case SERIES:	video.setEpisode(readEpisode(apple));break;
			case MOVIE:		video.setMovie(readMovie(apple));break;
			default: logger.warn("UNKNOWN handling");
		}
		
		isoFile.close();
		fcr.close();
		rafR.close();
		return video;
	}
	
	public Episode readEpisode(AppleItemListBox apple)
	{
		Episode episode = new Episode();
		episode.setSeason(new Season());
		episode.getSeason().setSeries(new Series());
		
		if (!apple.getBoxes(AppleTvSeasonBox.class).isEmpty())
		{
			AppleTvSeasonBox box = apple.getBoxes(AppleTvSeasonBox.class).get(0);
			episode.getSeason().setNr(new Integer(box.getValue()));
		}
		if (!apple.getBoxes(AppleTvSeasonBox.class).isEmpty())
		{
			AppleTvSeasonBox box = apple.getBoxes(AppleTvSeasonBox.class).get(0);
			episode.setNr(new Integer(box.getValue()));
		}		
		if (!apple.getBoxes(AppleShowBox.class).isEmpty())
		{
			AppleShowBox box = apple.getBoxes(AppleShowBox.class).get(0);
			episode.getSeason().getSeries().setName(box.getValue());
		}
		if (!apple.getBoxes(AppleTrackTitleBox.class).isEmpty())
		{
			AppleTrackTitleBox box = apple.getBoxes(AppleTrackTitleBox.class).get(0);
			episode.setName(box.getValue());
		}
		if(withCover && !apple.getBoxes(AppleCoverBox.class).isEmpty())
		{
			try
			{
				episode.setCover(getCover(apple.getBoxes(AppleCoverBox.class).get(0)));
			}
			catch (NoSuchFieldException e) {}
		}
		
		return episode;
	}
	
	public Movie readMovie(AppleItemListBox apple)
	{
		Movie movie = new Movie();
		if (!apple.getBoxes(AppleTrackTitleBox.class).isEmpty())
		{
			AppleTrackTitleBox box = apple.getBoxes(AppleTrackTitleBox.class).get(0);
			movie.setName(box.getValue());
		}
		return movie;
	}
	
	public Type getMediaType(AppleItemListBox apple)
	{
		if (apple.getBoxes(AppleMediaTypeBox.class).isEmpty())
		{
			return guessType(apple);
		}
		else
		{
			AppleMediaTypeBox box = apple.getBoxes(AppleMediaTypeBox.class).get(0);
			if(box.getValue().equals("10")){return Type.SERIES;}
			else
			{
				logger.warn("Unknown Mediatype "+box.getValue());
				logger.debug("\t"+box.getClass().getSimpleName()+" "+box.getType()+" "+box.getValue());
				
				return Type.UNKNOWN;
			}		
		}
	}
	
	public Type guessType(AppleItemListBox apple)
	{
		if (!apple.getBoxes(AppleShowBox.class).isEmpty())
		{
			AppleShowBox box = apple.getBoxes(AppleShowBox.class).get(0);
			if(box.getValue().length()>0){return Type.SERIES;}
		}
		return Type.MOVIE;
	}
	
	private Cover getCover(AppleCoverBox box) throws NoSuchFieldException
	{
		if(box.getBoxes(AppleDataBox.class).size()==0){throw new NoSuchFieldException();}	
		AppleDataBox adb = box.getBoxes(AppleDataBox.class).get(0);
		
		Cover cover = new Cover();
		cover.setData(adb.getData());
		
		//see source of AppleCoverBox.java
		if(adb.getFlags()==0xe){cover.setType("png");}
		else if(adb.getFlags()==0xd){cover.setType("jpg");}
		else {logger.warn("Unknown flag for cover "+adb.getFlags());}
		
		return cover;
	}
}