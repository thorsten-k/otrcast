package net.sf.otrcutmp4.controller.batch.video;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.AviToMp4;
import net.sf.otrcutmp4.controller.batch.AbstactBatchGenerator;
import net.sf.otrcutmp4.controller.exception.OtrInternalErrorException;
import net.sf.otrcutmp4.controller.factory.xml.otr.XmlOtrIdFactory;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.OtrConfig.Dir;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AviExtract extends AbstactBatchGenerator
{	
	final static Logger logger = LoggerFactory.getLogger(AviExtract.class);
	
	public AviExtract(OtrConfig cfg, AviToMp4.Profile profile)
	{
		super(cfg, profile);
	}
	
	public List<String> rawExtract(VideoFile vf) throws OtrInternalErrorException, UtilsProcessingException
	{
		List<String> result = new ArrayList<String>();
		
		String inAvi = rpf.relativate(new File(cfg.getDir(Dir.AVI),vf.getFileName().getValue()));		
		String outH264 = rpf.relativate(new File(cfg.getDir(Dir.TMP), "raw.h264"));
		
		StringBuffer sbVideo = new StringBuffer();
	
		sbVideo.append(cmdMp4Box).append(" ");
		
		JaxbUtil.trace(vf);
		
		switch(XmlOtrIdFactory.getType(vf.getOtrId().getFormat().getType()))
		{
			case hd: sbVideo.append("-fps 50 ");break;
			case hq: logger.trace("No FPS Handling required here");break;
			default: throw new OtrInternalErrorException("No Default Handling available");
		}
		
		sbVideo.append("-aviraw video "+inAvi+" -out "+outH264);
		
			
		result.add(sbVideo.toString());
		

		return result;
	}
}
