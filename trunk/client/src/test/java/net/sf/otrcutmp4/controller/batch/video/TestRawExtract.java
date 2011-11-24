package net.sf.otrcutmp4.controller.batch.video;

import java.io.IOException;
import java.util.List;

import net.sf.otrcutmp4.AviToMp4;
import net.sf.otrcutmp4.controller.exception.OtrConfigurationException;
import net.sf.otrcutmp4.model.xml.cut.FileName;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.test.AbstractClientTest;
import net.sf.otrcutmp4.util.TestOtrConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestRawExtract extends AbstractClientTest
{ 
	static Log logger = LogFactory.getLog(TestRawExtract.class);
	
	private RawExtract rawExtract;
	private String testFile;
	
	private VideoFile vf;
	
	@Before
	public void init() throws IOException
	{		
		TestOtrConfig tC = TestOtrConfig.factory();
		rawExtract = new  RawExtract(tC.getOtrConfig());
		testFile = "myTest";
		
		FileName fn = new FileName();
		fn.setValue("my.file.avi");
		
		vf = new VideoFile();
		vf.setFileName(fn);
	}
	
	@Test
	public void checkFail() throws OtrConfigurationException
	{
		List<String> actual = rawExtract.rawExtract(vf,AviToMp4.Quality.HQ, AviToMp4.Audio.Mp3);
		for(String s : actual)
		{
			logger.debug(s);
		}
		

	}

}