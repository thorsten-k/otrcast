package net.sf.otrcutmp4.controller.batch;

import java.io.IOException;

import net.sf.otrcutmp4.test.AbstractClientTest;

import org.junit.Before;

import de.kisner.otrcast.factory.xml.otr.XmlOtrIdFactory;
import de.kisner.otrcast.model.xml.cut.Cut;
import de.kisner.otrcast.model.xml.cut.CutList;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.cut.VideoFiles;
import de.kisner.otrcast.model.xml.otr.Format;
import de.kisner.otrcast.model.xml.otr.OtrId;
import de.kisner.otrcast.model.xml.series.Video;

public class AbstractBatchTest extends AbstractClientTest
{
	protected Video video;
	
	@Before
	public void abstractInit() throws IOException
	{		
		video = new Video();
		video.setVideoFiles(new VideoFiles());
		video.getVideoFiles().getVideoFile().add(createVideoFile("my-1"));
		video.getVideoFiles().getVideoFile().add(createVideoFile("my-2"));
	}
	
	private VideoFile createVideoFile(String name)
	{
		Cut c1 = new Cut();
		c1.setStart(14);
		c1.setInclude(true);
		c1.setDuration(23);
		
		Cut c2 = new Cut();
		c2.setStart(28);
		c2.setInclude(true);
		c2.setDuration(4);
		
		CutList cl = new CutList();
		cl.getCut().add(c1);
		cl.getCut().add(c2);
		
		Format format = new Format();
		format.setType(XmlOtrIdFactory.typeAviHq);
		format.setAc3(false);
		
		OtrId id = new OtrId();
		id.setKey(name);
		id.setFormat(format);
		
		VideoFile vf = new VideoFile();
		vf.setOtrId(id);
		vf.setCutList(cl);
		return vf;
	}
}
