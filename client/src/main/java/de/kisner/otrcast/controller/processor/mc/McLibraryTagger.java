package de.kisner.otrcast.controller.processor.mc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.lang.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coremedia.iso.boxes.apple.AppleItemListBox;

import de.kisner.otrcast.controller.tag.reader.Mp4TagReader;
import de.kisner.otrcast.controller.tag.util.Mp4BoxManager;
import de.kisner.otrcast.controller.tag.writer.SeriesTagWriter;
import de.kisner.otrcast.factory.io.IoFileFactory;
import de.kisner.otrcast.interfaces.rest.OtrSeriesRest;
import de.kisner.otrcast.model.json.JsonOtrIdentifier;
import de.kisner.otrcast.model.xml.series.Episode;
import de.kisner.otrcast.model.xml.series.Video;
import de.kisner.otrcast.util.query.io.FileQuery;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.monitor.BucketSizeCounter;
import net.sf.ahtutils.monitor.ProcessingEventCounter;
import net.sf.ahtutils.monitor.ProcessingTimeTracker;
import net.sf.exlp.util.io.StringUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class McLibraryTagger extends DirectoryWalker<File>
{
	final static Logger logger = LoggerFactory.getLogger(McLibraryTagger.class);
	
	private enum CodeTotal {total,withId,withoutId}
	
	private ProcessingEventCounter pecMediaType,pecTotal;
	private BucketSizeCounter bsc;
	
	private OtrSeriesRest rest;
	
	private Mp4TagReader tagReader;
	private SeriesTagWriter tagWriter;
	private IoFileFactory iofTmp,iofBackup;
	
	public McLibraryTagger(OtrSeriesRest rest, File fTmp, File fBackup)
	{
		super(FileQuery.mp4FileFilter(),-1);
		logger.info(StringUtil.stars());
		
		this.rest=rest;
		tagReader = new Mp4TagReader(false);
		tagWriter = new SeriesTagWriter();
		
		logger.info("Tmp Directory: "+fTmp.getAbsolutePath());
		iofTmp = new IoFileFactory(fTmp);
		
		if(fBackup!=null)
		{
			logger.info("Backup Directory: "+fBackup.getAbsolutePath());
			iofBackup = new IoFileFactory(fBackup);
		}
		else
		{
			logger.warn("Backups are deactivated!!");
		}
		logger.info(StringUtil.stars());
		
		bsc = new BucketSizeCounter("Files");
		pecMediaType = new ProcessingEventCounter("MediaType");
		pecTotal = new ProcessingEventCounter("Files");
	}
	
	public void scan(File startDirectory)
	{
		logger.info("Scanning "+startDirectory);
		
		ProcessingTimeTracker ptt = new ProcessingTimeTracker(true);
		
		List<File> results = new ArrayList<File>();
	    try
	    {
			walk(startDirectory, results);
		}
	    catch (IOException e) {e.printStackTrace();}
	    
	    
	    logger.info("Processing time: "+ptt.toTotalPeriod());
	    pecTotal.debug();
	    pecMediaType.debug();
	    bsc.debugAsFileSize();
	}
	
	@Override protected boolean handleDirectory(File directory, int depth, Collection<File> results)
	{
		if(pecTotal.events(CodeTotal.total)>100){return false;}
		return true;
	}

	@Override protected void handleFile(File file, int depth, Collection<File> results)
	{
		logger.trace("File :"+file);
		bsc.add(CodeTotal.total,file.length());
		pecTotal.add(CodeTotal.total);
		
		Mp4BoxManager.Type type = Mp4BoxManager.Type.UNKNOWN;
		JsonOtrIdentifier vidIdentifier = null;
		
		try
		{
			tagReader.readMp4Boxes(file);
			
			Mp4BoxManager.Type typeOtr = tagReader.getTypeFromOtrBox();
			Mp4BoxManager.Type typeApple = tagReader.getTypeFromAppleBox();
			Mp4BoxManager.Type typeFile = tagReader.geTypeFromFilePath();
			
			
			logger.info("Type-OTRC: "+typeOtr);
			logger.info("Type-APPL: "+typeApple);
			logger.info("Type-FILE: "+typeFile);
			
			if(type.equals(Mp4BoxManager.Type.UNKNOWN)) {pecMediaType.add("mediaTypeMissing");};
		}
		catch (IOException e) {e.printStackTrace();}
		finally
		{
			try {tagReader.closeFile();} catch (IOException e) {logger.error(e.getMessage());}
		}
		
		
		pecTotal.add(CodeTotal.withId);
		pecTotal.add(CodeTotal.withoutId);
		
		
		
		boolean withoutMediaType = type.equals(Mp4BoxManager.Type.UNKNOWN);
		boolean withoutIdentifier = (vidIdentifier==null);
		
		if(withoutMediaType || withoutIdentifier)
		{
			try {tagFile(file);}
			catch (IOException e) {logger.error(e.getMessage());}
		}
	}
	
	private void tagFile(File file) throws IOException
	{
		logger.info("Tagging "+file.getAbsolutePath());
		Video video = tagReader.read(file);
		JaxbUtil.trace(video);
		if(video.isSetEpisode() && !video.getEpisode().isSetId())
		{
//			processed = handleEpisode(video.getEpisode(), file);
		}
	}
	
	private boolean handleEpisode(Episode episodeRequest, File fSrc)
	{
		JaxbUtil.info(episodeRequest);
/*		Otr otr = rest.episodeInfo(episodeRequest);
		if(otr.getEpisode().size()==1)
		{
			Episode eInfo = otr.getEpisode().get(0);
			JaxbUtil.info(eInfo);
			try
			{
				
				File fBackup = fFile.build(eInfo);
				FileUtils.moveFile(fSrc, fBackup);
				
				tagWriter.tagEpisode(fBackup, eInfo, fSrc);
			}
			catch (IOException e){e.printStackTrace();}
			
			return true;
		}
*/		
		return false;
	}
}