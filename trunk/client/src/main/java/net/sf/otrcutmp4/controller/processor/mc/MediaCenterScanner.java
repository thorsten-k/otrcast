package net.sf.otrcutmp4.controller.processor.mc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.otrcutmp4.controller.facade.OtrMediacenterFacadeBean;
import net.sf.otrcutmp4.controller.tag.reader.Mp4TagReader;
import net.sf.otrcutmp4.factory.ejb.mc.EjbCoverFactory;
import net.sf.otrcutmp4.factory.ejb.mc.EjbStorageFactory;
import net.sf.otrcutmp4.model.OtrCover;
import net.sf.otrcutmp4.model.OtrEpisode;
import net.sf.otrcutmp4.model.OtrMovie;
import net.sf.otrcutmp4.model.OtrSeason;
import net.sf.otrcutmp4.model.OtrSeries;
import net.sf.otrcutmp4.model.OtrStorage;
import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.model.xml.series.Movie;
import net.sf.otrcutmp4.model.xml.series.Season;
import net.sf.otrcutmp4.model.xml.series.Series;
import net.sf.otrcutmp4.model.xml.series.Video;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MediaCenterScanner extends DirectoryWalker<File>
{
	final static Logger logger = LoggerFactory.getLogger(MediaCenterScanner.class);
	
	private Mp4TagReader tagReader;
	
	private EntityManager em;
	private UtilsFacadeBean fUtils;
	private OtrMediacenterFacadeBean<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrCover,OtrStorage> fOtrMc;
	
	private EjbCoverFactory<OtrCover> efCover;
	private EjbStorageFactory<OtrStorage> efStorage;
	
	public MediaCenterScanner(EntityManager em)
	{
		this.em=em;
		tagReader = new Mp4TagReader(true);
		fUtils = new UtilsFacadeBean(em);
		fOtrMc = new OtrMediacenterFacadeBean<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrCover,OtrStorage>(em,fUtils);
		
		efCover=EjbCoverFactory.factory(OtrCover.class);
		efStorage=EjbStorageFactory.factory(OtrStorage.class);
	}
	
	private static IOFileFilter filter()
	{
		IOFileFilter ioFfFile = FileFilterUtils.fileFileFilter();
		IOFileFilter ioFfMp4 = FileFilterUtils.suffixFileFilter("mp4");
		IOFileFilter ioFf1 = FileFilterUtils.and(ioFfFile,ioFfMp4);
		return ioFfMp4;
	}
	
	public void scan(File startDirectory)
	{
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

	@Override
	protected void handleFile(File file, int depth, Collection<File> results)
	{
		if(file.getAbsolutePath().endsWith(".mp4"))
		{
			logger.info("File: "+file.getAbsolutePath());
			try
			{
				em.getTransaction().begin();
				
				OtrStorage storage = efStorage.build(file);
				logger.trace("Storage: "+storage);
				em.persist(storage);
				
				Video video = tagReader.read(file);
				
				if(video.isSetEpisode())
				{
					handleEpisode(video.getEpisode(),storage);
				}
				else if(video.isSetMovie())
				{
					logger.trace("\t"+video.getMovie().getName());
					handleMovie(video.getMovie(),storage);
				}
				em.getTransaction().commit();
			}
			catch (IOException e) {e.printStackTrace();}
		    results.add(file);
		}
	}
	
	private void handleMovie(Movie xmlMovie,OtrStorage storage)
	{
		OtrMovie movie = getMovie(xmlMovie);
		movie.setStorage(storage);
		em.merge(movie);
		
		if(xmlMovie.isSetCover() && movie.getCover()==null)
		{
			OtrCover cover = efCover.build(xmlMovie.getCover());
			em.persist(cover);
			movie.setCover(cover);
			em.merge(movie);
		}
	}
	
	private OtrMovie getMovie(Movie xmlMovie)
	{
		OtrMovie movie;
		try
		{
			movie = fUtils.fByName(OtrMovie.class, xmlMovie.getName());
		}
		catch (UtilsNotFoundException e)
		{
			movie = new OtrMovie();
			movie.setName(xmlMovie.getName());
			movie.setYear(xmlMovie.getYear());
			
	        em.persist(movie);
		}
		return movie;
	}
	
	private void handleEpisode(Episode xmlEpisode,OtrStorage storage)
	{
		OtrSeries series = getSeries(xmlEpisode.getSeason().getSeries());
		OtrSeason season = getSeason(series, xmlEpisode.getSeason());
		OtrEpisode episode = getEpisode(season,xmlEpisode);
		
		
		season.getEpisodes().add(episode);
		
		if(xmlEpisode.isSetCover() && season.getCover()==null)
		{
			OtrCover cover = efCover.build(xmlEpisode.getCover());
			em.persist(cover);
			season.setCover(cover);
			em.merge(season);
		}
	}
	
	private OtrSeries getSeries(Series xmlSeries)
	{
		OtrSeries series;
		try
		{
			series = fUtils.fByName(OtrSeries.class, xmlSeries.getName());
		}
		catch (UtilsNotFoundException e)
		{
			series = new OtrSeries();
			series.setName(xmlSeries.getName());

	        em.persist(series);
		}
		return series;
	}
	
	private OtrSeason getSeason(OtrSeries series, Season xml)
	{
		OtrSeason season=null;
		try
		{
			season = fOtrMc.fSeason(OtrSeason.class, series, xml.getNr());
		}
		catch (UtilsNotFoundException e)
		{
			season = new OtrSeason();
			season.setName(xml.getName());
			season.setNr(xml.getNr());
			season.setSeries(series);

	        em.persist(season);	       
		}
		return season;	
	}
	
	private OtrEpisode getEpisode(OtrSeason season, Episode xml)
	{
		OtrEpisode episode=null;
		try
		{
			episode = fOtrMc.fEpisode(OtrEpisode.class, season, xml.getNr());
		}
		catch (UtilsNotFoundException e)
		{
			episode = new OtrEpisode();
			episode.setName(xml.getName());
			episode.setNr(xml.getNr());
			episode.setSeason(season);

	        em.persist(episode);	       
		}
		return episode;	
	}
}