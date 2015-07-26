package net.sf.otrcutmp4.controller.tag;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.tag.reader.Mp4TagReader;
import net.sf.otrcutmp4.controller.tag.writer.SeriesTagWriter;
import net.sf.otrcutmp4.factory.io.IoFileFactory;
import net.sf.otrcutmp4.interfaces.rest.OtrSeriesRest;
import net.sf.otrcutmp4.model.xml.container.Otr;
import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.model.xml.series.Video;
import net.sf.otrcutmp4.util.query.io.FileQuery;

public class Mp4LibraryTagger extends DirectoryWalker<File>
{
	final static Logger logger = LoggerFactory.getLogger(Mp4LibraryTagger.class);
	
	private OtrSeriesRest rest;
	
	private Mp4TagReader tagReader;
	private SeriesTagWriter tagWriter;
	private IoFileFactory fFile;
	
	public Mp4LibraryTagger(OtrSeriesRest rest, File fBackup)
	{
		super(FileQuery.mp4FileFilter(),-1);
		this.rest=rest;
		tagReader = new Mp4TagReader(false);
		tagWriter = new SeriesTagWriter();
		fFile = new IoFileFactory(fBackup);
	}
	
	public void scan(File startDirectory)
	{
		logger.info("Scanning "+startDirectory);
		DateTime startDate = new DateTime();
		
		List<File> results = new ArrayList<File>();
	    try
	    {
			walk(startDirectory, results);
		}
	    catch (IOException e) {e.printStackTrace();}
	    
	    Period period = new Period(startDate, new DateTime());
	    
	    logger.info("Processed "+results.size()+" files in "+PeriodFormat.getDefault().print(period));
	}
	
	@Override protected boolean handleDirectory(File directory, int depth, Collection<File> results) {return true;}

	@Override protected void handleFile(File file, int depth, Collection<File> results)
	{
		logger.trace("File :"+file);
		boolean processed = false;
		try
		{
			Video video = tagReader.read(file);
			if(video.isSetEpisode() && !video.getEpisode().isSetId())
			{
				processed = handleEpisode(video.getEpisode(), file);
			}
		}
		catch (IOException e) {e.printStackTrace();}
		if(processed){results.add(file);}
	}
	
	private boolean handleEpisode(Episode episodeRequest, File fSrc)
	{
		JaxbUtil.info(episodeRequest);
		Otr otr = rest.episodeInfo(episodeRequest);
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
		
		return false;
	}
}