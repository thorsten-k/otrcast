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
import net.sf.otrcutmp4.controller.batch.video.RawExtract;
import net.sf.otrcutmp4.controller.batch.video.VideoCutter;
import net.sf.otrcutmp4.controller.exception.OtrInternalErrorException;
import net.sf.otrcutmp4.controller.factory.xml.XmlOtrIdFactory;
import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.cut.CutListsSelected;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.OtrConfig.Dir;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CutGenerator extends AbstactBatchGenerator
{
	final static Logger logger = LoggerFactory.getLogger(CutGenerator.class);
	
	private RawExtract rawExtract;
	private VideoCutter videoCutter;
	
	private Mp3ToAac mp3ToAac;
	private Ac3ToAac ac3ToAac;
	
	public CutGenerator(OtrConfig cfg) throws OtrInternalErrorException
	{
		super(cfg);
		
		mp3ToAac = new Mp3ToAac(cfg);
		ac3ToAac = new Ac3ToAac(cfg);
		
		logger.debug("");
		logger.debug("Creating Batch in "+cfg.getDir(Dir.BAT).getAbsolutePath());
		
		txt = new ExlpTxtWriter();
		
		rawExtract = new RawExtract(cfg);
		rawExtract.setTxt(txt);

		videoCutter = new VideoCutter(cfg);
		videoCutter.setTxt(txt);
	}
	
	public void create(VideoFiles vFiles, AviToMp4.Profile profile) throws OtrInternalErrorException
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
	
		switch(profile)
		{
			case P0: extract(vf,profile);transcodeToMp4(vf);cut(vf,profile);merge(vf);break;
			case P1:                     transcodeToMp4(vf);cut(vf,profile);merge(vf);break;
		}	
		
		txt.add("");
		txt.add("");
	}
	
	private void extract(VideoFile vf, AviToMp4.Profile profile) throws OtrInternalErrorException, UtilsProcessingException
	{
		
				
		txt.add(rawExtract.rawExtract(vf));
		if(!vf.getOtrId().getFormat().isAc3())
		{
			txt.add(mp3ToAac.extract(vf, profile));
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
	
	private void transcodeToMp4(VideoFile vf) throws UtilsProcessingException
	{
		String sMp4 = rpf.relativate(new File(cfg.getDir(Dir.TMP),"mp4.mp4"));
		String sH264 = rpf.relativate(new File(cfg.getDir(Dir.TMP),"raw_video.h264"));
		String sAudio=rpf.relativate(new File(cfg.getDir(Dir.TMP),"aac.aac"));

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