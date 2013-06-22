package net.sf.otrcutmp4.controller.tagger;

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
import com.coremedia.iso.boxes.MetaBox;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.UserDataBox;
import com.coremedia.iso.boxes.apple.AppleDataBox;
import com.coremedia.iso.boxes.apple.AppleItemListBox;
import com.coremedia.iso.boxes.apple.AppleMediaTypeBox;
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
		
		//Get the Meta Data from the UserDataBox and get the Apple meta-data
		UserDataBox udta = moov.getBoxes(UserDataBox.class).get(0);
		MetaBox meta = udta.getBoxes(MetaBox.class).get(0);
		AppleItemListBox apple = meta.getBoxes(AppleItemListBox.class).get(0);
		
		for(Box box : apple.getBoxes())
		{
			logger.debug(box.getClass().getSimpleName());
		}
		
		debugAppleTrackTitleBoxd(apple);
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
	
	private void debugAppleDataBox(AppleDataBox db)
	{
		logger.debug("\t\t"+db.getType()+" "+db.getVersion()+" "+db.getSize()+" "+new String(db.getData()));
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = OtrUtilTestBootstrap.init();
		
		String src = config.getString("test.mp4Tagger.src");
		String fs = SystemUtils.FILE_SEPARATOR;
		
		List<String> files = new ArrayList<String>();
		files.add("AviCutMp4.mp4"); //... Transcoded AVI to MP4 by AviCutMp4
		files.add("iTunes.mp4"); //... Transcoded by onlinetvrecorder.com
		
		DebugAppleBoxes test = new DebugAppleBoxes();
		for(String s : files)
		{
			test.debug(src+fs+s);
		}
	}
 }