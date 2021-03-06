package de.kisner.otrcast.controller.tag.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coremedia.iso.boxes.apple.AppleCoverBox;
import com.coremedia.iso.boxes.apple.AppleItemListBox;
import com.coremedia.iso.boxes.apple.AppleRecordingYearBox;
import com.coremedia.iso.boxes.apple.AppleTrackTitleBox;

import de.kisner.otrcast.model.xml.video.tv.Movie;

public class MovieTagReader extends AbstractTagReader
{
	final static Logger logger = LoggerFactory.getLogger(MovieTagReader.class);
	
	private boolean withCover;
	
	protected MovieTagReader(boolean withCover)
	{
		this.withCover=withCover;
	}
		
	public Movie readMovie(AppleItemListBox apple)
	{
		Movie movie = new Movie();
		if (!apple.getBoxes(AppleTrackTitleBox.class).isEmpty())
		{
			AppleTrackTitleBox box = apple.getBoxes(AppleTrackTitleBox.class).get(0);
			movie.setName(box.getValue());
		}
		else
		{
			logger.warn("Strange, no TrackTitleBox in "+file.getAbsolutePath());
		}
		if (!apple.getBoxes(AppleRecordingYearBox.class).isEmpty())
		{
			AppleRecordingYearBox box = apple.getBoxes(AppleRecordingYearBox.class).get(0);
			movie.setYear(new Integer(box.getValue()));
		}
		if(withCover && !apple.getBoxes(AppleCoverBox.class).isEmpty())
		{
			try
			{
				movie.setImage(getCover(apple.getBoxes(AppleCoverBox.class).get(0)));
			}
			catch (NoSuchFieldException e) {}
		}
		return movie;
	}
}