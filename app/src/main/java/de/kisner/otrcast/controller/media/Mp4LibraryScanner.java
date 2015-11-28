package de.kisner.otrcast.controller.media;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.io.DirectoryWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.facade.OtrMediacenterFacadeBean;
import de.kisner.otrcast.controller.tag.reader.Mp4TagReader;
import de.kisner.otrcast.factory.ejb.mc.EjbCoverFactory;
import de.kisner.otrcast.factory.ejb.mc.EjbStorageFactory;
import de.kisner.otrcast.model.ejb.OtrEpisode;
import de.kisner.otrcast.model.ejb.OtrImage;
import de.kisner.otrcast.model.ejb.OtrMovie;
import de.kisner.otrcast.model.ejb.OtrSeason;
import de.kisner.otrcast.model.ejb.OtrSeries;
import de.kisner.otrcast.model.ejb.OtrStorage;
import de.kisner.otrcast.model.xml.series.Episode;
import de.kisner.otrcast.model.xml.series.Movie;
import de.kisner.otrcast.model.xml.series.Season;
import de.kisner.otrcast.model.xml.series.Video;
import de.kisner.otrcast.util.McJaxb;
import de.kisner.otrcast.util.query.io.FileQuery;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.monitor.ProcessingTimeTracker;
import net.sf.exlp.util.io.HashUtil;

public class Mp4LibraryScanner extends DirectoryWalker<File>
{
	final static Logger logger = LoggerFactory.getLogger(Mp4LibraryScanner.class);
	
	private Mp4TagReader tagReader;
	
	private EntityManager em;
	private OtrMediacenterFacadeBean<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage> fMc;
	
	private EjbCoverFactory<OtrImage> efCover;
	private EjbStorageFactory<OtrStorage> efStorage;
	
	public Mp4LibraryScanner(EntityManager em)
	{
		super(FileQuery.mp4FileFilter(),-1);
		this.em=em;
		tagReader = new Mp4TagReader(true);
		fMc = new OtrMediacenterFacadeBean<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage>(em);
		
		efCover=EjbCoverFactory.factory(OtrImage.class);
		efStorage=EjbStorageFactory.factory(OtrStorage.class);
	}
	
	public void scan(File startDirectory)
	{
		ProcessingTimeTracker ptt = new ProcessingTimeTracker(true);
		List<File> results = new ArrayList<File>();
	    try
	    {
			walk(startDirectory, results);
		}
	    catch (IOException e) {e.printStackTrace();}
	     
	    logger.info("Processed "+results.size()+" files in "+ptt.toTotalTime());
	}

	@Override protected boolean handleDirectory(File directory, int depth, Collection<File> results) {return true;}
	
	@Override protected void handleFile(File file, int depth, Collection<File> results)
	{
		boolean inspectFile = false;
        try
        {
        	boolean removeStorage = false;
            OtrStorage storage = fMc.fByName(OtrStorage.class,file.getAbsolutePath());
            Date modified = new Date(file.lastModified());
            if(!modified.equals(storage.getRecord()))
            {
            	try{if(!HashUtil.hash(file).equals(storage.getHash())){removeStorage=true;}}
            	catch (IOException e) {removeStorage=true;}
            }
            if(removeStorage)
            {
            	logger.info("Will remove storage");
            }
        }
        catch (UtilsNotFoundException e){inspectFile=true;}

        logger.info("File (inspect="+inspectFile+"): "+file.getAbsolutePath());
        if(inspectFile && false)
        {
            try
            {
                em.getTransaction().begin();
                Video video = tagReader.read(file);
                McJaxb.debug(video);

                if(video.isSetEpisode()){handleEpisode(video.getEpisode(),file);}
//                   else if(video.isSetMovie()){handleMovie(video.getMovie(),file);}
                em.getTransaction().commit();
            }
            catch (IOException e2) {e2.printStackTrace();}
        }

	    results.add(file);
	}
	
	private void handleMovie(Movie xmlMovie,File file)
	{
		OtrMovie movie = getMovie(xmlMovie);

        if(movie.getStorage()==null)
        {
            OtrStorage storage = efStorage.build(file);
            movie.setStorage(storage);
            em.merge(movie);
        }
		
		if(xmlMovie.isSetImage() && movie.getCover()==null)
		{
			OtrImage cover = efCover.build(xmlMovie.getImage());
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
			movie = fMc.fByName(OtrMovie.class, xmlMovie.getName());
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
	
	private void handleEpisode(Episode xmlEpisode,File file)
	{
		OtrSeries series = fMc.fcSeries(OtrSeries.class,xmlEpisode.getSeason().getSeries());
		OtrSeason season = getSeason(series, xmlEpisode.getSeason());
		OtrEpisode episode = getEpisode(season,xmlEpisode);

		season.getEpisodes().add(episode);

        if(episode.getStorage()==null)
        {
            OtrStorage storage = efStorage.build(file);
            em.persist(storage);
            episode.setStorage(storage);
            em.merge(episode);
        }

		if(xmlEpisode.isSetImage() && season.getCover()==null)
		{
			OtrImage cover = efCover.build(xmlEpisode.getImage());
			em.persist(cover);
			season.setCover(cover);
			em.merge(season);
		}
	}
		
	private OtrSeason getSeason(OtrSeries series, Season xml)
	{
		OtrSeason season=null;
		try{season = fMc.fSeason(OtrSeason.class, series, xml.getNr());}
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
		try{episode = fMc.fEpisode(OtrEpisode.class, season, xml.getNr());}
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