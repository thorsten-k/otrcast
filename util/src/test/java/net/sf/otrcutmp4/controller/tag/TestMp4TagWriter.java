package net.sf.otrcutmp4.controller.tag;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.cover.FileSystemCoverManager;
import net.sf.otrcutmp4.controller.tag.writer.SeriesTagWriter;
import net.sf.otrcutmp4.factory.xml.series.XmlEpisodeFactory;
import net.sf.otrcutmp4.interfaces.controller.CoverManager;
import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.test.AbstractUtilTest;
import net.sf.otrcutmp4.test.OtrUtilTestBootstrap;

public class TestMp4TagWriter extends AbstractUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestMp4TagWriter.class);
	
	@Test public void dummy(){}
	
	public TestMp4TagWriter()
	{
		
	}
	
	
	public void init(File dirCovers)
	{
		CoverManager coverManager = new FileSystemCoverManager(dirCovers);

		episode = XmlEpisodeFactory.build("mySeries", 11, 22, "myEpisode");
		episode.getSeason().getSeries().setKey("TEST");
		tagger = new SeriesTagWriter(coverManager);
	}
	
	private SeriesTagWriter tagger;
	private Episode episode;
		
	public void tag(File src, File dst) throws IOException
	{
		episode.setId(334455);
		JaxbUtil.debug(episode);
		tagger.tagEpisode(src, episode, dst);
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = OtrUtilTestBootstrap.init();

        String src = config.getString("test.mp4Tagger.src");
        String dst = config.getString("test.mp4Tagger.dst");
		File dirCovers = new File(config.getString("test.mp4Tagger.cover"));

        logger.info("src-dir: "+src);
        logger.info("dst-dir: "+dst);
        logger.info("Covers: "+dirCovers.getAbsolutePath());

        TestMp4TagWriter test = new TestMp4TagWriter();
		test.init(dirCovers);
		
		
		List<String> files = new ArrayList<String>();
		files.add("by-Cut.mp4");
		files.add("by-OTR.mp4");

		for(String file : files)
		{
			test.tag(new File(src,file),new File(dst,file));
			
			// This tests the balancer directly
//			Mp4MetadataBalancer balancer = new Mp4MetadataBalancer();
//			File fSrc = new File(src+fs+file);
//			File fDst = new File(dst+fs+"TMP-"+file);		
//			balancer.writeRandomMetadata(fSrc, dst+fs+"MDB-"+file, "new data",fDst);
		}
	}
 }