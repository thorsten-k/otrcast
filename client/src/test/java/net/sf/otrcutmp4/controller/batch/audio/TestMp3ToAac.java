package net.sf.otrcutmp4.controller.batch.audio;

import java.io.IOException;
import java.util.List;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.otrcutmp4.AviToMp4.Profile;
import net.sf.otrcutmp4.controller.exception.OtrConfigurationException;
import net.sf.otrcutmp4.controller.exception.OtrInternalErrorException;
import net.sf.otrcutmp4.controller.factory.xml.otr.XmlOtrIdFactory;
import net.sf.otrcutmp4.model.xml.cut.FileName;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.otr.Format;
import net.sf.otrcutmp4.model.xml.otr.OtrId;
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
		mp3ToAac = new  Mp3ToAac(tC.getOtrConfig(),Profile.P0);
	}
	
	@Test
	public void checkFail() throws OtrConfigurationException
	{
		String actual = mp3ToAac.transcode();
		logger.debug(actual);
		String expected = "dir.tools/tool.lame --decode dir.tmp/raw_audio.mp3 - | dir.tools/tool.faac --mpeg-vers 4 -b "+TestOtrConfig.faacKbit+" -o dir.tmp/aac.aac -";
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void extract() throws OtrConfigurationException, UtilsProcessingException, OtrInternalErrorException
	{
		FileName fn = new FileName();
		fn.setValue("my.file.avi");
		
		Format format = new Format();
		format.setType(XmlOtrIdFactory.typeAviHq);
		format.setAc3(false);
		
		OtrId id = new OtrId();
		id.setFormat(format);
		
		VideoFile vf = new VideoFile();
		vf.setFileName(fn);
		vf.setOtrId(id);
		
		String actual = mp3ToAac.extract(vf);
		String expected = "dir.tools/tool.mp4box -aviraw audio dir.avi/my.file.avi -out dir.tmp/raw.mp3";
		
		Assert.assertEquals(expected, actual);
	}

}