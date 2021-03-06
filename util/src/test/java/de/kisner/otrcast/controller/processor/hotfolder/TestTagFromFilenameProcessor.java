package de.kisner.otrcast.controller.processor.hotfolder;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.tag.writer.SeriesTagWriter;
import de.kisner.otrcast.model.xml.video.tv.Episode;
import de.kisner.otrcast.test.AbstractOtrcastTest;

public class TestTagFromFilenameProcessor extends AbstractOtrcastTest
{
	
	final static Logger logger = LoggerFactory.getLogger(TestTagFromFilenameProcessor.class);
	
	@Test @Ignore public void dummy(){}
	
	public static void main(String[] args) throws Exception
	{
		TagFromFilenameProcessor processor = new TagFromFilenameProcessor();
		String testfile = "src/test/resources/hotfolder/ENTER-S01-E01-TESTFILENAMEHERE.mp4";
		File input = new File(testfile);
		Episode episode = processor.createEpisodeFromFilename(input.getName());
		logger.info("Episode: " +episode.toString());
		
		SeriesTagWriter tagger = new SeriesTagWriter();
		tagger.tagEpisode(input, episode, new File("target/new4" +input.getName()));
	}
	
	
}
