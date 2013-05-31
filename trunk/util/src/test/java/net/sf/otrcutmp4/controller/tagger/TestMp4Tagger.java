package net.sf.otrcutmp4.controller.tagger;

import java.io.IOException;

import net.sf.otrcutmp4.controller.factory.xml.series.XmlEpisodeFactory;
import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.test.AbstractUtilTest;
import net.sf.otrcutmp4.test.OtrUtilTstBootstrap;

import org.apache.commons.configuration.Configuration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMp4Tagger extends AbstractUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestMp4Tagger.class);
	
	@Before
	public void init()
	{
		episode = XmlEpisodeFactory.create("mySeries", 11, 22, "myEpisode");
		tagger = new Mp4Tagger();
	}
	
	private Mp4Tagger tagger;
	private Episode episode;
	
	@Ignore
	@Test
	public void tag() throws IOException
	{
		String src = "/Volumes/ramdisk/test.mp4";
		String dst = "/Volumes/ramdisk/tagged.mp4";
		tag(src,dst);
	}
	
	public void tag(String src, String dst) throws IOException
	{
		tagger.tagEpisode(src, episode, dst);
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = OtrUtilTstBootstrap.init();
		
		TestMp4Tagger test = new TestMp4Tagger();
		
		test.init();
		test.tag(config.getString("test.mp4Tagger.src.avicut"),config.getString("test.mp4Tagger.dst.avicut"));
		
		test.init();
		test.tag(config.getString("test.mp4Tagger.src.otrcut"),config.getString("test.mp4Tagger.dst.otrcut"));
	}
 }