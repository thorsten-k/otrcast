package net.sf.otrcutmp4.controller.tag;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import net.sf.otrcutmp4.test.AbstractUtilTest;
import net.sf.otrcutmp4.test.OtrUtilTestBootstrap;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.MediaBox;
import com.coremedia.iso.boxes.MediaHeaderBox;
import com.coremedia.iso.boxes.MetaBox;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.MovieHeaderBox;
import com.coremedia.iso.boxes.TrackBox;
import com.coremedia.iso.boxes.TrackHeaderBox;
import com.coremedia.iso.boxes.UserDataBox;
import com.coremedia.iso.boxes.apple.AppleDataBox;
import com.coremedia.iso.boxes.apple.AppleItemListBox;
import com.coremedia.iso.boxes.apple.AppleMediaTypeBox;
import com.coremedia.iso.boxes.apple.AppleRecordingYearBox;
import com.coremedia.iso.boxes.apple.AppleShowBox;
import com.coremedia.iso.boxes.apple.AppleTrackTitleBox;
import com.coremedia.iso.boxes.apple.AppleTvEpisodeBox;
import com.coremedia.iso.boxes.apple.AppleTvSeasonBox;

public class DebugAppleBoxes extends AbstractUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(DebugAppleBoxes.class);
	
	public void debug(String filename) throws IOException
	{
		logger.info("Debugging "+filename);
		RandomAccessFile raf = new RandomAccessFile(filename, "r");
		FileChannel fcr = raf.getChannel();
		
		IsoFile isoFile = new IsoFile(fcr);
		
		//Get the MovieBox
		MovieBox moov = Mp4BoxManager.movieBox(isoFile);
		logger.info("***** "+MovieBox.class.getSimpleName());
		for(Box box : moov.getBoxes()){debugBox(box);}
		
		//Get the Meta Data from the UserDataBox and get the Apple meta-data
		UserDataBox udta = moov.getBoxes(UserDataBox.class).get(0);
		logger.info("***** "+UserDataBox.class.getSimpleName());
		for(Box box : udta.getBoxes()){debugBox(box);}
		
		MetaBox meta = udta.getBoxes(MetaBox.class).get(0);
		logger.info("***** "+MetaBox.class.getSimpleName());
		for(Box box : udta.getBoxes()){debugBox(box);}
		
		AppleItemListBox apple = meta.getBoxes(AppleItemListBox.class).get(0);
		
		for(Box box : apple.getBoxes())
		{
			logger.debug(box.getClass().getSimpleName());
		}
		
		debugAppleTrackTitleBoxd(apple);
		debugAppleRecordingYearBox(apple);
		debugAppleTvEpisodeBox(apple);
		debugAppleTvSeasonBox(apple);
		debugAppleShowBox(apple);
		debugAppleMediaTypeBox(apple);
		
		fcr.close();
		raf.close();
	}
	
	private void debugAppleTrackTitleBoxd(AppleItemListBox apple)
	{
		logger.debug("***** "+AppleTrackTitleBox.class.getSimpleName()+" "+apple.getBoxes(AppleTrackTitleBox.class).size());
		if (!apple.getBoxes(AppleTrackTitleBox.class).isEmpty())
		{
			for(AppleTrackTitleBox ttBox : apple.getBoxes(AppleTrackTitleBox.class))
			{
				logger.debug(ttBox.getType()+" "+ttBox.getValue());
				for(Box box : ttBox.getBoxes())
				{
					logger.debug("\t"+box.getClass().getSimpleName());
					if(box instanceof AppleDataBox){debugAppleDataBox((AppleDataBox)box);}
				}
			}
		}
	}
	
	private void debugAppleTvEpisodeBox(AppleItemListBox apple)
	{
		logger.debug("***** "+AppleTvSeasonBox.class.getSimpleName()+" "+apple.getBoxes(AppleTvSeasonBox.class).size());
		if (!apple.getBoxes(AppleTvEpisodeBox.class).isEmpty())
		{
			for(AppleTvSeasonBox parentBox : apple.getBoxes(AppleTvSeasonBox.class))
			{
			logger.debug(parentBox.getType()+" "+parentBox.getValue());
				for(Box box : parentBox.getBoxes())
				{
					logger.debug("\t"+box.getClass().getSimpleName());
					if(box instanceof AppleDataBox){debugAppleDataBox((AppleDataBox)box);}
				}
			}
		}
	}
	
	private void debugAppleShowBox(AppleItemListBox apple)
	{
		logger.debug("***** "+AppleShowBox.class.getSimpleName()+" "+apple.getBoxes(AppleShowBox.class).size());
		if (!apple.getBoxes(AppleShowBox.class).isEmpty())
		{
			for(AppleShowBox parentBox : apple.getBoxes(AppleShowBox.class))
			{
			logger.debug(parentBox.getType()+" "+parentBox.getValue());
				for(Box box : parentBox.getBoxes())
				{
					logger.debug("\t"+box.getClass().getSimpleName());
					if(box instanceof AppleDataBox){debugAppleDataBox((AppleDataBox)box);}
				}
			}
		}
	}
	
	private void debugAppleTvSeasonBox(AppleItemListBox apple)
	{
		logger.debug("***** "+AppleTvEpisodeBox.class.getSimpleName()+" "+apple.getBoxes(AppleTvEpisodeBox.class).size());
		if (!apple.getBoxes(AppleTvEpisodeBox.class).isEmpty())
		{
			for(AppleTvEpisodeBox tvEpisodeBox : apple.getBoxes(AppleTvEpisodeBox.class))
			{
			logger.debug(tvEpisodeBox.getType()+" "+tvEpisodeBox.getValue());
				for(Box box : tvEpisodeBox.getBoxes())
				{
					logger.debug("\t"+box.getClass().getSimpleName());
					if(box instanceof AppleDataBox){debugAppleDataBox((AppleDataBox)box);}
				}
			}
		}
	}
	
	private void debugAppleMediaTypeBox(AppleItemListBox apple)
	{
		logger.debug("***** "+AppleMediaTypeBox.class.getSimpleName()+" "+apple.getBoxes(AppleMediaTypeBox.class).size());
		if (!apple.getBoxes(AppleMediaTypeBox.class).isEmpty())
		{
			for(AppleMediaTypeBox mtEpisodeBox : apple.getBoxes(AppleMediaTypeBox.class))
			{
			logger.debug(mtEpisodeBox.getType()+" "+mtEpisodeBox.getValue());
				for(Box box : mtEpisodeBox.getBoxes())
				{
					logger.debug("\t"+box.getClass().getSimpleName());
					if(box instanceof AppleDataBox){debugAppleDataBox((AppleDataBox)box);}
				}
			}
		}
	}
	
	private void debugAppleRecordingYearBox(AppleItemListBox apple)
	{
		logger.debug("***** "+AppleRecordingYearBox.class.getSimpleName()+" "+apple.getBoxes(AppleRecordingYearBox.class).size());
		if (!apple.getBoxes(AppleRecordingYearBox.class).isEmpty())
		{
			for(AppleRecordingYearBox ryEpisodeBox : apple.getBoxes(AppleRecordingYearBox.class))
			{
			logger.debug(ryEpisodeBox.getValue());
				for(Box box : ryEpisodeBox.getBoxes())
				{
					logger.debug("\t"+box.getClass().getSimpleName());
					if(box instanceof AppleDataBox){debugAppleDataBox((AppleDataBox)box);}
				}
			}
		}
	}
	
	private void debugAppleDataBox(AppleDataBox db)
	{
		logger.debug("\t\t"+db.getType()+" "+db.getVersion()+" "+db.getSize()+" "+new String(db.getData()));
	}
	
	private void debugBox(Box box)
	{
		logger.debug(box.getClass().getSimpleName()+" "+box.getType());
		if(box instanceof TrackBox){debugTrackBox((TrackBox) box);}
		else if(box instanceof MediaBox){debugMediaBox((MediaBox) box);}
		else if(box instanceof TrackHeaderBox){debugTrackHeaderBox((TrackHeaderBox) box);}
		else if(box instanceof MovieHeaderBox){debugMovieHeaderBox((MovieHeaderBox) box);}
		else if(box instanceof MetaBox){debugMetaBox((MetaBox) box);}
		else if(box instanceof MediaHeaderBox){debugMediaHeaderBox((MediaHeaderBox)box);}
		else {logger.warn("no handling");}
	}
	
	private void debugTrackBox(TrackBox box)
	{
		logger.info("\t"+box.getType());
		for(Box child : box.getBoxes()){debugBox(child);}
	}
	
	private void debugTrackHeaderBox(TrackHeaderBox box)
	{
		logger.info("\t"+box.getType());
		logger.info("\t"+box.getWidth()+"x"+box.getHeight());
	}
	
	private void debugMovieHeaderBox(MovieHeaderBox box)
	{
		logger.info("\t"+box.getType());
		logger.info("\t"+box.getSize()+"");
	}
	
	private void debugMediaHeaderBox(MediaHeaderBox box)
	{
		logger.debug(box.getType());
	}
	
	private void debugMetaBox(MetaBox box)
	{
		for(Box child : box.getBoxes())
		{
			debugBox(child);
		}
	}
	
	private void debugMediaBox(MediaBox box)
	{

		for(Box child : box.getBoxes())
		{
			debugBox(child);
		}
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = OtrUtilTestBootstrap.init();
		
		String src = config.getString("test.mp4Tagger.dst");
		String fs = SystemUtils.FILE_SEPARATOR;
		
		List<String> files = new ArrayList<String>();
		files.add("AviCutMp4.mp4"); //... Transcoded AVI to MP4 by AviCutMp4
//		files.add("iTunes.mp4"); //... Transcoded by onlinetvrecorder.com
		
		DebugAppleBoxes test = new DebugAppleBoxes();
		for(String s : files)
		{
			test.debug(src+fs+s);
		}
	}
 }