package net.sf.otrcutmp4.controller.batch;

import java.io.File;

import net.sf.exlp.util.exception.ExlpUnsupportedOsException;
import net.sf.exlp.util.io.txt.ExlpTxtWriter;
import net.sf.exlp.util.os.shell.ShellCmdCopy;
import net.sf.exlp.util.os.shell.ShellCmdRm;
import net.sf.otrcutmp4.AviToMp4;
import net.sf.otrcutmp4.controller.batch.audio.Ac3ToAac;
import net.sf.otrcutmp4.controller.batch.audio.Mp3ToAac;
import net.sf.otrcutmp4.controller.batch.video.RawExtract;
import net.sf.otrcutmp4.controller.batch.video.VideoCutter;
import net.sf.otrcutmp4.controller.exception.OtrInternalErrorException;
import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.cut.CutListsSelected;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.OtrConfig.Dir;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CutGenerator extends AbstactBatchGenerator
{
	final static Logger logger = LoggerFactory.getLogger(CutGenerator.class);
	
	private RawExtract rawExtract;
	private VideoCutter videoCutter;
	
	private ShellCmdCopy shellCopy;
	private ShellCmdRm shellRm;
	
	private Mp3ToAac mp3ToAac;
	private Ac3ToAac ac3ToAac;
	
	public CutGenerator(OtrConfig cfg) throws OtrInternalErrorException
	{
		super(cfg);
		shellCopy = new ShellCmdCopy();
		shellRm = new ShellCmdRm();
		
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
	
	public void create(VideoFiles vFiles, AviToMp4.Quality quality, AviToMp4.Audio audio, AviToMp4.Profile profile) throws OtrInternalErrorException
	{
		 for(VideoFile vf : vFiles.getVideoFile())
		 {
			 if(vf.isSetCutListsAvailable()
				&& vf.getCutListsAvailable().isSetCutList()
				&& vf.isSetCutListsSelected()
				&& vf.getCutListsSelected().isSetCutList())
			{crateForVideo(vf,quality,audio,profile);}
			else
			{
				txt.add("echo No Cutlist available for: "+vf.getFileName().getValue());
				txt.add("");
			}
			 
		 }
		 txt.debug();

		 File f = new File("cut.bat");
		 txt.writeFile(f);
		 logger.info("");
		 logger.info("Batch file written to: "+rpf.relativate(new File("."), f));
	}
	
	private void crateForVideo(VideoFile vf, AviToMp4.Quality quality, AviToMp4.Audio audio, AviToMp4.Profile profile) throws OtrInternalErrorException
	{
		txt.add("echo Processing: "+vf.getFileName().getValue());
		txt.add("");
		
		try {txt.add(shellRm.rmDir(rpf.relativate(cfg.getDir(Dir.TMP)), true));}
		catch (ExlpUnsupportedOsException e) {logger.error("",e);}
	
		switch(profile)
		{
			case P0: extract(vf,quality,audio,profile);cut(vf,quality,profile);merge(vf,quality);break;
			case P1: cut(vf,quality,profile);merge(vf,quality);break;
		}	
		
		txt.add("");
		txt.add("");
	}
	
	private void extract(VideoFile vf,AviToMp4.Quality quality, AviToMp4.Audio audio, AviToMp4.Profile profile) throws OtrInternalErrorException
	{
		String sMp4 = rpf.relativate(new File(cfg.getDir(Dir.TMP),"mp4.mp4"));
				
		txt.add(rawExtract.rawExtract(vf,quality,audio));
		switch(audio)
		{
			case Mp3: txt.add(mp3ToAac.create());break;
			case Ac3: txt.add(ac3ToAac.create(vf.getOtrId().getKey()+"."+vf.getOtrId().getFormat().getType()));break;
		}
		createMp4(vf.getFileName().getValue(),sMp4,quality);
	}
	
	private void cut(VideoFile vf,AviToMp4.Quality quality, AviToMp4.Profile profile) throws OtrInternalErrorException
	{
		String inVideo=null;
		switch(profile)
		{
			case P0: inVideo = rpf.relativate(new File(cfg.getDir(Dir.TMP),"mp4.mp4"));break;
			case P1: inVideo = rpf.relativate(new File(getAviDir(quality),vf.getFileName().getValue()));break;
		}
		 
		videoCutter.applyCutList(vf.getCutListsSelected(),inVideo,profile);
	}
	
	private void merge(VideoFile vf,AviToMp4.Quality quality)
	{
		mergeMp4(vf.getCutListsSelected(),vf,quality);
	}
	
	private void mergeMp4(CutListsSelected clSelected, VideoFile vf,AviToMp4.Quality quality)
	{
		txt.add("");
		int counter=1;
		for(CutList cl : clSelected.getCutList())
		{
			mergeMp4(counter,cl,vf,quality);
			counter++;
		}
	}
	
	private void mergeMp4(int index, CutList cl, VideoFile vf,AviToMp4.Quality quality)
	{
		String fileName = vf.getFileName().getValue();
		if(cl.isSetFileName() && cl.getFileName().getValue().length()>0){fileName=cl.getFileName().getValue();}
		
		StringBuffer sb = new StringBuffer();
		if(cl.getCut().size()==1)
		{
			String sFrom = rpf.relativate(new File(cfg.getDir(Dir.TMP),index+"-1.mp4"));
			String sTo = rpf.relativate(new File(cfg.getDir(Dir.MP4),fileName+".mp4"));
			
			try {txt.add(shellCopy.copyFile(sFrom, sTo));}
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
			switch(quality){case HD: sb.append("-fps 50 ");break;}
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
	
	private void createMp4(String vfName, String sMp4,AviToMp4.Quality quality)
	{
		String sH264 = rpf.relativate(new File(cfg.getDir(Dir.TMP),"raw_video.h264"));
		String sAudio=rpf.relativate(new File(cfg.getDir(Dir.TMP),"aac.aac"));

		StringBuffer sb = new StringBuffer();
		sb.append(cmdMp4Box).append(" ");
		switch(quality){case HD: sb.append("-fps 50 ");break;}
		sb.append(" -add "+sH264+" -add "+sAudio+" "+sMp4);
		txt.add(sb.toString());
	}	
}