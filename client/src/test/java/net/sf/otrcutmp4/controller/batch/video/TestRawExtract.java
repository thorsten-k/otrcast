package net.sf.otrcutmp4.controller.batch.video;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.otrcutmp4.AviToMp4.Profile;
import net.sf.otrcutmp4.controller.exception.OtrConfigurationException;
import net.sf.otrcutmp4.controller.exception.OtrInternalErrorException;
import net.sf.otrcutmp4.controller.factory.xml.XmlOtrIdFactory;
import net.sf.otrcutmp4.model.xml.cut.FileName;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.otr.Format;
import net.sf.otrcutmp4.model.xml.otr.OtrId;
import net.sf.otrcutmp4.test.AbstractClientTest;
import net.sf.otrcutmp4.util.TestOtrConfig;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRawExtract extends AbstractClientTest
{ 
	final static Logger logger = LoggerFactory.getLogger(TestRawExtract.class);
	
	private AviExtract rawExtract;
	
	private VideoFile vf;
	
	@Before
	public void init() throws IOException
	{		
		TestOtrConfig tC = TestOtrConfig.factory();
		rawExtract = new  AviExtract(tC.getOtrConfig(),Profile.P0);
		
		FileName fn = new FileName();
		fn.setValue("my.file.avi");
		
		Format format = new Format();
		format.setType(XmlOtrIdFactory.typeAviHq);
		format.setAc3(false);
		
		OtrId id = new OtrId();
		id.setFormat(format);
		
		vf = new VideoFile();
		vf.setFileName(fn);
		vf.setOtrId(id);
	}
	
	@Test
	public void hq() throws OtrConfigurationException, OtrInternalErrorException, UtilsProcessingException
	{
		List<String> actual = rawExtract.rawExtract(vf);
		Assert.assertEquals(1, actual.size());
		
		String expected0 = "dir.tools/tool.mp4box -aviraw video dir.avi/my.file.avi -out dir.tmp/raw.h264";
		
		for(String s : actual){logger.debug(s);}
		
		Assert.assertEquals(expected0, actual.get(0));
	}
}