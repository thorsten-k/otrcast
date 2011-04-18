package net.sf.otrcutmp4.batch;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import net.sf.exlp.util.exception.ExlpUnsupportedOsException;
import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.exlp.util.io.txt.ExlpTxtWriter;
import net.sf.exlp.util.os.shell.ShellCmdCopy;
import net.sf.exlp.util.os.shell.ShellCmdRm;
import net.sf.otrcutmp4.data.jaxb.Cut;
import net.sf.otrcutmp4.data.jaxb.CutList;
import net.sf.otrcutmp4.data.jaxb.CutListsSelected;
import net.sf.otrcutmp4.data.jaxb.VideoFile;
import net.sf.otrcutmp4.data.jaxb.VideoFiles;
import net.sf.otrcutmp4.util.OtrConfig;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CutGenerator
{
	static Log logger = LogFactory.getLog(CutGenerator.class);
	
	private File dirHqAvi,dirTmp,dirHqMp4,dirTools,dirBat;
	private ExlpTxtWriter txt;
	private static DecimalFormat df;
	
	private String cmdMp4Box,cmdLame,cmdFfmpeg, cmdFaac;
	private RelativePathFactory rpf;
	
	private ShellCmdCopy shellCopy;
	private ShellCmdRm shellRm;
	
	public CutGenerator(Configuration config)
	{
		shellCopy = new ShellCmdCopy();
		shellRm = new ShellCmdRm();
		
		dirHqAvi = new File(config.getString(OtrConfig.dirHqAvi));
		dirTmp = new File(config.getString(OtrConfig.dirTmp));
		dirHqMp4 = new File(config.getString(OtrConfig.dirHqMp4));
		dirTools = new File(config.getString(OtrConfig.dirTools));
		dirBat = new File(config.getString(OtrConfig.dirBat));

		
		logger.debug("");
		logger.debug("Creating Batch in "+dirBat.getAbsolutePath());
		
		txt = new ExlpTxtWriter();
		rpf = new RelativePathFactory();
		
		cmdMp4Box = rpf.relativate(dirBat, new File(dirTools,config.getString(OtrConfig.toolMp4Box)));
		cmdLame = rpf.relativate(dirBat, new File(dirTools,config.getString(OtrConfig.toolLame)));
		cmdFfmpeg = rpf.relativate(dirBat, new File(dirTools,config.getString(OtrConfig.toolFfmpeg)));
		cmdFaac = rpf.relativate(dirBat, new File(dirTools,config.getString(OtrConfig.toolFaac)));
	}
	
	public void create(VideoFiles vFiles)
	{
		 for(VideoFile vf : vFiles.getVideoFile())
		 {
			 crateForVideo(vf);
		 }
		 txt.debug();

		 File f = new File(dirBat, "bat.bat");
		 txt.writeFile(f);
		 logger.info("");
		 logger.info("Batch file written to: "+rpf.relativate(new File("."), f));
	}
	
	private void crateForVideo(VideoFile vf)
	{
		String sMp4 = rpf.relativate(dirBat, new File(dirTmp,"mp4.mp4"));
		
		txt.add("echo Processing: "+vf.getFileName().getValue());
		txt.add("");
		try {txt.add(shellRm.rmDir(rpf.relativate(dirBat, dirTmp), true));}
		catch (ExlpUnsupportedOsException e) {logger.error(e);}
		rawExtract(vf);
		aac();
		createMp4(sMp4);
		cutList(vf.getCutListsSelected(),sMp4);
		mergeMp4(vf.getCutListsSelected(),vf);
		txt.add("");
		txt.add("");
	}
	
	private void aac()
	{
		String sMp3 = rpf.relativate(dirBat, new File(dirTmp,"raw_audio.mp3"));
		String sAac = rpf.relativate(dirBat, new File(dirTmp,"aac.aac"));
		txt.add(cmdLame+" --decode "+sMp3+" - | "+cmdFaac+" --mpeg-vers 4 -b 192 -o "+sAac+" -");
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
		if(cl.isSetFileName()){fileName=cl.getFileName().getValue();}
		
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
	
	private void createMp4(String sMp4)
	{
		String sH264 = rpf.relativate(dirBat, new File(dirTmp,"raw_video.h264"));
		String sAac = rpf.relativate(dirBat, new File(dirTmp,"aac.aac"));
		
		txt.add(cmdMp4Box+" -add "+sH264+" -add "+sAac+" "+sMp4);
	}
	
	private void rawExtract(VideoFile vf)
	{
		String sIn = rpf.relativate(dirBat, new File(dirHqAvi,vf.getFileName().getValue()));
		String sH264 = rpf.relativate(dirBat, new File(dirTmp,"raw.h264"));
		String sMp3 = rpf.relativate(dirBat, new File(dirTmp,"raw.mp3"));
		
		txt.add(cmdMp4Box+" -aviraw video "+sIn+" -out "+sH264);
		txt.add(cmdMp4Box+" -aviraw audio "+sIn+" -out "+sMp3);
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