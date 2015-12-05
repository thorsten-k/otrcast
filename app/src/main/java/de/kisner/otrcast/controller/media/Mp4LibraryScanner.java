package de.kisner.otrcast.controller.media;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.commons.io.DirectoryWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.facade.OtrMediacenterFacadeBean;
import de.kisner.otrcast.controller.tag.reader.Mp4TagReader;
import de.kisner.otrcast.factory.ejb.mc.EjbCoverFactory;
import de.kisner.otrcast.factory.ejb.mc.EjbStorageFactory;
import de.kisner.otrcast.factory.ejb.series.EjbEpisodeFactory;
import de.kisner.otrcast.model.ejb.OtrEpisode;
import de.kisner.otrcast.model.ejb.OtrImage;
import de.kisner.otrcast.model.ejb.OtrMovie;
import de.kisner.otrcast.model.ejb.OtrSeason;
import de.kisner.otrcast.model.ejb.OtrSeries;
import de.kisner.otrcast.model.ejb.OtrStorage;
import de.kisner.otrcast.model.xml.series.Episode;
import de.kisner.otrcast.model.xml.series.Movie;
import de.kisner.otrcast.model.xml.series.Video;
import de.kisner.otrcast.util.McJaxb;
import de.kisner.otrcast.util.query.io.FileQuery;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.exlp.util.io.StringUtil;

public class Mp4LibraryScanner extends DirectoryWalker<File>
{
	final static Logger logger = LoggerFactory.getLogger(Mp4LibraryScanner.class);
	
	private Mp4TagReader tagReader;
	
	private EntityManager em;
	private OtrMediacenterFacadeBean<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage> fMc;
	
	private EjbCoverFactory<OtrImage> efCover;
	private EjbStorageFactory<OtrStorage> efStorage;
	
	private Set<OtrStorage> setStorage;
	
	public Mp4LibraryScanner(EntityManager em)
	{
		super(FileQuery.mp4FileFilter(),-1);
		this.em=em;
		tagReader = new Mp4TagReader(true);
		fMc = new OtrMediacenterFacadeBean<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage>(em);
				
		efCover=EjbCoverFactory.factory(OtrImage.class);
		efStorage=EjbStorageFactory.factory(OtrStorage.class);
		
		setStorage = new HashSet<OtrStorage>();
	}
	
	public void scan(File startDirectory)
	{
		List<File> results = new ArrayList<File>();
	    try {walk(startDirectory, results);}
	    catch (IOException e) {e.printStackTrace();}
	    
	    setStorage.addAll(fMc.all(OtrStorage.class));
	    
	    logger.info(StringUtil.stars());
	    logger.info("File scanner will start with "+results.size()+" files and "+setStorage.size()+" DB records");
	    for(File f : results){scanFile(f);}
	    logger.info("File scanner finished with "+fMc.all(OtrStorage.class).size()+" DB records");
	    
	    logger.info(StringUtil.stars());
	    for(OtrStorage storage : fMc.all(OtrStorage.class))
	    {
	    	logger.info(storage.toString());
	    	em.getTransaction().begin();
	    	try
	    	{
				fMc.rm(storage);
				em.getTransaction().commit();
			}
	    	catch (UtilsConstraintViolationException e)
	    	{
				e.printStackTrace();
				em.getTransaction().rollback();
			}
	    }
	    
	    logger.info(StringUtil.stars());
	    logger.info(OtrStorage.class.getSimpleName()+" "+fMc.all(OtrStorage.class).size()+" DB records");
	    logger.info(OtrEpisode.class.getSimpleName()+" "+fMc.all(OtrEpisode.class).size()+" DB records");
	}

	@Override protected boolean handleDirectory(File directory, int depth, Collection<File> results) {return true;}
	@Override protected void handleFile(File file, int depth, Collection<File> results){results.add(file);}
	
	public void scanFile(File file)
	{
		try
		{
			em.getTransaction().begin();
			OtrStorage storage = fMc.fcStorage(OtrStorage.class, file);
			
			Video video = tagReader.read(file);
			if(video.isSetEpisode()){handleEpisode(storage, video.getEpisode(), file);}
//			McJaxb.debug(video);
			em.getTransaction().commit();
		}
		catch (IOException e)
		{
			logger.warn("Cannot scan "+file.getAbsolutePath());
			em.getTransaction().rollback();
		}
		catch (UtilsConstraintViolationException e)
		{
			logger.warn("Cannot scan "+file.getAbsolutePath());
			e.printStackTrace();
		}
	}
	
	private void handleEpisode(OtrStorage storage, Episode xmlEpisode,File file) throws UtilsConstraintViolationException
	{
		OtrEpisode episode = fMc.fcEpisode(OtrSeries.class, OtrSeason.class, OtrEpisode.class, OtrImage.class, xmlEpisode);

        if(episode.getStorage()==null)
        {
            episode.setStorage(storage);
            em.merge(episode);
        }

		if(xmlEpisode.isSetImage() && episode.getSeason().getCover()==null)
		{
			OtrImage cover = efCover.build(xmlEpisode.getImage());
			em.persist(cover);
			episode.getSeason().setCover(cover);
			em.merge(episode.getSeason());
		}
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
}