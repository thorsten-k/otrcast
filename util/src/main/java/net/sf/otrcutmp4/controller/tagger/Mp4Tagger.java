package net.sf.otrcutmp4.controller.tagger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import net.sf.otrcutmp4.interfaces.controller.CoverManager;
import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.model.xml.series.Season;
import net.sf.otrcutmp4.model.xml.series.Series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.MetaBox;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.UserDataBox;
import com.coremedia.iso.boxes.apple.AppleCoverBox;
import com.coremedia.iso.boxes.apple.AppleItemListBox;
import com.coremedia.iso.boxes.apple.AppleMediaTypeBox;
import com.coremedia.iso.boxes.apple.AppleShowBox;
import com.coremedia.iso.boxes.apple.AppleTrackTitleBox;
import com.coremedia.iso.boxes.apple.AppleTvEpisodeBox;
import com.coremedia.iso.boxes.apple.AppleTvSeasonBox;

public class Mp4Tagger
{
	final static Logger logger = LoggerFactory.getLogger(Mp4Tagger.class);

	private CoverManager coverManager;
	
	public Mp4Tagger()
	{
		coverManager = null;
	}
	public Mp4Tagger(CoverManager coverManager)
	{
		this.coverManager=coverManager;
	}
	
	public void tagEpisode(String srcFileName, Episode episode, String dstFileName) throws IOException
	{
		File srcFile = new File(srcFileName);
		File dstFile = new File(dstFileName);
		
		logger.info("srcFile: "+srcFile.getAbsolutePath());
		logger.info("dstFile: "+dstFile.getAbsolutePath());
		
		tagEpisode(srcFile, episode, dstFile);
	}

	public void tagEpisode(File srcFile, Episode episode, File dstFile) throws IOException
	{
//		JaxbUtil.info(episode);
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
		
		//TODO Write a method to make this code DRY-compatible!
		
		writeEpisodeName(apple, episode);
		writeEpisodeNr(apple, episode);
		writeSeason(apple, episode.getSeason());
		writeSeries(apple, episode.getSeason().getSeries());
		writeCover(apple, episode.getSeason());
		writeMediaType(apple);
					
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
	
	private void writeEpisodeName(AppleItemListBox apple, Episode episode)
	{
		AppleTrackTitleBox titleBox = null;
		if (apple.getBoxes(AppleTrackTitleBox.class).isEmpty())
		{
			titleBox = new AppleTrackTitleBox();
		}
		else
		{
			titleBox = apple.getBoxes(AppleTrackTitleBox.class).get(0);
		}
		titleBox.setValue(episode.getName());
		apple.addBox(titleBox);
	}
	
	private void writeEpisodeNr(AppleItemListBox apple, Episode episode)
	{
		AppleTvEpisodeBox episodeBox = null;
		if (apple.getBoxes(AppleTvEpisodeBox.class).isEmpty())
		{
			episodeBox = new AppleTvEpisodeBox();
		}
		else
		{
			episodeBox = apple.getBoxes(AppleTvEpisodeBox.class).get(0);
			
		}
		episodeBox.setValue(episode.getNr() +"");
		apple.addBox(episodeBox);
	}
	
	private void writeSeason(AppleItemListBox apple, Season season)
	{
		AppleTvSeasonBox seasonBox = null;
		if(apple.getBoxes(AppleTvSeasonBox.class).isEmpty())
		{
			seasonBox = new AppleTvSeasonBox();
		}
		else
		{
			seasonBox =apple.getBoxes(AppleTvSeasonBox.class).get(0);
		}
		seasonBox.setValue(season.getNr()+"");
		apple.addBox(seasonBox);
	}
	
	private void writeMediaType(AppleItemListBox apple)
	{
		AppleMediaTypeBox box = null;
		if(apple.getBoxes(AppleMediaTypeBox.class).isEmpty())
		{
			box = new AppleMediaTypeBox();
		}
		else
		{
			box = apple.getBoxes(AppleMediaTypeBox.class).get(0);
		}
		box.setValue("10");
		apple.addBox(box);
	}
	
	private void writeSeries(AppleItemListBox apple, Series series)
	{
		logger.debug("Writing Series");
		AppleShowBox box = null;
		if(apple.getBoxes(AppleShowBox.class).isEmpty())
		{
			logger.debug(AppleShowBox.class.getSimpleName()+" is empty");
			box = new AppleShowBox();
		}
		else
		{
			box = apple.getBoxes(AppleShowBox.class).get(0);
			logger.debug(AppleShowBox.class.getSimpleName()+" exists: "+box.getValue());
		}
		box.setValue(series.getName());
		apple.addBox(box);
	}
	
	private void writeCover(AppleItemListBox apple, Season season) throws IOException
	{
		boolean abortNoCoverManager = (coverManager==null);
		boolean aboirtNoSeriesKey = !season.getSeries().isSetKey();
		
		logger.trace("Aboirt because no coverManager?"+abortNoCoverManager);
		logger.trace("Aboirt because no series@key?"+aboirtNoSeriesKey);
		
		if(abortNoCoverManager || aboirtNoSeriesKey){return;}
		
		boolean coverAvailable = coverManager.isAvailable(season);
		logger.info("Available: "+coverAvailable);
		if(coverAvailable)
		{
			logger.debug("Writing Cover "+season.getSeries().getKey());
			AppleCoverBox box = null;
			if(apple.getBoxes(AppleCoverBox.class).isEmpty())
			{
				logger.debug(AppleShowBox.class.getSimpleName()+" is empty");
				box = new AppleCoverBox();
			}
			else
			{
				box = (AppleCoverBox) apple.getBoxes(AppleCoverBox.class).get(0);
				logger.debug(AppleCoverBox.class.getSimpleName()+" exists: "+box.getValue());
			}
			switch(coverManager.getFormat())
			{
				case PNG:	box.setPng(coverManager.getImageStream());break;
				default:   logger.warn("IMAGE Format "+coverManager.getFormat()+" not handled");
							  return;
			}
			apple.addBox(box);
		}
	}
}