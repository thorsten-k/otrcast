package net.sf.otrcutmp4.controller.batch.video;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.batch.video.AviExtract;
import de.kisner.otrcast.controller.exception.OtrConfigurationException;
import de.kisner.otrcast.controller.exception.OtrInternalErrorException;
import de.kisner.otrcast.interfaces.OtrCastInterface;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.otrcutmp4.controller.batch.AbstractBatchTest;
import net.sf.otrcutmp4.util.TestOtrConfig;

public class TestRawExtract extends AbstractBatchTest
{ 
	final static Logger logger = LoggerFactory.getLogger(TestRawExtract.class);
	
	private AviExtract rawExtract;
	
	@Before
	public void init() throws IOException
	{		
		TestOtrConfig tC = TestOtrConfig.factory();
		rawExtract = new AviExtract(tC.getOtrConfig(), OtrCastInterface.Profile.P0);
	}
	
	@Test
	public void hq() throws OtrConfigurationException, OtrInternalErrorException, UtilsProcessingException
	{
		List<String> expected = new ArrayList<String>();
		expected.add("dir.tools/tool.mp4box -aviraw video dir.avi/my-1.mpg.HQ.avi -out dir.tmp/raw-1.h264");
		expected.add("dir.tools/tool.mp4box -aviraw video dir.avi/my-2.mpg.HQ.avi -out dir.tmp/raw-2.h264");
		
		List<String> actual = rawExtract.rawExtract(video);
		Assert.assertEquals(expected.size(), actual.size());
		
		for(int i=0;i<expected.size();i++)
		{
			logger.debug("Result "+i);
			logger.debug("Actual:   "+actual.get(i));
			logger.debug("Expected: "+expected.get(i));
			Assert.assertEquals(expected.get(i), actual.get(i));
		}
	}
}