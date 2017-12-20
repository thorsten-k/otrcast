package net.sf.otrcutmp4.controller.batch.audio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.batch.audio.Mp3ToAac;
import de.kisner.otrcast.controller.exception.OtrConfigurationException;
import de.kisner.otrcast.controller.exception.OtrInternalErrorException;
import de.kisner.otrcast.interfaces.OtrCastInterface;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.otrcutmp4.controller.batch.AbstractBatchTest;
import net.sf.otrcutmp4.util.TestOtrConfig;

public class TestMp3ToAac extends AbstractBatchTest
{ 
	final static Logger logger = LoggerFactory.getLogger(TestMp3ToAac.class);
	
	private Mp3ToAac mp3ToAac;
	
	@Before
	public void init() throws IOException
	{		
		TestOtrConfig tC = TestOtrConfig.factory();
		mp3ToAac = new  Mp3ToAac(tC.getOtrConfig(), OtrCastInterface.Profile.P0);
	}
		
	@Test
	public void extract() throws OtrConfigurationException, UtilsProcessingException, OtrInternalErrorException
	{
		List<String> expected = new ArrayList<String>();
		expected.add("dir.tools/tool.mp4box -aviraw audio dir.avi/my-1.mpg.HQ.avi -out dir.tmp/raw-1.mp3");
		expected.add("dir.tools/tool.mp4box -aviraw audio dir.avi/my-2.mpg.HQ.avi -out dir.tmp/raw-2.mp3");
		
		List<String> actual = mp3ToAac.extract(video);
		Assert.assertEquals(expected.size(), actual.size());
		
		for(int i=0;i<expected.size();i++)
		{
			logger.debug("extract result "+i);
			logger.debug("Actual:   "+actual.get(i));
			logger.debug("Expected: "+expected.get(i));
			Assert.assertEquals(expected.get(i), actual.get(i));
		}
	}
	
	@Test
	public void transcode() throws OtrConfigurationException, UtilsProcessingException, OtrInternalErrorException
	{
		List<String> expected = new ArrayList<String>();
		expected.add("dir.tools/tool.lame --decode dir.tmp/raw-1_audio.mp3 - | dir.tools/tool.faac --mpeg-vers 4 -b "+TestOtrConfig.faacKbit+" -o dir.tmp/aac-1.aac -");
		expected.add("dir.tools/tool.lame --decode dir.tmp/raw-2_audio.mp3 - | dir.tools/tool.faac --mpeg-vers 4 -b "+TestOtrConfig.faacKbit+" -o dir.tmp/aac-2.aac -");
		
		List<String> actual = mp3ToAac.transcode(video);
		Assert.assertEquals(expected.size(), actual.size());
		
		for(int i=0;i<expected.size();i++)
		{
			logger.debug("transcode result "+i);
			logger.debug("Actual:   "+actual.get(i));
			logger.debug("Expected: "+expected.get(i));
			Assert.assertEquals(expected.get(i), actual.get(i));
		}
	}
}