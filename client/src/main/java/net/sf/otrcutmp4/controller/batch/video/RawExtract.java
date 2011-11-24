package net.sf.otrcutmp4.controller.batch.video;

import java.io.File;

import net.sf.otrcutmp4.controller.batch.AbstactBatchGenerator;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.util.OtrConfig;

public class RawExtract extends AbstactBatchGenerator
{	
	public RawExtract(OtrConfig otrConfig)
	{
		super(otrConfig);
	}
	
	public void rawExtract(VideoFile vf)
	{
		String sIn = rpf.relativate(new File(dirAvi,vf.getFileName().getValue()));
		String sH264 = rpf.relativate(new File(fTmp,"raw.h264"));
		String sMp3 = rpf.relativate(new File(fTmp,"raw.mp3"));
		
		StringBuffer sbVideo = new StringBuffer();
		StringBuffer sbAudio = new StringBuffer();
		
		sbVideo.append(cmdMp4Box).append(" ");
		sbAudio.append(cmdMp4Box).append(" ");
		
		switch(quality)
		{
			case HD: sbVideo.append("-fps 50 ");sbAudio.append("-fps 50 ");break;
		}
		
		sbVideo.append("-aviraw video "+sIn+" -out "+sH264);
		sbAudio.append("-aviraw audio "+sIn+" -out "+sMp3);
		
		txt.add(sbVideo.toString());
		
		switch(audio)
		{
			case Mp3: txt.add(sbAudio.toString());;break;
		}
	}
}
