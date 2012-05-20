package net.sf.otrcutmp4.controller.batch.video;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.batch.AbstactBatchGenerator;
import net.sf.otrcutmp4.controller.exception.OtrInternalErrorException;
import net.sf.otrcutmp4.controller.factory.xml.XmlOtrIdFactory;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.OtrConfig.Dir;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RawExtract extends AbstactBatchGenerator
{	
	final static Logger logger = LoggerFactory.getLogger(RawExtract.class);
	
	public RawExtract(OtrConfig cfg)
	{
		super(cfg);
	}
	
	public List<String> rawExtract(VideoFile vf) throws OtrInternalErrorException, UtilsProcessingException
	{
		List<String> result = new ArrayList<String>();
		
		String inAvi = rpf.relativate(new File(cfg.getDir(Dir.AVI),vf.getFileName().getValue()));		
		String outH264 = rpf.relativate(new File(cfg.getDir(Dir.TMP), "raw.h264"));
		String outMp3 = rpf.relativate(new File(cfg.getDir(Dir.TMP), "raw.mp3"));
		
		StringBuffer sbVideo = new StringBuffer();
		StringBuffer sbAudio = new StringBuffer();
		
		sbVideo.append(cmdMp4Box).append(" ");
		sbAudio.append(cmdMp4Box).append(" ");
		
		JaxbUtil.error(vf);
		
		switch(XmlOtrIdFactory.getType(vf.getOtrId().getFormat().getType()))
		{
			case hd: sbVideo.append("-fps 50 ");sbAudio.append("-fps 50 ");break;
			case hq: logger.trace("No FPS Handling required here");break;
			default: throw new OtrInternalErrorException("No Default Handling available");
		}
		
		sbVideo.append("-aviraw video "+inAvi+" -out "+outH264);
		sbAudio.append("-aviraw audio ").append(inAvi).append(" -out "+outMp3);
			
		result.add(sbVideo.toString());
		
		if(vf.getOtrId().getFormat().isAc3())
		{
			logger.trace("No Handling required here for AC3");
		}
		else
		{
			result.add(sbAudio.toString());
		}


		return result;
	}
}
