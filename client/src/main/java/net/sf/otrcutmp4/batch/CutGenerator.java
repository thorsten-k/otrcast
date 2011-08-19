package net.sf.otrcutmp4.batch;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import net.sf.exlp.util.exception.ExlpUnsupportedOsException;
import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.exlp.util.io.txt.ExlpTxtWriter;
import net.sf.exlp.util.os.shell.ShellCmdCopy;
import net.sf.exlp.util.os.shell.ShellCmdRm;
import net.sf.otrcutmp4.AviToMp4;
import net.sf.otrcutmp4.batch.audio.Ac3ToAac;
import net.sf.otrcutmp4.batch.audio.Mp3ToAac;
import net.sf.otrcutmp4.model.xml.cut.Cut;
import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.cut.CutListsSelected;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.util.OtrConfig;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CutGenerator
{
	static Log logger = LogFactory.getLog(CutGenerator.class);
	
	private File dirAvi,dirTmp,dirHqMp4,dirTools,dirBat;
	private ExlpTxtWriter txt;
	private static DecimalFormat df;
	
	private AviToMp4.Quality quality;
	private AviToMp4.Audio audio;
	
	private String cmdMp4Box,cmdFfmpeg;
	private RelativePathFactory rpf;
	
	private ShellCmdCopy shellCopy;
	private ShellCmdRm shellRm;
	
	private Mp3ToAac mp3ToAac;
	private Ac3ToAac ac3ToAac;
	
	public CutGenerator(Configuration config, AviToMp4.Quality quality, AviToMp4.Audio audio)
	{
		this.quality=quality;
		this.audio=audio;
		shellCopy = new ShellCmdCopy();
		shellRm = new ShellCmdRm();
		
		switch(quality)
		{
			case HQ: dirAvi = new File(config.getString(OtrConfig.dirHqAvi));break;
			case HD: dirAvi = new File(config.getString(OtrConfig.dirHdAvi));break;
		}
		
		dirTmp = new File(config.getString(OtrConfig.dirTmp));
		dirHqMp4 = new File(config.getString(OtrConfig.dirHqMp4));
		dirTools = new File(config.getString(OtrConfig.dirTools));
		dirBat = new File(config.getString(OtrConfig.dirBat));

		mp3ToAac = new Mp3ToAac(config,dirTools,dirBat,dirTmp);
		ac3ToAac = new Ac3ToAac(config);
		
		logger.debug("");
		logger.debug("Creating Batch in "+dirBat.getAbsolutePath());
		
		txt = new ExlpTxtWriter();
		rpf = new RelativePathFactory();
		
		cmdMp4Box = rpf.relativate(dirBat, new File(dirTools,config.getString(OtrConfig.toolMp4Box)));
		cmdFfmpeg = rpf.relativate(dirBat, new File(dirTools,config.getString(OtrConfig.toolFfmpeg)));
	}
	
	public void create(VideoFiles vFiles)
	{
		 for(VideoFile vf : vFiles.getVideoFile())
		 {
			 crateForVideo(vf);
		 }
		 txt.debug();

		 File f = new File(dirBat, "cut.bat");
		 txt.writeFile(f);
		 logger.info("");
		 logger.info("Batch file written to: "+rpf.relativate(new File("."), f));
	}
	
	private void crateForVideo(VideoFile vf)
	{
		if(vf.isSetCutListsAvailable() && vf.getCutListsAvailable().isSetCutList()
				&& vf.isSetCutListsSelected() && vf.getCutListsSelected().isSetCutList())
		{
			String sMp4 = rpf.relativate(dirBat, new File(dirTmp,"mp4.mp4"));
			
			txt.add("echo Processing: "+vf.getFileName().getValue());
			txt.add("");
			try {txt.add(shellRm.rmDir(rpf.relativate(dirBat, dirTmp), true));}
			catch (ExlpUnsupportedOsException e) {logger.error(e);}
			rawExtract(vf);
			
			switch(audio)
			{
				case Mp3: txt.add(mp3ToAac.convert());break;
				case Ac3: txt.add(ac3ToAac.convert(vf.getFileId().getValue()));break;
			}
			
			createMp4(vf.getFileName().getValue(),sMp4);
			cutList(vf.getCutListsSelected(),sMp4);
			mergeMp4(vf.getCutListsSelected(),vf);
			txt.add("");
			txt.add("");
		}
		else
		{
			txt.add("echo No Cutlist available for: "+vf.getFileName().getValue());
			txt.add("");
		}
	}
		
	private void cutList(CutListsSelected clSelected, String fMp4)
	{
		txt.add("");
		int counter=1;
		for(CutList cl : clSelected.getCutList())
		{
			cutList(counter, cl, fMp4);
			counter++;
		}
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
	
	private void cutList(int index, CutList cl, String sMp4)
	{
		int counter = 1;
		for(Cut cut : cl.getCut())
		{
			if(cut.isSetInclude())
			{
				StringBuffer sb = new StringBuffer();
				sb.append(cmdFfmpeg);
				sb.append(" -ss ").append(getSecond(cut.getStart()));
				sb.append(" -t ").append(getSecond(cut.getDuration()));
				sb.append(" -i "+sMp4);
				sb.append(" -vcodec copy");
				sb.append(" -acodec copy");
				sb.append(" ").append(rpf.relativate(dirBat, new File(dirTmp,index+"-"+counter+".mp4")));
				
				txt.add(sb.toString());
				counter++;
			}
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
	
	private void rawExtract(VideoFile vf)
	{
		String sIn = rpf.relativate(dirBat, new File(dirAvi,vf.getFileName().getValue()));
		String sH264 = rpf.relativate(dirBat, new File(dirTmp,"raw.h264"));
		String sMp3 = rpf.relativate(dirBat, new File(dirTmp,"raw.mp3"));
		
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
	
	public static synchronized String getSecond(double d)
	{
		if(df==null)
		{
			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator('.');
			df = new DecimalFormat("0.00",dfs);
		}
		return df.format(d);
	}
}