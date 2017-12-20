package de.kisner.otrcast.controller.batch.video;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.app.AviToMp4;
import de.kisner.otrcast.controller.batch.AbstactBatchGenerator;
import de.kisner.otrcast.controller.exception.OtrInternalErrorException;
import de.kisner.otrcast.factory.txt.TxtFileNameFactoy;
import de.kisner.otrcast.factory.xml.otr.XmlOtrIdFactory;
import de.kisner.otrcast.interfaces.OtrCastInterface;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.series.Video;
import de.kisner.otrcast.util.OtrConfig;
import de.kisner.otrcast.util.OtrConfig.Dir;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.exlp.util.xml.JaxbUtil;

public class AviExtract extends AbstactBatchGenerator
{	
	final static Logger logger = LoggerFactory.getLogger(AviExtract.class);
	
	public AviExtract(OtrConfig cfg, OtrCastInterface.Profile profile)
	{
		super(cfg, profile);
	}
	
	public List<String> rawExtract(Video video) throws OtrInternalErrorException, UtilsProcessingException
	{
		List<String> result = new ArrayList<String>();
		
		int index=1;
		for(VideoFile vf : video.getVideoFiles().getVideoFile())
		{
			result.add(rawExtract(index,vf));
			index++;
		}
		
		return result;
	}
	
	private String rawExtract(int index, VideoFile vf) throws OtrInternalErrorException, UtilsProcessingException
	{	
		File f = new File(cfg.getDir(Dir.AVI),TxtFileNameFactoy.build(vf.getOtrId()));
		JaxbUtil.trace(vf);
		logger.trace("Extracting "+f.getAbsolutePath());
		if(!f.exists() || !f.isFile()){throw new UtilsProcessingException("File missing: "+f.getAbsolutePath());}
		
		String inAvi = rpf.relativate(f);		
		String outH264 = rpf.relativate(new File(cfg.getDir(Dir.TMP), "raw-"+index+".h264"));
		
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
		
		return sbVideo.toString();
	}
}
