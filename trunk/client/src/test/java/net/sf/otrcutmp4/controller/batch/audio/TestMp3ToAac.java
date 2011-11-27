package net.sf.otrcutmp4.controller.batch.audio;

import java.io.IOException;

import net.sf.otrcutmp4.controller.exception.OtrConfigurationException;
import net.sf.otrcutmp4.test.AbstractClientTest;
import net.sf.otrcutmp4.util.TestOtrConfig;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMp3ToAac extends AbstractClientTest
{ 
	final static Logger logger = LoggerFactory.getLogger(TestMp3ToAac.class);
	
	private Mp3ToAac mp3ToAac;
	
	@Before
	public void init() throws IOException
	{		
		TestOtrConfig tC = TestOtrConfig.factory();
		mp3ToAac = new  Mp3ToAac(tC.getOtrConfig());
	}
	
	@Test
	public void checkFail() throws OtrConfigurationException
	{
		String actual = mp3ToAac.create();
		logger.debug(actual);
		String expected = "dir.tools/tool.lame --decode dir.tmp/raw_audio.mp3 - | dir.tools/tool.faac --mpeg-vers 4 -b "+TestOtrConfig.faacKbit+" -o dir.tmp/aac.aac -";
		Assert.assertEquals(expected, actual);
	}

}