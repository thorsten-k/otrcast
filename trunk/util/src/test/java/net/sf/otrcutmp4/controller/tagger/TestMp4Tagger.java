package net.sf.otrcutmp4.controller.tagger;

import net.sf.otrcutmp4.controller.factory.xml.series.XmlEpisodeFactory;
import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.test.AbstractUtilTest;
import net.sf.otrcutmp4.test.OtrUtilTstBootstrap;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMp4Tagger extends AbstractUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestMp4Tagger.class);
	
	@Before
	public void init()
	{
		episode = XmlEpisodeFactory.create("mySeries", 1, 2, "myEpisode");
		tagger = new Mp4Tagger();
	}
	
	private Mp4Tagger tagger;
	private Episode episode;
	
	@Test
	public void tag()
	{
		tagger.tagEpisode(null, episode);
	}
	
	public static void main(String args[]) throws Exception
	{
		OtrUtilTstBootstrap.init();
		
		TestMp4Tagger test = new TestMp4Tagger();
		test.init();
		test.tag();
	}
	
 }