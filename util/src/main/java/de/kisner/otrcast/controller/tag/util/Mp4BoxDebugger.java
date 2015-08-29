package de.kisner.otrcast.controller.tag.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.DataInformationBox;
import com.coremedia.iso.boxes.EditBox;
import com.coremedia.iso.boxes.EditListBox;
import com.coremedia.iso.boxes.HandlerBox;
import com.coremedia.iso.boxes.MediaBox;
import com.coremedia.iso.boxes.MediaHeaderBox;
import com.coremedia.iso.boxes.MediaInformationBox;
import com.coremedia.iso.boxes.MetaBox;
import com.coremedia.iso.boxes.MovieHeaderBox;
import com.coremedia.iso.boxes.SampleTableBox;
import com.coremedia.iso.boxes.SoundMediaHeaderBox;
import com.coremedia.iso.boxes.TrackBox;
import com.coremedia.iso.boxes.TrackHeaderBox;
import com.coremedia.iso.boxes.UnknownBox;
import com.coremedia.iso.boxes.UserDataBox;
import com.coremedia.iso.boxes.VideoMediaHeaderBox;
import com.coremedia.iso.boxes.apple.AppleCoverBox;
import com.coremedia.iso.boxes.apple.AppleDataBox;
import com.coremedia.iso.boxes.apple.AppleEncoderBox;
import com.coremedia.iso.boxes.apple.AppleItemListBox;
import com.coremedia.iso.boxes.apple.AppleMediaTypeBox;
import com.coremedia.iso.boxes.apple.AppleShowBox;
import com.coremedia.iso.boxes.apple.AppleTrackTitleBox;
import com.coremedia.iso.boxes.apple.AppleTvEpisodeBox;
import com.coremedia.iso.boxes.apple.AppleTvSeasonBox;

public class Mp4BoxDebugger
{
	final static Logger logger = LoggerFactory.getLogger(Mp4BoxDebugger.class);
	
	public static void debug(int lvl, Box box)
	{
		debug(lvl,box.getClass().getSimpleName(),box.getType()+" ("+box.getSize()+")");
	}
	
	public static void debug(int lvl, TrackBox box)
	{
		debug(lvl,"Childs",box.getBoxes().size());
	}
	
	public static void debug(int lvl, MovieHeaderBox box)
	{
		debug(lvl,"Duration",box.getDuration());
	}
	
	public static void debug(int lvl, UnknownBox box)
	{

	}
	
	public static void debug(int lvl,TrackHeaderBox box)
	{
		Double width = box.getWidth();
		Double height = box.getHeight();
		debug(lvl,"Dimension",width.intValue()+"x"+height.intValue());
	}
	
	public static void debug(int lvl,MediaBox box)
	{
		debug(lvl,"Childs",box.getBoxes().size());
	}
	
	public static void debug(int lvl,MediaHeaderBox box)
	{
		debug(lvl,"Language",box.getLanguage());
//		debug(lvl,"Childs",0);
	}
	
	public static void debug(int lvl,HandlerBox box)
	{
		debug(lvl,"HumanReadableTrackType",box.getHumanReadableTrackType());
//		debug(lvl,"Childs",0);
	}
	
	public static void debug(int lvl,MediaInformationBox box)
	{
		debug(lvl,"Childs",box.getBoxes().size());
	}
	
	public static void debug(int lvl,VideoMediaHeaderBox box)
	{
		debug(lvl,"Graphicsmode",box.getGraphicsmode());
	}
	
	public static void debug(int lvl,SoundMediaHeaderBox box)
	{
		debug(lvl,"Balance",box.getBalance());
	}
	
	public static void debug(int lvl,DataInformationBox box)
	{
		debug(lvl,"Childs",box.getBoxes().size());
	}
	
	public static void debug(int lvl,SampleTableBox box)
	{
		debug(lvl,"Childs",box.getBoxes().size());
	}
	
	public static void debug(int lvl,EditBox box)
	{
		debug(lvl,"Childs",box.getBoxes().size());
	}
	
	public static void debug(int lvl,EditListBox box)
	{
		debug(lvl,"Flags",box.getFlags());
	}
	
	public static void debug(int lvl,UserDataBox box)
	{
		debug(lvl,"Childs",box.getBoxes().size());
	}
	
	public static void debug(int lvl,MetaBox box)
	{
		debug(lvl,"Mp4",box.isMp4Box());
		debug(lvl,"Childs",box.getBoxes().size());
	}
	
	public static void debug(int lvl,AppleItemListBox box)
	{
		debug(lvl,"Childs",box.getBoxes().size());
	}
	
	public static void debug(int lvl,AppleTrackTitleBox box)
	{
		debug(lvl,"Name",box.getValue());
//		debug(lvl,"Childs",box.getBoxes().size());
	}
	
	public static void debug(int lvl,AppleTvEpisodeBox box)
	{
		debug(lvl,"Nr",box.getValue());
//		debug(lvl,"Childs",box.getBoxes().size());
	}
	
	public static void debug(int lvl,AppleTvSeasonBox box)
	{
		debug(lvl,"Nr",box.getValue());
//		debug(lvl,"Childs",box.getBoxes().size());
	}
	
	public static void debug(int lvl,AppleShowBox box)
	{
		debug(lvl,"Show",box.getValue());
//		debug(lvl,"Childs",box.getBoxes().size());
	}
	
	public static void debug(int lvl,AppleCoverBox box)
	{
		debug(lvl,"Childs",box.getBoxes().size());
	}
	
	public static void debug(int lvl,AppleMediaTypeBox box)
	{
		debug(lvl,"Type",box.getValue());
//		debug(lvl,"Childs",box.getBoxes().size());
	}
	
	public static void debug(int lvl,AppleEncoderBox box)
	{
		debug(lvl,"Encoder",box.getValue());
//		debug(lvl,"Childs",box.getBoxes().size());
	}
	
	public static void debug(int lvl,AppleDataBox box)
	{
		debug(lvl,"Flags",box.getFlags());
		debug(lvl,"Version",box.getVersion());
		if(box.getData().length<100)
		{
			debug(lvl,"Data",""+new String(box.getData()));
		}
		else
		{
			debug(lvl,"Data","<100");
		}
	}
	
	private static void debug(int lvl, String key, long value){debug(lvl,key,""+value);}
	private static void debug(int lvl, String key, float value){debug(lvl,key,""+value);}
	private static void debug(int lvl, String key, boolean value){debug(lvl,key,""+value);}
	private static void debug(int lvl, String key, String value)
	{
		logger.debug(StringUtils.repeat("\t",lvl)+key+": "+value);
	}
}