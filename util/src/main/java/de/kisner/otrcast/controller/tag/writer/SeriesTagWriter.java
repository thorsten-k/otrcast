package de.kisner.otrcast.controller.tag.writer;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

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
import com.coremedia.iso.boxes.apple.AppleTvSeasonBox;

import de.kisner.otrcast.controller.tag.util.Mp4BoxManager;
import de.kisner.otrcast.controller.tag.util.Mp4MetadataBalancer;
import de.kisner.otrcast.interfaces.controller.CoverManager;
import de.kisner.otrcast.model.xml.series.Episode;
import de.kisner.otrcast.model.xml.series.Season;

public class SeriesTagWriter extends AbstractTagWriter
{
	final static Logger logger = LoggerFactory.getLogger(SeriesTagWriter.class);

	public SeriesTagWriter()
	{
		super(null);
	}
	public SeriesTagWriter(CoverManager coverManager)
	{
		super(coverManager);
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
		
		writeEpisodeName(apple, episode.getName());
		writeEpisodeNr(apple, new Long(episode.getNr()).intValue());
		writeSeasonNr(apple, new Long(episode.getSeason().getNr()).intValue());
		writeSeries(apple, episode.getSeason().getSeries().getName());
		writeMediaType(apple, "10");
		writeCover(apple, episode.getSeason());
					
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
	
	private void writeEpisodeName(AppleItemListBox apple, String name)
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
		titleBox.setValue(name);
		apple.addBox(titleBox);
	}
	
	private void writeEpisodeNr(AppleItemListBox apple, int nr)
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
		episodeBox.setValue(nr+"");
		apple.addBox(episodeBox);
	}
		
	private void writeSeasonNr(AppleItemListBox apple, int season)
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
		seasonBox.setValue(season+"");
		apple.addBox(seasonBox);
	}
		
	private void writeSeries(AppleItemListBox apple, String show)
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
			box = (AppleShowBox) apple.getBoxes(AppleShowBox.class).get(0);
			logger.debug(AppleShowBox.class.getSimpleName()+" exists: "+box.getValue());
		}
		box.setValue(show);
		apple.addBox(box);
	}
	
	private void writeCover(AppleItemListBox apple, Season season) throws IOException
	{
		boolean abortNoCoverManager = (coverManager==null);
		boolean abortNoSeriesKey = !season.getSeries().isSetKey();
		
		logger.trace("Abort because no coverManager?"+abortNoCoverManager);
		logger.trace("Abort because no series@key?"+abortNoSeriesKey);
		
		if(abortNoCoverManager || abortNoSeriesKey){return;}
		
		boolean coverAvailable = coverManager.isAvailable(season);
		logger.info("Cover available: "+coverAvailable);
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
				case JPEG:	box.setJpg(coverManager.getImageStream());break;
				default:    logger.warn("IMAGE Format "+coverManager.getFormat()+" not handled");return;
			}
			apple.addBox(box);
		}
	}
}