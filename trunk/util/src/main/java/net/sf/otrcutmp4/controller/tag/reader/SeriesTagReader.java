package net.sf.otrcutmp4.controller.tag.reader;

import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.model.xml.series.Movie;
import net.sf.otrcutmp4.model.xml.series.Season;
import net.sf.otrcutmp4.model.xml.series.Series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coremedia.iso.boxes.apple.AppleCoverBox;
import com.coremedia.iso.boxes.apple.AppleItemListBox;
import com.coremedia.iso.boxes.apple.AppleShowBox;
import com.coremedia.iso.boxes.apple.AppleTrackTitleBox;
import com.coremedia.iso.boxes.apple.AppleTvEpisodeBox;
import com.coremedia.iso.boxes.apple.AppleTvSeasonBox;

public class SeriesTagReader extends AbstractTagReader
{
	final static Logger logger = LoggerFactory.getLogger(SeriesTagReader.class);
	
	private boolean withCover;
	
	public SeriesTagReader(boolean withCover)
	{
		this.withCover=withCover;
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
		if (!apple.getBoxes(AppleTvEpisodeBox.class).isEmpty())
		{
			AppleTvEpisodeBox box = apple.getBoxes(AppleTvEpisodeBox.class).get(0);
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
}