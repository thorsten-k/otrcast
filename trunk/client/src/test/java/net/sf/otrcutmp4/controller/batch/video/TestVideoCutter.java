package net.sf.otrcutmp4.controller.batch.video;

import java.io.IOException;

import net.sf.otrcutmp4.controller.exception.OtrConfigurationException;
import net.sf.otrcutmp4.controller.exception.OtrInternalErrorException;
import net.sf.otrcutmp4.model.xml.cut.FileName;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.test.AbstractClientTest;
import net.sf.otrcutmp4.util.TestOtrConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestVideoCutter extends AbstractClientTest
{ 
	static Log logger = LogFactory.getLog(TestVideoCutter.class);
	
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
	public void getSecond() throws OtrConfigurationException, OtrInternalErrorException
	{	
		Assert.assertEquals(VideoCutter.getSecond(123.999),"124.00");
		Assert.assertEquals(VideoCutter.getSecond(123),"123.00");
		Assert.assertEquals(VideoCutter.getSecond(2.0),"2.00");
		Assert.assertEquals(VideoCutter.getSecond(2.1),"2.10");
		Assert.assertEquals(VideoCutter.getSecond(12342.1),"12342.10");

	}
}