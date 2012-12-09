package net.sf.otrcutmp4.controller.batch.video;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.otrcutmp4.AviToMp4;
import net.sf.otrcutmp4.controller.batch.AbstactBatchGenerator;
import net.sf.otrcutmp4.controller.exception.OtrInternalErrorException;
import net.sf.otrcutmp4.controller.factory.xml.otr.XmlOtrIdFactory;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.series.Video;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.OtrConfig.Dir;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class H264Transcode extends AbstactBatchGenerator
{	
	final static Logger logger = LoggerFactory.getLogger(H264Transcode.class);
	
	public H264Transcode(OtrConfig cfg, AviToMp4.Profile profile)
	{
		super(cfg, profile);
	}
	
	public List<String> transcode(Video video) throws OtrInternalErrorException, UtilsProcessingException
	{
		List<String> result = new ArrayList<String>();
		
		int index=1;
		for(VideoFile vf : video.getVideoFiles().getVideoFile())
		{
			result.add(transcode(index,vf));
			index++;
		}
		
		return result;
	}
	
	private String transcode(int index, VideoFile vf) throws OtrInternalErrorException, UtilsProcessingException
	{	
		String sMp4 = rpf.relativate(new File(cfg.getDir(Dir.TMP),"mp4-"+index+".mp4"));
		String sH264 = rpf.relativate(new File(cfg.getDir(Dir.TMP),"raw-"+index+"_video.h264"));
		
		String sAudio = rpf.relativate(new File(cfg.getDir(Dir.TMP),"aac-"+index+".aac"));
		
		StringBuffer sb = new StringBuffer();
		sb.append(cmdMp4Box);
		switch(XmlOtrIdFactory.getType(vf.getOtrId().getFormat().getType()))
		{
			case hd: sb.append(" -fps 50 ");break;
		}
		sb.append(" -add "+sH264+" -add "+sAudio+" "+sMp4);
		return sb.toString();
	}
}
