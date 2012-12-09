package net.sf.otrcutmp4.controller.batch;

import java.io.IOException;

import net.sf.otrcutmp4.controller.factory.xml.otr.XmlOtrIdFactory;
import net.sf.otrcutmp4.model.xml.cut.FileName;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.model.xml.otr.Format;
import net.sf.otrcutmp4.model.xml.otr.OtrId;
import net.sf.otrcutmp4.model.xml.series.Video;
import net.sf.otrcutmp4.test.AbstractClientTest;

import org.junit.Before;

public class AbstractBatchTest extends AbstractClientTest
{
	protected Video video;
	
	@Before
	public void abstractInit() throws IOException
	{		
		video = new Video();
		video.setVideoFiles(new VideoFiles());
		video.getVideoFiles().getVideoFile().add(createVideoFile("my-1.file.avi"));
		video.getVideoFiles().getVideoFile().add(createVideoFile("my-2.file.avi"));
	}
	
	private VideoFile createVideoFile(String name)
	{
		FileName fn = new FileName();
		fn.setValue(name);
		
		Format format = new Format();
		format.setType(XmlOtrIdFactory.typeAviHq);
		format.setAc3(false);
		
		OtrId id = new OtrId();
		id.setFormat(format);
		
		VideoFile vf = new VideoFile();
		vf.setFileName(fn);
		vf.setOtrId(id);
		return vf;
	}
}
