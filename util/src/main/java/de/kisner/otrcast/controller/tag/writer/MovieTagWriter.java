package de.kisner.otrcast.controller.tag.writer;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coremedia.iso.boxes.apple.AppleCustomGenreBox;
import com.coremedia.iso.boxes.apple.AppleItemListBox;
import com.coremedia.iso.boxes.apple.AppleTrackTitleBox;

import de.kisner.otrcast.controller.tag.util.Mp4BoxManager;
import de.kisner.otrcast.model.xml.video.tv.Movie;

public class MovieTagWriter extends AbstractTagWriter
{
	final static Logger logger = LoggerFactory.getLogger(MovieTagWriter.class);

	public MovieTagWriter()
	{
		super(null);
	}
	
	public void tagMovie(File srcFile, Movie movie, File dstFile) throws IOException
	{
		readMp4Box(srcFile);

		writeMovieTitle(apple, movie.getName());
		writeGenre(apple, movie.getName());
//		writeCover(apple, movie.getCoverFile());
		writeMediaType(apple, Mp4BoxManager.Type.MOVIE);
					
		writeMp4(dstFile);
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