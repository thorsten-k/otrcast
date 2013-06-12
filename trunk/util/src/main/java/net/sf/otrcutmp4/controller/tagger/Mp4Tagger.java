package net.sf.otrcutmp4.controller.tagger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.model.xml.series.Season;
import net.sf.otrcutmp4.model.xml.series.Series;

import org.apache.commons.io.FileUtils;
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

public class Mp4Tagger
{
	final static Logger logger = LoggerFactory.getLogger(Mp4Tagger.class);
		
	private File dirCovers;
	
	public Mp4Tagger(){}
	public Mp4Tagger(File dirCovers)
	{
		this.dirCovers=dirCovers;
	}
	
	public void tagEpisode(String srcFileName, Episode episode, String dstFileName) throws IOException
	{
		File srcFile = new File(srcFileName);
		File dstFile = new File(dstFileName);
		
		logger.info("srcFile: "+srcFile.getAbsolutePath());
		logger.info("dstFile: "+dstFile.getAbsolutePath());
		
		tagEpisode(srcFile, episode, dstFile);
	}
	@SuppressWarnings("resource")
	public void tagEpisode(File srcFile, Episode episode, File dstFile) throws IOException
	{
//		JaxbUtil.info(episode);
		FileChannel fcr = new RandomAccessFile(srcFile, "r").getChannel();
		FileChannel fcw = new RandomAccessFile(dstFile, "rw").getChannel();
		
		//TODO Write a temporary file and in the end, remove original and rename the temp file to replace the original
		//tk: This should be done outside of this class!
		
		IsoFile isoFile = new IsoFile(fcr);
		
		MovieBox moov = isoFile.getBoxes(MovieBox.class).get(0);
		UserDataBox udta = null;
		MetaBox meta = null;
		AppleItemListBox apple = null;
		try
		{
			udta = moov.getBoxes(UserDataBox.class).get(0);
			meta = udta.getBoxes(MetaBox.class).get(0);
			apple = meta.getBoxes(AppleItemListBox.class).get(0);
		}
		catch (Exception e)
		{
			logger.info("Exception occured: " +e.getMessage());
			apple = new AppleItemListBox();
			meta = new MetaBox();
			udta = new UserDataBox();
			
			meta.addBox(apple);
			udta.addBox(meta);
			moov.addBox(udta);
			
		}
		
		
		
		long sizeBefore = udta.getSize();
		
		//TODO Write a method to make this code DRY-compatible!
		
		writeEpisodeName(apple, episode);
		writeEpisodeNr(apple, episode);
		writeSeason(apple, episode.getSeason());
		writeSeries(apple, episode.getSeason().getSeries());
//		writeCover(apple, episode.getSeason());
					
		Mp4MetadataBalancer mdb = new Mp4MetadataBalancer();
		boolean needsCorrection = mdb.needsOffsetCorrection(isoFile);
		
		long sizeAfter = udta.getSize();
		logger.debug(UserDataBox.class.getSimpleName()+" "+sizeBefore);
		logger.debug(UserDataBox.class.getSimpleName()+" "+sizeAfter);
		logger.debug(UserDataBox.class.getSimpleName()+" needs corrction:"+needsCorrection);
		if (needsCorrection)
        {
            mdb.correctChunkOffsets(isoFile, sizeAfter - sizeBefore);
        }
		
		isoFile.getBox(fcw);
		fcw.force(true);fcw.close();
		fcr.close();
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
			titleBox = (AppleTrackTitleBox) apple.getBoxes(AppleTrackTitleBox.class).get(0);
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
			episodeBox = (AppleTvEpisodeBox) apple.getBoxes(AppleTvEpisodeBox.class).get(0);
			
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
			seasonBox = (AppleTvSeasonBox) apple.getBoxes(AppleTvSeasonBox.class).get(0);
		}
		seasonBox.setValue(season.getNr()+"");
		apple.addBox(seasonBox);
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
			box = (AppleShowBox) apple.getBoxes(AppleShowBox.class).get(0);
			logger.debug(AppleShowBox.class.getSimpleName()+" exists: "+box.getValue());
		}
		box.setValue(series.getName());
		apple.addBox(box);
	}
	
	private void writeCover(AppleItemListBox apple, Season season) throws IOException
	{
		if(dirCovers==null || !season.getSeries().isSetKey()){return;}
		
		File dirSeason = new File(dirCovers,season.getSeries().getKey());
		if(!dirSeason.exists()){return;}
		
		File fCover = new File(dirSeason,season.getNr()+".png");
		if(!fCover.exists()){return;}
		
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
		 
		byte[] pngData = FileUtils.readFileToByteArray(fCover);
		box.setPng(pngData);
		apple.addBox(box);
	}
}