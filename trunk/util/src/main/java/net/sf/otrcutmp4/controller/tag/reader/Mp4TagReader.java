package net.sf.otrcutmp4.controller.tag.reader;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import net.sf.otrcutmp4.controller.tag.Mp4BoxManager;
import net.sf.otrcutmp4.model.xml.series.Video;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.MetaBox;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.UserDataBox;
import com.coremedia.iso.boxes.apple.AppleItemListBox;
import com.coremedia.iso.boxes.apple.AppleMediaTypeBox;
import com.coremedia.iso.boxes.apple.AppleShowBox;

public class Mp4TagReader
{
	final static Logger logger = LoggerFactory.getLogger(Mp4TagReader.class);

	private static enum Type {UNKNOWN,MOVIE,SERIES}
	
	private SeriesTagReader trSeries;
	private MovieTagReader trMovie;
	
	public Mp4TagReader(boolean withCover)
	{
		trSeries = new SeriesTagReader(withCover);
		trMovie = new MovieTagReader(withCover);
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
			case SERIES:	video.setEpisode(trSeries.readEpisode(apple));break;
			case MOVIE:		video.setMovie(trMovie.readMovie(apple));break;
			default: logger.warn("UNKNOWN handling");
		}
		
		isoFile.close();
		fcr.close();
		rafR.close();
		return video;
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
}