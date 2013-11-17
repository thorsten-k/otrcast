package net.sf.otrcutmp4.controller.tag.reader;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.MetaBox;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.UserDataBox;
import com.coremedia.iso.boxes.apple.AppleItemListBox;
import com.coremedia.iso.boxes.apple.AppleMediaTypeBox;
import com.coremedia.iso.boxes.apple.AppleShowBox;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.otrcutmp4.controller.tag.Mp4BoxManager;
import net.sf.otrcutmp4.model.xml.series.Video;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class Mp4TagReader
{
	final static Logger logger = LoggerFactory.getLogger(Mp4TagReader.class);


	
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

        Mp4BoxManager.Type type;
        try{type = getMediaType(apple);}
        catch (UtilsNotFoundException e) {type=guessType(apple);}

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

    public Mp4BoxManager.Type readMediaType(File file) throws IOException,UtilsNotFoundException
    {
        RandomAccessFile rafR = new RandomAccessFile(file, "r");
        FileChannel fcr = rafR.getChannel();
        IsoFile isoFile = new IsoFile(fcr);

        MovieBox moov = Mp4BoxManager.movieBox(isoFile);
        UserDataBox udta = Mp4BoxManager.userDataBox(moov);
        MetaBox meta = Mp4BoxManager.metaBox(udta);
        AppleItemListBox apple = Mp4BoxManager.appleItemListBox(meta);

        Mp4BoxManager.Type type;
        try{type = getMediaType(apple);}
        catch (UtilsNotFoundException e) {throw e;}
        finally
        {
            isoFile.close();
            fcr.close();
            rafR.close();
        }

        return type;
    }
	public Mp4BoxManager.Type getMediaType(AppleItemListBox apple) throws UtilsNotFoundException
    {
		if (apple.getBoxes(AppleMediaTypeBox.class).isEmpty())
		{
            logger.trace(AppleMediaTypeBox.class.getSimpleName()+" not set");
            throw new UtilsNotFoundException(AppleMediaTypeBox.class.getSimpleName()+" not set");
		}
		else
		{
			AppleMediaTypeBox box = apple.getBoxes(AppleMediaTypeBox.class).get(0);
			if(box.getValue().equals(Mp4BoxManager.typeSeries)){return Mp4BoxManager.Type.SERIES;}
			else
			{
				logger.warn("Unknown Mediatype "+box.getValue());
				logger.debug("\t"+box.getClass().getSimpleName()+" "+box.getType()+" "+box.getValue());
				
				return Mp4BoxManager.Type.UNKNOWN;
			}		
		}
	}

    public Mp4BoxManager.Type guessType(File file) throws IOException
    {
        RandomAccessFile rafR = new RandomAccessFile(file, "r");
        FileChannel fcr = rafR.getChannel();
        IsoFile isoFile = new IsoFile(fcr);

        MovieBox moov = Mp4BoxManager.movieBox(isoFile);
        UserDataBox udta = Mp4BoxManager.userDataBox(moov);
        MetaBox meta = Mp4BoxManager.metaBox(udta);
        AppleItemListBox apple = Mp4BoxManager.appleItemListBox(meta);

        Mp4BoxManager.Type type;
        type = guessType(apple);

        isoFile.close();
        fcr.close();
        rafR.close();

        return type;
    }
	public Mp4BoxManager.Type guessType(AppleItemListBox apple)
	{
		if (!apple.getBoxes(AppleShowBox.class).isEmpty())
		{
			AppleShowBox box = apple.getBoxes(AppleShowBox.class).get(0);
			if(box.getValue().length()>0){return Mp4BoxManager.Type.SERIES;}
		}
		return Mp4BoxManager.Type.MOVIE;
	}
}