package de.kisner.otrcast.controller.tag.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.MetaBox;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.UserDataBox;
import com.coremedia.iso.boxes.apple.AppleCoverBox;
import com.coremedia.iso.boxes.apple.AppleItemListBox;
import com.coremedia.iso.boxes.apple.AppleShowBox;
import com.coremedia.iso.boxes.apple.AppleTrackTitleBox;
import com.coremedia.iso.boxes.apple.AppleTvEpisodeBox;
import com.coremedia.iso.boxes.apple.AppleTvEpisodeNumberBox;
import com.coremedia.iso.boxes.apple.AppleTvSeasonBox;

public class Mp4BoxManager
{
	final static Logger logger = LoggerFactory.getLogger(Mp4BoxManager.class);

    public static enum Type {UNKNOWN,MOVIE,SERIES}
    public static final String typeMovie = "9";
    public static final String typeSeries = "10";

	public static MovieBox movieBox(IsoFile isoFile)
	{
		return isoFile.getBoxes(MovieBox.class).get(0);
	}
	
	public static UserDataBox userDataBox(MovieBox movieBox)
	{
		List<UserDataBox> boxes = movieBox.getBoxes(UserDataBox.class);
		if(boxes.size()>0)
		{
			return boxes.get(0);
		}
		else
		{
			UserDataBox udta = new UserDataBox();
			movieBox.addBox(udta);
			return udta;
		}
	}
	
	public static MetaBox metaBox(UserDataBox userDataBox)
	{
		List<MetaBox> boxes = userDataBox.getBoxes(MetaBox.class);
		if(boxes.size()>0)
		{
			return boxes.get(0);
		}
		else
		{
			MetaBox meta = new MetaBox();
			userDataBox.addBox(meta);
			return meta;
		}
	}
	
	public static AppleItemListBox appleItemListBox(MetaBox metaBox)
	{
		List<AppleItemListBox> boxes = metaBox.getBoxes(AppleItemListBox.class);
		if(boxes.size()>0)
		{
			return boxes.get(0);
		}
		else
		{
			AppleItemListBox apple = new AppleItemListBox();
			metaBox.addBox(apple);
			return apple;
		}
	}
	
	// Series
	public static AppleTrackTitleBox fcAppleTrackTitleBox(AppleItemListBox apple)
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
		return titleBox;
	}
	
	public static AppleTvEpisodeBox fcAppleTvEpisodeBox(AppleItemListBox apple)
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
		return episodeBox;
	}
	
	public static AppleTvSeasonBox fcAppleTvSeasonBox(AppleItemListBox apple)
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
		return seasonBox;
	}
	
	public static AppleShowBox fcAppleShowBox(AppleItemListBox apple)
	{
		AppleShowBox box = null;
		if(apple.getBoxes(AppleShowBox.class).isEmpty())
		{
			box = new AppleShowBox();
		}
		else
		{
			box = (AppleShowBox) apple.getBoxes(AppleShowBox.class).get(0);
		}
		return box;
	}
	
	public static AppleTvEpisodeNumberBox fcAppleTvEpisodeNumberBox(AppleItemListBox apple)
	{
		AppleTvEpisodeNumberBox box = null;
		if(apple.getBoxes(AppleTvEpisodeNumberBox.class).isEmpty())
		{
			box = new AppleTvEpisodeNumberBox();
		}
		else
		{
			box = (AppleTvEpisodeNumberBox) apple.getBoxes(AppleTvEpisodeNumberBox.class).get(0);
		}
		return box;
	}
	
	public static AppleCoverBox fcAppleCoverBox(AppleItemListBox apple)
	{
		AppleCoverBox box = null;
		if(apple.getBoxes(AppleCoverBox.class).isEmpty())
		{
			box = new AppleCoverBox();
		}
		else
		{
			box = (AppleCoverBox) apple.getBoxes(AppleCoverBox.class).get(0);
		}
		return box;
	}
}