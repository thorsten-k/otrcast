package net.sf.otrcutmp4.controller.tagger;

import java.io.File;

import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.test.AbstractUtilTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestEpisodeFromFilename extends AbstractUtilTest{
	
	final static Logger logger = LoggerFactory.getLogger(TestEpisodeFromFilename.class);
	
	public static void main(String[] args) throws Exception
	{
		ProcessTagging processor = new ProcessTagging();
		String testfile = "src/test/resources/hotfolder/ENTER-S01-E01-TESTFILENAMEHERE.mp4";
		File input = new File(testfile);
		Episode episode = processor.createEpisodeFromFilename(input.getName());
		logger.info("Episode: " +episode.toString());
		
		Mp4Tagger tagger = new Mp4Tagger();
		tagger.tagEpisode(input, episode, new File("target/new4" +input.getName()));
	}
	
	
}
