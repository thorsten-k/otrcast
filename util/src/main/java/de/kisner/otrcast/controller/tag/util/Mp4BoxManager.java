package de.kisner.otrcast.controller.tag.util;


import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.MetaBox;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.UserDataBox;
import com.coremedia.iso.boxes.apple.AppleItemListBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Mp4BoxManager
{
	final static Logger logger = LoggerFactory.getLogger(Mp4BoxManager.class);

    public static enum Type {UNKNOWN,MOVIE,SERIES}
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
}