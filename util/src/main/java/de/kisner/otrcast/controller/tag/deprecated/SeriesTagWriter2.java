package de.kisner.otrcast.controller.tag.deprecated;

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
import com.coremedia.iso.boxes.apple.AppleTvEpisodeNumberBox;
import com.coremedia.iso.boxes.apple.AppleTvSeasonBox;

import de.kisner.otrcast.controller.tag.writer.AbstractTagWriter;
import de.kisner.otrcast.interfaces.controller.CoverManager;
import de.kisner.otrcast.model.xml.series.Episode;
import de.kisner.otrcast.model.xml.series.Season;
import de.kisner.otrcast.model.xml.series.Series;

public class SeriesTagWriter2 extends AbstractTagWriter
{
	final static Logger logger = LoggerFactory.getLogger(SeriesTagWriter2.class);
	
	public SeriesTagWriter2()
	{
		super(null);
	}
	
	public SeriesTagWriter2(CoverManager coverManager)
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
//		JaxbUtil.info(episode);
        Mp4MetadataBalancer mdb = new Mp4MetadataBalancer();

		RandomAccessFile rafR = new RandomAccessFile(srcFile, "r");
		RandomAccessFile rafW = new RandomAccessFile(dstFile, "rw");
		
		FileChannel fcr = rafR.getChannel();
		FileChannel fcw = rafW.getChannel();
		
		IsoFile isoFile = new IsoFile(fcr);
		
		MovieBox moov = Mp4BoxManager.movieBox(isoFile);
		UserDataBox udta = Mp4BoxManager.userDataBox(moov);
		MetaBox meta = Mp4BoxManager.metaBox(udta);
		AppleItemListBox apple = Mp4BoxManager.appleItemListBox(meta);

        mdb.saveInitialState(moov);

		writeEpisodeName(apple, episode);
		writeEpisodeNr(apple, episode);
		writeEpisodeId(apple,episode);
		writeSeason(apple, episode.getSeason());
		writeSeries(apple, episode.getSeason().getSeries());
		writeCover(apple, episode.getSeason());

        Mp4MediaTypeWriter mtw = new Mp4MediaTypeWriter();
        mtw.writeMediaType(apple,Mp4BoxManager.Type.SERIES);
					
        mdb.saveFinalState(moov);

        mdb.correctChunkOffsets(isoFile);
		
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
	
	private void writeEpisodeId(AppleItemListBox apple, Episode episode)
	{
		if(episode.isSetId())
		{
			AppleTvEpisodeNumberBox idBox = null;
			if (apple.getBoxes(AppleTvEpisodeNumberBox.class).isEmpty())
			{
				idBox = new AppleTvEpisodeNumberBox();
			}
			else
			{
				idBox = apple.getBoxes(AppleTvEpisodeNumberBox.class).get(0);
				
			}
			idBox.setValue(episode.getId() +"");
			apple.addBox(idBox);
		}
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
	
	private void writeSeries(AppleItemListBox apple, Series series)
	{
		logger.trace("Writing Series");
		AppleShowBox box = null;
		if(apple.getBoxes(AppleShowBox.class).isEmpty())
		{
			logger.trace(AppleShowBox.class.getSimpleName()+" is empty");
			box = new AppleShowBox();
		}
		else
		{
			box = apple.getBoxes(AppleShowBox.class).get(0);
			logger.trace(AppleShowBox.class.getSimpleName()+" exists: "+box.getValue());
		}
		box.setValue(series.getName());
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