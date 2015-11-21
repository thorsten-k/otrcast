package de.kisner.otrcast.controller.processor.mc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.DirectoryWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.tag.reader.Mp4TagReader;
import de.kisner.otrcast.controller.tag.util.Mp4BoxManager;
import de.kisner.otrcast.controller.tag.writer.SeriesTagWriter;
import de.kisner.otrcast.factory.io.IoFileFactory;
import de.kisner.otrcast.factory.txt.TxtEpisodeFactory;
import de.kisner.otrcast.factory.xml.series.XmlVideosFactory;
import de.kisner.otrcast.interfaces.controller.CoverManager;
import de.kisner.otrcast.interfaces.rest.OtrSeriesRest;
import de.kisner.otrcast.model.xml.container.Otr;
import de.kisner.otrcast.model.xml.series.Episode;
import de.kisner.otrcast.model.xml.series.Video;
import de.kisner.otrcast.model.xml.series.Videos;
import de.kisner.otrcast.util.query.io.FileQuery;
import net.sf.ahtutils.monitor.BucketSizeCounter;
import net.sf.ahtutils.monitor.ProcessingEventCounter;
import net.sf.ahtutils.monitor.ProcessingTimeTracker;
import net.sf.exlp.shell.io.ConsoleInputChoice;
import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.exlp.util.io.StringUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class McLibraryTagger extends DirectoryWalker<File>
{
	final static Logger logger = LoggerFactory.getLogger(McLibraryTagger.class);
	
	private enum CodeTotal {total,withId,withoutId}
	
	protected CoverManager coverManager;
	private RelativePathFactory rpf;
	
	private ProcessingEventCounter pecMediaType,pecTotal;
	private BucketSizeCounter bsc;
	
	private OtrSeriesRest rest;
	
	private Mp4TagReader tagReader;
	private SeriesTagWriter tagWriter;
	private IoFileFactory iofTmp,iofBackup;
	
	private List<File> results;
	
	public McLibraryTagger(File fTmp, File fBackup)
	{
		super(FileQuery.mp4FileFilter(),-1);
		logger.info(StringUtil.stars());
		
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
		
		bsc = new BucketSizeCounter("Files");
		pecMediaType = new ProcessingEventCounter("MediaType");
		pecTotal = new ProcessingEventCounter("Files");
	}
		
	public void scan(File baseDirectory)
	{
		rpf = new RelativePathFactory(baseDirectory);
		logger.info("Scanning "+baseDirectory);
		
		ProcessingTimeTracker ptt = new ProcessingTimeTracker(true);
		
		logger.info(StringUtil.stars());
		results = new ArrayList<File>();
		try{walk(baseDirectory, results);}
		catch (IOException e) {e.printStackTrace();}
		logger.info("Directoy Listing complete in "+ptt.toTotalTime()+" with "+results.size()+" files");
		
		logger.info(StringUtil.stars());
		logger.info("Processing time: "+ptt.toTotalPeriod());
		pecTotal.debug();
		pecMediaType.debug();
		bsc.debugAsFileSize();
	}
	
	@Override protected boolean handleDirectory(File directory, int depth, Collection<File> results)
	{
//		if(pecTotal.events(CodeTotal.total)>100){return false;}
		return true;
	}

	@Override protected void handleFile(File file, int depth, Collection<File> results){results.add(file);}
	
	public void saveToXml(File fXml) throws IOException
	{
		Videos videos = XmlVideosFactory.build();
		for(File f : results)
		{
			tagReader.readMp4Boxes(f);
			Video video = tagReader.read();
			if(video.isSetEpisode())
			{
				videos.getVideo().add(video);
			}
//			if(videos.getVideo().size()>5){break;}
		}
//		JaxbUtil.info(videos);
		JaxbUtil.save(fXml, videos, true);
	}
	
	private void handle(File file)
	{
		logger.trace("File :"+file);
		bsc.add(CodeTotal.total,file.length());
		pecTotal.add(CodeTotal.total);
		
		try
		{
			tagReader.readMp4Boxes(file);
			Video video = tagReader.read();
			if(video.isSetEpisode())
			{
				pecMediaType.add(Mp4BoxManager.Type.SERIES);
				handleEpisode(file, video.getEpisode());
			}
			else if(video.isSetMovie())
			{
				pecMediaType.add(Mp4BoxManager.Type.MOVIE);
			}
			else
			{
				pecMediaType.add(Mp4BoxManager.Type.UNKNOWN);
			}
		}
		catch (IOException e) {e.printStackTrace();}
		finally {try {tagReader.closeFile();} catch (IOException e) {logger.error(e.getMessage());}}
		
		pecTotal.add(CodeTotal.withId);
		pecTotal.add(CodeTotal.withoutId);
	}
	
	private boolean handleEpisode(File fSrc, Episode episodeRequest)
	{
		logger.trace("Handling "+rpf.relativate(fSrc));
		logger.trace("Resolving: "+TxtEpisodeFactory.build(episodeRequest,true));
		JaxbUtil.trace(episodeRequest);
		Otr otr = rest.resolveEpisode(episodeRequest);
		
		Episode episodeSelected = null;
		if(otr.getEpisode().size()==0)
		{
			logger.warn("Nothing found for "+TxtEpisodeFactory.build(episodeRequest));
			return false;
		}
		else if(otr.getEpisode().size()==1){episodeSelected = otr.getEpisode().get(0);}
		else if(otr.getEpisode().size()>1)
		{
			Map<Episode,String> map = new Hashtable<Episode,String>();
			for(Episode e : otr.getEpisode()){map.put(e, TxtEpisodeFactory.build(e));}
			System.out.println("Select Episode for "+TxtEpisodeFactory.build(episodeRequest));
			episodeSelected = ConsoleInputChoice.getListItemForChoice(otr.getEpisode(),map);
		}
		
		logger.trace("Tagging: "+TxtEpisodeFactory.build(episodeSelected,true));
		if(coverManager!=null)
		{
			boolean coverAvailable = coverManager.isAvailable(episodeSelected.getSeason());
			logger.trace("\tCover available: "+coverAvailable);
		}
		
/*		
		if(iofBackup!=null)
		{
			File fBackup = iofBackup.build(episodeSelected);
			try
			{
				FileUtils.copyFile(fSrc, fBackup);
			}
			catch (IOException e)
			{
				logger.warn("Cannot create backup-file "+fBackup.getAbsolutePath());
				return false;
			}
		}
		
		try
		{
			File fTmp = iofTmp.build(episodeSelected);
			FileUtils.moveFile(fSrc,fTmp);
			tagWriter.tagEpisode(fTmp, episodeSelected, fSrc);
			fTmp.delete();
		}
		catch (IOException e) {e.printStackTrace();}
*/		
		return false;
	}
	
	public CoverManager getCoverManager() {return coverManager;}
	public void setCoverManager(CoverManager coverManager)
	{
		this.coverManager = coverManager;
		tagWriter.setCoverManager(coverManager);
	}
}