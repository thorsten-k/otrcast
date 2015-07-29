package de.kisner.otrcast.controller.tag;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.DataInformationBox;
import com.coremedia.iso.boxes.EditBox;
import com.coremedia.iso.boxes.EditListBox;
import com.coremedia.iso.boxes.HandlerBox;
import com.coremedia.iso.boxes.MediaBox;
import com.coremedia.iso.boxes.MediaHeaderBox;
import com.coremedia.iso.boxes.MediaInformationBox;
import com.coremedia.iso.boxes.MetaBox;
import com.coremedia.iso.boxes.MovieBox;
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

import de.kisner.otrcast.controller.tag.util.Mp4BoxDebugger;
import de.kisner.otrcast.controller.tag.util.Mp4BoxManager;
import de.kisner.otrcast.test.AbstractUtilTest;
import de.kisner.otrcast.test.OtrUtilTestBootstrap;

public class CliMp4BoxDebugger extends AbstractUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(CliMp4BoxDebugger.class);
	
	public void debug(String filename) throws IOException
	{
		logger.info("Debugging "+filename);
		RandomAccessFile raf = new RandomAccessFile(filename, "r");
		FileChannel fcr = raf.getChannel();
		
		IsoFile isoFile = new IsoFile(fcr);
		
		MovieBox moov = Mp4BoxManager.movieBox(isoFile);
		logger.info("***** "+MovieBox.class.getSimpleName());
		for(Box box : moov.getBoxes()){handleBox(0,box);}

		fcr.close();
		raf.close();
	}
	
	private void handleBox(int lvl, Box box)
	{
		Mp4BoxDebugger.debug(lvl, box);
		if(box instanceof MovieHeaderBox)
		{
			Mp4BoxDebugger.debug(lvl+1, (MovieHeaderBox)box);
		}
		else if(box instanceof UnknownBox)
		{
			Mp4BoxDebugger.debug(lvl+1, (UnknownBox)box);
		}
		else if(box instanceof TrackBox)
		{
			TrackBox b = (TrackBox)box;
			Mp4BoxDebugger.debug(lvl+1, b);
//			for(Box child : b.getBoxes()){handleBox(lvl+1,child);}
		}
		else if(box instanceof TrackHeaderBox)
		{
			Mp4BoxDebugger.debug(lvl+1, (TrackHeaderBox)box);
		}
		else if(box instanceof MediaBox)
		{
			MediaBox b = (MediaBox)box;
			Mp4BoxDebugger.debug(lvl+1, b);
			for(Box child : b.getBoxes()){handleBox(lvl+1,child);}
		}
		else if(box instanceof EditBox)
		{
			EditBox b = (EditBox)box;
			Mp4BoxDebugger.debug(lvl+1, b);
			for(Box child : b.getBoxes()){handleBox(lvl+1,child);}
		}
		else if(box instanceof MediaInformationBox)
		{
			MediaInformationBox b = (MediaInformationBox)box;
			Mp4BoxDebugger.debug(lvl+1, b);
			for(Box child : b.getBoxes()){handleBox(lvl+1,child);}
		}
		else if(box instanceof UserDataBox)
		{
			UserDataBox b = (UserDataBox)box;
			Mp4BoxDebugger.debug(lvl+1, b);
			for(Box child : b.getBoxes()){handleBox(lvl+1,child);}
		}
		else if(box instanceof MetaBox)
		{
			MetaBox b = (MetaBox)box;
			Mp4BoxDebugger.debug(lvl+1, b);
			for(Box child : b.getBoxes()){handleBox(lvl+1,child);}
		}
		else if(box instanceof AppleItemListBox)
		{
			AppleItemListBox b = (AppleItemListBox)box;
			Mp4BoxDebugger.debug(lvl+1, b);
			for(Box child : b.getBoxes()){handleBox(lvl+1,child);}
		}
		else if(box instanceof AppleEncoderBox)
		{
			AppleEncoderBox b = (AppleEncoderBox)box;
			Mp4BoxDebugger.debug(lvl+1, b);
//			for(Box child : b.getBoxes()){handleBox(lvl+1,child);}
		}
		else if(box instanceof AppleTrackTitleBox)
		{
			AppleTrackTitleBox b = (AppleTrackTitleBox)box;
			Mp4BoxDebugger.debug(lvl+1, b);
//			for(Box child : b.getBoxes()){handleBox(lvl+1,child);}
		}
		else if(box instanceof AppleTvEpisodeBox)
		{
			AppleTvEpisodeBox b = (AppleTvEpisodeBox)box;
			Mp4BoxDebugger.debug(lvl+1, b);
//			for(Box child : b.getBoxes()){handleBox(lvl+1,child);}
		}
		else if(box instanceof AppleTvSeasonBox)
		{
			AppleTvSeasonBox b = (AppleTvSeasonBox)box;
			Mp4BoxDebugger.debug(lvl+1, b);
//			for(Box child : b.getBoxes()){handleBox(lvl+1,child);}
		}
		else if(box instanceof AppleShowBox)
		{
			AppleShowBox b = (AppleShowBox)box;
			Mp4BoxDebugger.debug(lvl+1, b);
//			for(Box child : b.getBoxes()){handleBox(lvl+1,child);}
		}
		else if(box instanceof AppleCoverBox)
		{
			AppleCoverBox b = (AppleCoverBox)box;
			Mp4BoxDebugger.debug(lvl+1, b);
			for(Box child : b.getBoxes()){handleBox(lvl+1,child);}
		}
		else if(box instanceof AppleMediaTypeBox)
		{
			AppleMediaTypeBox b = (AppleMediaTypeBox)box;
			Mp4BoxDebugger.debug(lvl+1, b);
//			for(Box child : b.getBoxes()){handleBox(lvl+1,child);}
		}
		else if(box instanceof AppleDataBox){Mp4BoxDebugger.debug(lvl+1, (AppleDataBox)box);}
		else if(box instanceof EditListBox){Mp4BoxDebugger.debug(lvl+1, (EditListBox)box);}
		else if(box instanceof MediaHeaderBox){Mp4BoxDebugger.debug(lvl+1, (MediaHeaderBox)box);}
		else if(box instanceof HandlerBox){Mp4BoxDebugger.debug(lvl+1, (HandlerBox)box);}
		
		else if(box instanceof SoundMediaHeaderBox){Mp4BoxDebugger.debug(lvl+1, (SoundMediaHeaderBox)box);}
		else if(box instanceof VideoMediaHeaderBox){Mp4BoxDebugger.debug(lvl+1, (VideoMediaHeaderBox)box);}
		else if(box instanceof DataInformationBox){Mp4BoxDebugger.debug(lvl+1, (DataInformationBox)box);}
		else if(box instanceof SampleTableBox){Mp4BoxDebugger.debug(lvl+1, (SampleTableBox)box);}
		
		else {logger.warn(StringUtils.repeat("\t",lvl+1)+"no handling");}
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = OtrUtilTestBootstrap.init();
		
		String src = config.getString("test.mp4Tagger.dst");
		String fs = SystemUtils.FILE_SEPARATOR;
		
		List<String> files = new ArrayList<String>();
//		files.add("OtrCutMp4.mp4");
		files.add("Vatertag.mp4");

		
		CliMp4BoxDebugger test = new CliMp4BoxDebugger();
		for(String s : files)
		{
			test.debug(src+fs+s);
		}
	}
 }