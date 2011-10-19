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

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CutGenerator extends AbstactBatchGenerator
{
	static Log logger = LogFactory.getLog(CutGenerator.class);
	
	private RawExtract rawExtract;
	private VideoCutter videoCutter;
	
	private ShellCmdCopy shellCopy;
	private ShellCmdRm shellRm;
	
	private Mp3ToAac mp3ToAac;
	private Ac3ToAac ac3ToAac;
	
	public CutGenerator(Configuration config, AviToMp4.Quality quality, AviToMp4.Audio audio, AviToMp4.Profile profile) throws OtrInternalErrorException
	{
		super(config);
		shellCopy = new ShellCmdCopy();
		shellRm = new ShellCmdRm();
		
		mp3ToAac = new Mp3ToAac(config,dirTools,dirBat,dirTmp);
		ac3ToAac = new Ac3ToAac(config);
		
		logger.debug("");
		logger.debug("Creating Batch in "+dirBat.getAbsolutePath());
		
		txt = new ExlpTxtWriter();
		
		init(quality,audio,profile);
		initChilds();
	}
	
	private void initChilds() throws OtrInternalErrorException
	{
		try
		{
			rawExtract = new RawExtract(config);
			rawExtract.setTxt(txt);
			rawExtract.init(quality,audio,profile);
		}
		catch (OtrInternalErrorException e)
		{
			throw new OtrInternalErrorException(e.getMessage()+" while initalizing "+RawExtract.class.getSimpleName());
		}
		try
		{
			videoCutter = new VideoCutter(config);
			videoCutter.setTxt(txt);
			videoCutter.init(quality,audio,profile);
		}
		catch (OtrInternalErrorException e)
		{
			throw new OtrInternalErrorException(e.getMessage()+" while initalizing "+VideoCutter.class.getSimpleName());
		}
		
	}
	
	public void create(VideoFiles vFiles)
	{
		 for(VideoFile vf : vFiles.getVideoFile())
		 {
			 if(vf.isSetCutListsAvailable()
				&& vf.getCutListsAvailable().isSetCutList()
				&& vf.isSetCutListsSelected()
				&& vf.getCutListsSelected().isSetCutList())
			{crateForVideo(vf);}
			else
			{
				txt.add("echo No Cutlist available for: "+vf.getFileName().getValue());
				txt.add("");
			}
			 
		 }
		 txt.debug();

		 File f = new File(dirBat, "cut.bat");
		 txt.writeFile(f);
		 logger.info("");
		 logger.info("Batch file written to: "+rpf.relativate(new File("."), f));
	}
	
	private void crateForVideo(VideoFile vf)
	{
		txt.add("echo Processing: "+vf.getFileName().getValue());
		txt.add("");
		try {txt.add(shellRm.rmDir(rpf.relativate(dirBat, dirTmp), true));}
		catch (ExlpUnsupportedOsException e) {logger.error(e);}
	
		switch(profile)
		{
			case P0: extract(vf);cut(vf);merge(vf);break;
			case P1: cut(vf);merge(vf);break;
		}	
		
		txt.add("");
		txt.add("");
	}
	
	private void extract(VideoFile vf)
	{
		String sMp4 = rpf.relativate(dirBat, new File(dirTmp,"mp4.mp4"));
		rawExtract.rawExtract(vf);
		switch(audio)
		{
			case Mp3: txt.add(mp3ToAac.convert());break;
			case Ac3: txt.add(ac3ToAac.convert(vf.getOtrId().getKey()+"."+vf.getOtrId().getFormat().getType()));break;
		}
		createMp4(vf.getFileName().getValue(),sMp4);
	}
	
	private void cut(VideoFile vf)
	{
		String inVideo=null;
		switch(profile)
		{
			case P0: inVideo = rpf.relativate(dirBat, new File(dirTmp,"mp4.mp4"));break;
			case P1: inVideo = rpf.relativate(dirBat, new File(dirAvi,vf.getFileName().getValue()));break;
		}
		 
		videoCutter.applyCutList(vf.getCutListsSelected(),inVideo);
	}
	
	private void merge(VideoFile vf)
	{
		mergeMp4(vf.getCutListsSelected(),vf);
	}
	
	private void mergeMp4(CutListsSelected clSelected, VideoFile vf)
	{
		txt.add("");
		int counter=1;
		for(CutList cl : clSelected.getCutList())
		{
			mergeMp4(counter,cl,vf);
			counter++;
		}
	}
	
	private void mergeMp4(int index, CutList cl, VideoFile vf)
	{
		String fileName = vf.getFileName().getValue();
		if(cl.isSetFileName() && cl.getFileName().getValue().length()>0){fileName=cl.getFileName().getValue();}
		
		StringBuffer sb = new StringBuffer();
		if(cl.getCut().size()==1)
		{
			String sFrom = rpf.relativate(dirBat, new File(dirTmp,index+"-1.mp4"));
			String sTo = rpf.relativate(dirBat, new File(dirHqMp4,fileName+".mp4"));
			
			try {txt.add(shellCopy.copyFile(sFrom, sTo));}
			catch (ExlpUnsupportedOsException e)
			{
				logger.error(e);
				logger.error("File was not copied! ");
				logger.error("\tFrom: "+sFrom);
				logger.error("\tTo  : "+sTo);
			}
		}
		else if(cl.getCut().size()>1)
		{
			sb.append(cmdMp4Box).append(" ");
			switch(quality){case HD: sb.append("-fps 50 ");break;}
			sb.append(rpf.relativate(dirBat, new File(dirTmp,index+"-1.mp4")));
			sb.append(" ");
			for(int i=2;i<=cl.getCut().size();i++)
			{
				sb.append("-cat ");
				sb.append(rpf.relativate(dirBat, new File(dirTmp,index+"-"+i+".mp4")));
				sb.append(" ");
			}
			sb.append("-out ");
			sb.append(rpf.relativate(dirBat, new File(dirHqMp4,fileName+".mp4")));
			txt.add(sb.toString());
		}
	}
	
	
	
	private void createMp4(String vfName, String sMp4)
	{
		String sH264 = rpf.relativate(dirBat, new File(dirTmp,"raw_video.h264"));
		String sAudio=rpf.relativate(dirBat, new File(dirTmp,"aac.aac"));

		StringBuffer sb = new StringBuffer();
		sb.append(cmdMp4Box).append(" ");
		switch(quality){case HD: sb.append("-fps 50 ");break;}
		sb.append(" -add "+sH264+" -add "+sAudio+" "+sMp4);
		txt.add(sb.toString());
	}
	
	
}