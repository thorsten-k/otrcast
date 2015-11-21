package de.kisner.otrcast.controller.tag;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.cover.FileSystemCoverManager;
import de.kisner.otrcast.controller.tag.writer.MovieTagWriter;
import de.kisner.otrcast.controller.tag.writer.SeriesTagWriter;
import de.kisner.otrcast.factory.txt.TxtEpisodeFactory;
import de.kisner.otrcast.factory.xml.series.XmlEpisodeFactory;
import de.kisner.otrcast.interfaces.controller.CoverManager;
import de.kisner.otrcast.interfaces.controller.TestPropertyKeys;
import de.kisner.otrcast.model.xml.series.Episode;
import de.kisner.otrcast.model.xml.series.Movie;
import de.kisner.otrcast.test.OtrUtilTestBootstrap;
import de.kisner.otrcast.util.query.io.FileQuery;

public class CliMp4TagWriter
{
	final static Logger logger = LoggerFactory.getLogger(CliMp4TagWriter.class);
	
	private SeriesTagWriter seriesTagger;
	private MovieTagWriter movieTagger;
	private Episode episode;
	private Movie movie;
	
	public CliMp4TagWriter()
	{
		
	}
	
	public void init(File dirCovers)
	{
		CoverManager coverManager = new FileSystemCoverManager(dirCovers);

		episode = XmlEpisodeFactory.build("mySeries", 11, 22, "myEpisode");
		episode.setId(12345);
		episode.getSeason().getSeries().setKey("TEST");
		
		movie = new Movie();
		movie.setName("myMovie");
		
		seriesTagger = new SeriesTagWriter(coverManager);
		movieTagger = new MovieTagWriter();
	}
		
	public void series(File src, File dst) throws IOException
	{
		logger.info("Tagging: "+TxtEpisodeFactory.build(episode));
		seriesTagger.tagEpisode(src, episode, dst);
	}
	
	public void movie(File src, File dst) throws IOException
	{
		logger.info("Tagging: "+TxtEpisodeFactory.build(episode));
		movieTagger.tagMovie(src, movie, dst);
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = OtrUtilTestBootstrap.init();

		File srcMp4 = new File(config.getString(TestPropertyKeys.dirTaggerSrc));
		File dstMp4 = new File(config.getString(TestPropertyKeys.dirTaggerDst));
		File dirCovers = new File(config.getString(TestPropertyKeys.dirCover));

        logger.info("src-dir: "+srcMp4+" ("+TestPropertyKeys.dirTaggerSrc+")");
        logger.info("dst-dir: "+dstMp4+" ("+TestPropertyKeys.dirTaggerDst+")");
        logger.info("Covers: "+dirCovers.getAbsolutePath());

        CliMp4TagWriter test = new CliMp4TagWriter();
		test.init(dirCovers);
		
		File[] files = srcMp4.listFiles(FileQuery.mp4FileFilter());
		logger.info(files.length+" files for tagging found");
		
		for(File file : files)
		{
			File fTarget = new File(dstMp4,file.getName());
			if(fTarget.exists()){fTarget.delete();}
			test.series(file,fTarget);
//			test.movie(file,new File(dstMp4,file.getName()));
		}
	}
 }