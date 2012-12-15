package net.sf.otrcutmp4.controller.batch.video;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.AviToMp4.Profile;
import net.sf.otrcutmp4.controller.batch.AbstractBatchTest;
import net.sf.otrcutmp4.controller.exception.OtrConfigurationException;
import net.sf.otrcutmp4.controller.exception.OtrInternalErrorException;
import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.util.TestOtrConfig;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestVideoCutter extends AbstractBatchTest
{ 
	final static Logger logger = LoggerFactory.getLogger(TestVideoCutter.class);
	
	private VideoCutter videoCutter;
	
	@Before
	public void init() throws IOException
	{		
		TestOtrConfig tC = TestOtrConfig.factory();
		videoCutter = new  VideoCutter(tC.getOtrConfig(),Profile.P0);
	}
	
	@Test
	public void testInit()
	{
		Assert.assertTrue(video.isSetVideoFiles());
		Assert.assertTrue(video.getVideoFiles().isSetVideoFile());
		for(VideoFile vf : video.getVideoFiles().getVideoFile())
		{
			Assert.assertTrue(vf.isSetCutListsSelected());
			Assert.assertTrue(vf.getCutListsSelected().isSetCutList());
			for(CutList cl : vf.getCutListsSelected().getCutList())
			{
				Assert.assertTrue(cl.isSetCut());
			}
		}
		JaxbUtil.trace(video);
	}
	
	@Test
	public void cut() throws OtrConfigurationException, OtrInternalErrorException, UtilsProcessingException
	{
		List<String> expected = new ArrayList<String>();
		expected.add("dir.tools/tool.ffmpeg -ss 14.00 -t 23.00 -i dir.tmp/mp4-1.mp4 -vcodec copy -acodec copy dir.tmp/1.1-1.mp4");
		expected.add("dir.tools/tool.ffmpeg -ss 28.00 -t 4.00 -i dir.tmp/mp4-1.mp4 -vcodec copy -acodec copy dir.tmp/1.1-2.mp4");
		expected.add("dir.tools/tool.ffmpeg -ss 14.00 -t 23.00 -i dir.tmp/mp4-2.mp4 -vcodec copy -acodec copy dir.tmp/2.1-1.mp4");
		expected.add("dir.tools/tool.ffmpeg -ss 28.00 -t 4.00 -i dir.tmp/mp4-2.mp4 -vcodec copy -acodec copy dir.tmp/2.1-2.mp4");
		
		List<String> actual = videoCutter.cut(video);
		Assert.assertEquals(expected.size(), actual.size());
		
		for(int i=0;i<expected.size();i++)
		{
			logger.debug("Result "+i);
			logger.debug("Actual:   "+actual.get(i));
			logger.debug("Expected: "+expected.get(i));
			Assert.assertEquals(expected.get(i), actual.get(i));
		}
	}
	
	@Test
	public void getSecond() throws OtrConfigurationException, OtrInternalErrorException
	{	
		Assert.assertEquals(VideoCutter.getSecond(123.999),"124.00");
		Assert.assertEquals(VideoCutter.getSecond(123),"123.00");
		Assert.assertEquals(VideoCutter.getSecond(2.0),"2.00");
		Assert.assertEquals(VideoCutter.getSecond(2.1),"2.10");
		Assert.assertEquals(VideoCutter.getSecond(12342.1),"12342.10");
	}
}