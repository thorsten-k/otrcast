package net.sf.otrcutmp4.controller.batch.video;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;
import net.sf.otrcutmp4.AviToMp4;
import net.sf.otrcutmp4.controller.exception.OtrConfigurationException;
import net.sf.otrcutmp4.controller.exception.OtrInternalErrorException;
import net.sf.otrcutmp4.model.xml.cut.FileName;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.test.AbstractClientTest;
import net.sf.otrcutmp4.util.TestOtrConfig;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRawExtract extends AbstractClientTest
{ 
	final static Logger logger = LoggerFactory.getLogger(TestRawExtract.class);
	
	private RawExtract rawExtract;
	
	private VideoFile vf;
	
	@Before
	public void init() throws IOException
	{		
		TestOtrConfig tC = TestOtrConfig.factory();
		rawExtract = new  RawExtract(tC.getOtrConfig());
		
		FileName fn = new FileName();
		fn.setValue("my.file.avi");
		
		vf = new VideoFile();
		vf.setFileName(fn);
	}
	
	@Test
	public void hqMp3() throws OtrConfigurationException, OtrInternalErrorException
	{
		List<String> actual = rawExtract.rawExtract(vf,AviToMp4.Quality.HQ, AviToMp4.Audio.Mp3);
		Assert.assertEquals(2, actual.size());
		
		String expected0 = "dir.tools/tool.mp4box -aviraw video dir.hq.avi/my.file.avi -out dir.tmp/raw.h264";
		String expected1 = "dir.tools/tool.mp4box -aviraw audio dir.hq.avi/my.file.avi -out dir.tmp/raw.mp3";
		
		for(String s : actual){logger.debug(s);}
		
		Assert.assertEquals(expected0, actual.get(0));
		Assert.assertEquals(expected1, actual.get(1));
	}
}