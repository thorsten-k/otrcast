package net.sf.otrcutmp4.controller.batch.audio;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.batch.audio.Ac3ToAac;
import de.kisner.otrcast.controller.exception.OtrConfigurationException;
import de.kisner.otrcast.factory.xml.otr.XmlOtrIdFactory;
import de.kisner.otrcast.interfaces.OtrCastInterface.Profile;
import de.kisner.otrcast.model.xml.cut.FileName;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.otr.Format;
import de.kisner.otrcast.model.xml.otr.OtrId;
import net.sf.otrcutmp4.test.AbstractClientTest;
import net.sf.otrcutmp4.util.TestOtrConfig;

public class TestAc3ToAac extends AbstractClientTest
{ 
	final static Logger logger = LoggerFactory.getLogger(TestAc3ToAac.class);
	
	private Ac3ToAac ac3ToAac;
	private VideoFile vf;
	
	@Before
	public void init() throws IOException
	{		
		FileName fn = new FileName();
		fn.setValue("my.file.avi");
		
		Format format = new Format();
		format.setType(XmlOtrIdFactory.typeAviHq);
		format.setAc3(false);
		
		OtrId id = new OtrId();
		id.setKey("myFile");
		id.setFormat(format);
		
		vf = new VideoFile();
		vf.setFileName(fn);
		vf.setOtrId(id);
		
		TestOtrConfig tC = TestOtrConfig.factory();
		ac3ToAac = new  Ac3ToAac(tC.getOtrConfig(),Profile.P0);
	}
	
	@Test
	public void checkFail() throws OtrConfigurationException
	{
		String actual = ac3ToAac.extract(vf);
		logger.debug(actual);
		String expected = "dir.tools/tool.eac3to dir.avi/"+vf.getOtrId().getKey()+"."+XmlOtrIdFactory.typeAc3Hd+" 1: stdout.wav | dir.tools/tool.neroaac -q 0.35 -ignorelength -if - -of dir.tmp/aac.m4a";
		Assert.assertEquals(expected, actual);
	}

}