package net.sf.otrcutmp4.controller.tagger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.otrcutmp4.controller.cover.FileSystemCoverManager;
import net.sf.otrcutmp4.controller.factory.xml.series.XmlEpisodeFactory;
import net.sf.otrcutmp4.interfaces.controller.CoverManager;
import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.test.AbstractUtilTest;
import net.sf.otrcutmp4.test.OtrUtilTstBootstrap;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMp4Tagger extends AbstractUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestMp4Tagger.class);
	
	public TestMp4Tagger(File dirCovers)
	{
		CoverManager coverManager = new FileSystemCoverManager(dirCovers);
		
		episode = XmlEpisodeFactory.create("mySeries", 11, 22, "myEpisode");
		episode.getSeason().getSeries().setKey("TEST");
		tagger = new Mp4Tagger(coverManager);
	}
	
	private Mp4Tagger tagger;
	private Episode episode;
		
	public void tag(String src, String dst) throws IOException
	{
		tagger.tagEpisode(src, episode, dst);
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = OtrUtilTstBootstrap.init();
		
		File dirCovers = new File(config.getString("test.mp4Tagger.cover"));
		TestMp4Tagger test = new TestMp4Tagger(dirCovers);
		
		
		String src = config.getString("test.mp4Tagger.src");
		String dst = config.getString("test.mp4Tagger.dst");
		String fs = SystemUtils.FILE_SEPARATOR;
		
		List<String> files = new ArrayList<String>();
		files.add("AviCutMp4.mp4"); //... Transcoded AVI to MP4 by AviCutMp4
//		files.add("OtrCutMp4.mp4"); //... Transcoded by onlinetvrecorder.com
		
		for(String file : files)
		{
			test.tag(src+fs+file,dst+fs+file);
			
// This tests the balancer directly
//			Mp4MetadataBalancer balancer = new Mp4MetadataBalancer();
//			File fSrc = new File(src+fs+file);
//			File fDst = new File(dst+fs+"TMP-"+file);		
//			balancer.writeRandomMetadata(fSrc, dst+fs+"MDB-"+file, "new data",fDst);
		}
	}
 }