package net.sf.otrcutmp4.controller.batch;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.factory.txt.TxtFileNameFactoy;
import de.kisner.otrcast.factory.xml.otr.XmlOtrIdFactory;
import de.kisner.otrcast.model.xml.cut.Cut;
import de.kisner.otrcast.model.xml.cut.CutList;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.cut.VideoFiles;
import de.kisner.otrcast.model.xml.otr.Format;
import de.kisner.otrcast.model.xml.otr.OtrId;
import de.kisner.otrcast.model.xml.video.Video;
import net.sf.otrcutmp4.controller.batch.video.TestRawExtract;
import net.sf.otrcutmp4.test.AbstractClientTest;

public class AbstractBatchTest extends AbstractClientTest
{
	final static Logger logger = LoggerFactory.getLogger(TestRawExtract.class);
	
	protected Video video;
	
	@Before
	public void abstractInit() throws IOException
	{		
		video = new Video();
		video.setVideoFiles(new VideoFiles());
		video.getVideoFiles().getVideoFile().add(createVideoFile("my-1"));
		video.getVideoFiles().getVideoFile().add(createVideoFile("my-2"));
		
		File fAvi = new File(fTarget,"test/dir.avi");
		if(!fAvi.exists()){fAvi.mkdir();}
		for(VideoFile vf : video.getVideoFiles().getVideoFile())
		{
			File f = new File(fAvi,TxtFileNameFactoy.build(vf));
			logger.info(f.getAbsolutePath()+" "+f.exists());
			if(!f.exists()){f.createNewFile();}
		}
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
