package de.kisner.otrcast.controller.tag;

import java.io.File;
import java.io.IOException;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.cover.FileSystemCoverManager;
import de.kisner.otrcast.controller.tag.deprecated.SeriesTagWriter2;
import de.kisner.otrcast.factory.txt.TxtEpisodeFactory;
import de.kisner.otrcast.factory.xml.series.XmlEpisodeFactory;
import de.kisner.otrcast.interfaces.controller.CoverManager;
import de.kisner.otrcast.model.xml.series.Episode;
import de.kisner.otrcast.test.OtrUtilTestBootstrap;
import de.kisner.otrcast.util.query.io.FileQuery;

public class CliMp4TagWriter
{
	final static Logger logger = LoggerFactory.getLogger(CliMp4TagWriter.class);
	
	public CliMp4TagWriter()
	{
		
	}
	
	public void init(File dirCovers)
	{
		CoverManager coverManager = new FileSystemCoverManager(dirCovers);

		episode = XmlEpisodeFactory.build("mySeries", 11, 22, "myEpisode");
		episode.getSeason().getSeries().setKey("TEST");
		tagger = new SeriesTagWriter2(coverManager);
	}
	
	private SeriesTagWriter2 tagger;
	private Episode episode;
		
	public void tag(File src, File dst) throws IOException
	{
		episode.setId(334455);
		logger.info("Tagging: "+TxtEpisodeFactory.build(episode));
		tagger.tagEpisode(src, episode, dst);
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = OtrUtilTestBootstrap.init();

		File srcMp4 = new File(config.getString("test.mp4Tagger.src"));
		File dstMp4 = new File(config.getString("test.mp4Tagger.dst"));
		File dirCovers = new File(config.getString("test.mp4Tagger.cover"));

        logger.info("src-dir: "+srcMp4);
        logger.info("dst-dir: "+dstMp4);
        logger.info("Covers: "+dirCovers.getAbsolutePath());

        CliMp4TagWriter test = new CliMp4TagWriter();
		test.init(dirCovers);
		
		for(File file : srcMp4.listFiles(FileQuery.mp4FileFilter()))
		{
			test.tag(file,new File(dstMp4,file.getName()));

			// This tests the balancer directly
//			Mp4MetadataBalancer balancer = new Mp4MetadataBalancer();
//			File fSrc = new File(src+fs+file);
//			File fDst = new File(dst+fs+"TMP-"+file);		
//			balancer.writeRandomMetadata(fSrc, dst+fs+"MDB-"+file, "new data",fDst);
		}
	}
 }