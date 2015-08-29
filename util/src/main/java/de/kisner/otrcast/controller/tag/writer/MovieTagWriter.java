package de.kisner.otrcast.controller.tag.writer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.MetaBox;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.UserDataBox;
import com.coremedia.iso.boxes.apple.AppleCoverBox;
import com.coremedia.iso.boxes.apple.AppleCustomGenreBox;
import com.coremedia.iso.boxes.apple.AppleItemListBox;
import com.coremedia.iso.boxes.apple.AppleMediaTypeBox;
import com.coremedia.iso.boxes.apple.AppleShowBox;
import com.coremedia.iso.boxes.apple.AppleTrackTitleBox;
import com.coremedia.iso.boxes.apple.AppleTvEpisodeBox;
import com.coremedia.iso.boxes.apple.AppleTvSeasonBox;

import de.kisner.otrcast.controller.tag.util.Mp4BoxManager;
import de.kisner.otrcast.controller.tag.util.Mp4MetadataBalancer;
import de.kisner.otrcast.model.xml.series.Episode;
import de.kisner.otrcast.model.xml.series.Movie;
import de.kisner.otrcast.model.xml.series.Season;
import de.kisner.otrcast.model.xml.series.Series;

public class MovieTagWriter extends AbstractTagWriter
{
	final static Logger logger = LoggerFactory.getLogger(MovieTagWriter.class);

	public MovieTagWriter()
	{
		super(null);
	}
	
	public void tagMovie(File srcFile, Movie movie, File dstFile) throws IOException
	{
		logger.info("srcFile: "+srcFile.getAbsolutePath());
		logger.info("dstFile: "+dstFile.getAbsolutePath());
		
		RandomAccessFile rafR = new RandomAccessFile(srcFile, "r");
		RandomAccessFile rafW = new RandomAccessFile(dstFile, "rw");
		
		FileChannel fcr = rafR.getChannel();
		FileChannel fcw = rafW.getChannel();
		
		IsoFile isoFile = new IsoFile(fcr);
		
		MovieBox moov = Mp4BoxManager.movieBox(isoFile);
		long sizeBefore = moov.getSize();
		
		UserDataBox udta = Mp4BoxManager.userDataBox(moov);
		MetaBox meta = Mp4BoxManager.metaBox(udta);
		AppleItemListBox apple = Mp4BoxManager.appleItemListBox(meta);

		
		writeMovieTitle(apple, movie.getName());
		writeGenre(apple, movie.getName());
//		writeCover(apple, movie.getCoverFile());
		writeMediaType(apple, "9");
					
		Mp4MetadataBalancer mdb = new Mp4MetadataBalancer();
		boolean needsCorrection = mdb.needsOffsetCorrection(isoFile);
		
		long sizeAfter = moov.getSize();
		logger.debug(UserDataBox.class.getSimpleName()+" "+sizeBefore);
		logger.debug(UserDataBox.class.getSimpleName()+" "+sizeAfter);
		logger.debug(UserDataBox.class.getSimpleName()+" needs corrction:"+needsCorrection);
		if (needsCorrection)
        {
            mdb.correctChunkOffsets(isoFile, sizeAfter - sizeBefore);
        }
		
		isoFile.getBox(fcw);
		fcw.force(true);fcw.close();rafW.close();
		fcr.close();rafR.close();
	}

	private void writeMovieTitle(AppleItemListBox apple, String title)
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
		titleBox.setValue(title);
		apple.addBox(titleBox);
	}
	
	private void writeGenre(AppleItemListBox apple, String genre)
	{
		AppleCustomGenreBox genreBox = null;
		if (apple.getBoxes(AppleCustomGenreBox.class).isEmpty())
		{
			genreBox = new AppleCustomGenreBox();
		}
		else
		{
			genreBox = (AppleCustomGenreBox) apple.getBoxes(AppleCustomGenreBox.class).get(0);
		}
		genreBox.setValue(genre);
		apple.addBox(genreBox);
	}
}