package net.sf.otrcutmp4.controller.batch;

import java.io.File;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.exlp.util.exception.ExlpUnsupportedOsException;
import net.sf.exlp.util.io.FilenameIllegalCharRemover;
import net.sf.exlp.util.io.txt.ExlpTxtWriter;
import net.sf.exlp.util.os.shell.ShellCmdCopy;
import net.sf.exlp.util.os.shell.ShellCmdRm;
import net.sf.otrcutmp4.AviToMp4;
import net.sf.otrcutmp4.controller.batch.audio.Ac3ToAac;
import net.sf.otrcutmp4.controller.batch.audio.Mp3ToAac;
import net.sf.otrcutmp4.controller.batch.video.AviExtract;
import net.sf.otrcutmp4.controller.batch.video.VideoCutter;
import net.sf.otrcutmp4.controller.exception.OtrInternalErrorException;
import net.sf.otrcutmp4.controller.factory.xml.XmlOtrIdFactory;
import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.OtrConfig.Dir;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BatchGenerator extends AbstactBatchGenerator
{
	final static Logger logger = LoggerFactory.getLogger(BatchGenerator.class);
	
	private AviExtract rawExtract;
	private VideoCutter videoCutter;
	
	private Mp3ToAac mp3ToAac;
	private Ac3ToAac ac3ToAac;
	
	public BatchGenerator(OtrConfig cfg,AviToMp4.Profile profile) throws OtrInternalErrorException
	{
		super(cfg,profile);
		
		mp3ToAac = new Mp3ToAac(cfg,profile);
		ac3ToAac = new Ac3ToAac(cfg,profile);
		
		logger.debug("");
		logger.debug("Creating Batch in "+cfg.getDir(Dir.BAT).getAbsolutePath());
		
		txt = new ExlpTxtWriter();
		
		rawExtract = new AviExtract(cfg,profile);
		rawExtract.setTxt(txt);

		videoCutter = new VideoCutter(cfg,profile);
		videoCutter.setTxt(txt);
	}
	
	public void create(VideoFiles vFiles) throws OtrInternalErrorException
	{
		 for(VideoFile vf : vFiles.getVideoFile())
		 {
			 try
			 {
				 if(vf.isSetCutListsSelected() && vf.getCutListsSelected().isSetCutList())
				 {
					 crateForVideo(vf,profile);
				 }
				 else
				 {
					 txt.add("echo No Cutlist available for: "+vf.getFileName().getValue());
					 txt.add("");
				 }
			 }
			 catch (UtilsProcessingException e){e.printStackTrace();}
			 
		 }
		 txt.debug();

		 File f = new File(cfg.getDir(Dir.BAT),"cut.bat");
		 txt.writeFile(f);
		 logger.info("");
		 logger.info("Batch file written to: "+rpf.relativate(new File("."), f));
	}
	
	private void crateForVideo(VideoFile vf, AviToMp4.Profile profile) throws OtrInternalErrorException, UtilsProcessingException
	{
		txt.add("echo Processing: "+vf.getFileName().getValue());
		txt.add("");
		
		try {txt.add(ShellCmdRm.rmDir(rpf.relativate(cfg.getDir(Dir.TMP)), true));}
		catch (ExlpUnsupportedOsException e) {logger.error("",e);}
	
		extract(vf);
		transcode(vf);
		cut(vf,profile);
		merge(vf);
		
		txt.add("");
		txt.add("");
	}
	
	private void extract(VideoFile vf) throws OtrInternalErrorException, UtilsProcessingException
	{
		switch(profile)
		{
			case P0: txt.add(rawExtract.rawExtract(vf));break;
		}	
		
		if(vf.getOtrId().getFormat().isAc3())
		{
			txt.add(ac3ToAac.extract(vf));
		}
		else
		{
			txt.add(mp3ToAac.extract(vf));
		}
	}
	
	
	private void cut(VideoFile vf, AviToMp4.Profile profile) throws OtrInternalErrorException
	{
		String inVideo=null;
		switch(profile)
		{
			case P0: inVideo = rpf.relativate(new File(cfg.getDir(Dir.TMP),"mp4.mp4"));break;
			case P1: inVideo = rpf.relativate(new File(cfg.getDir(Dir.AVI),vf.getFileName().getValue()));break;
		}
		 
		videoCutter.applyCutList(vf.getCutListsSelected(),inVideo,profile);
	}
	
	private void merge(VideoFile vf) throws UtilsProcessingException
	{
		txt.add("");
		int counter=1;
		for(CutList cl : vf.getCutListsSelected().getCutList())
		{
			mergeMp4(counter,cl,vf);
			counter++;
		}
	}
	
	private void mergeMp4(int index, CutList cl, VideoFile vf) throws UtilsProcessingException
	{
		String fileName;
		if(cl.isSetVideo())
		{
			Episode xmlEpisode = cl.getVideo().get(0).getEpisode();
			StringBuffer sb = new StringBuffer();
			sb.append("S").append(xmlEpisode.getSeason().getNr()).append("E").append(xmlEpisode.getNr()).append("-");
			sb.append(xmlEpisode.getName());
			sb.append("-").append(xmlEpisode.getSeason().getSeries().getName());
			fileName = sb.toString();
		}
		else if(cl.isSetFileName() && cl.getFileName().getValue().length()>0){fileName=cl.getFileName().getValue();}
		else {fileName= vf.getFileName().getValue();}
		fileName = FilenameIllegalCharRemover.convert(fileName); 
		
		StringBuffer sb = new StringBuffer();
		if(cl.getCut().size()==1)
		{
			String sFrom = rpf.relativate(new File(cfg.getDir(Dir.TMP),index+"-1.mp4"));
			String sTo = rpf.relativate(new File(cfg.getDir(Dir.MP4),fileName+".mp4"));
			
			try {txt.add(ShellCmdCopy.copyFile(sFrom, sTo));}
			catch (ExlpUnsupportedOsException e)
			{
				logger.error("",e);
				logger.error("File was not copied! ");
				logger.error("\tFrom: "+sFrom);
				logger.error("\tTo  : "+sTo);
			}
		}
		else if(cl.getCut().size()>1)
		{
			sb.append(cmdMp4Box).append(" ");
			switch(XmlOtrIdFactory.getType(vf.getOtrId().getFormat().getType()))
			{
				case hd: sb.append("-fps 50 ");break;
			}
			sb.append(rpf.relativate(new File(cfg.getDir(Dir.TMP),index+"-1.mp4")));
			sb.append(" ");
			for(int i=2;i<=cl.getCut().size();i++)
			{
				sb.append("-cat ");
				sb.append(rpf.relativate(new File(cfg.getDir(Dir.TMP),index+"-"+i+".mp4")));
				sb.append(" ");
			}
			sb.append("-out ");
			sb.append(rpf.relativate(new File(cfg.getDir(Dir.MP4),fileName+".mp4")));
			txt.add(sb.toString());
		}
	}
	
	private void transcode(VideoFile vf) throws UtilsProcessingException
	{
		String sMp4 = rpf.relativate(new File(cfg.getDir(Dir.TMP),"mp4.mp4"));
		String sH264 = rpf.relativate(new File(cfg.getDir(Dir.TMP),"raw_video.h264"));
		String sAudio;
		
		if(vf.getOtrId().getFormat().isAc3())
		{
			sAudio = rpf.relativate(new File(cfg.getDir(Dir.TMP),"aac.m4a"));
		}
		else
		{
			txt.add(mp3ToAac.transcode());
			sAudio = rpf.relativate(new File(cfg.getDir(Dir.TMP),"aac.aac"));
		}

		StringBuffer sb = new StringBuffer();
		sb.append(cmdMp4Box).append(" ");
		switch(XmlOtrIdFactory.getType(vf.getOtrId().getFormat().getType()))
		{
			case hd: sb.append("-fps 50 ");break;
		}
		sb.append(" -add "+sH264+" -add "+sAudio+" "+sMp4);
		txt.add(sb.toString());
	}	
}