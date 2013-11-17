package net.sf.otrcutmp4.controller.tag;

import net.sf.otrcutmp4.controller.cover.FileSystemCoverManager;
import net.sf.otrcutmp4.factory.xml.series.XmlEpisodeFactory;
import net.sf.otrcutmp4.interfaces.controller.CoverManager;
import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.test.AbstractUtilTest;
import net.sf.otrcutmp4.test.OtrUtilTestBootstrap;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestMp4TagWriter extends AbstractUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestMp4TagWriter.class);
	
	public TestMp4TagWriter(){}
	@Test public void dummy(){}
	
	public void init(File dirCovers)
	{
		CoverManager coverManager = new FileSystemCoverManager(dirCovers);
		
		
		episode = XmlEpisodeFactory.build("mySeries", 11, 22, "myEpisode");
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
		Configuration config = OtrUtilTestBootstrap.init();
		
		File dirCovers = new File(config.getString("test.mp4Tagger.cover"));
		TestMp4TagWriter test = new TestMp4TagWriter();
		test.init(dirCovers);
		
		String src = config.getString("test.mp4Tagger.src");
		String dst = config.getString("test.mp4Tagger.dst");
		String fs = SystemUtils.FILE_SEPARATOR;
		
		List<String> files = new ArrayList<String>();
		files.add("AviCutMp4.mp4"); //... Transcoded AVI to MP4 by AviCutMp4
		files.add("OtrCutMp4.mp4"); //... Transcoded by onlinetvrecorder.com
		
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