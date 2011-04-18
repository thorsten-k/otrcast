package net.sf.otrcutmp4;

import java.io.File;
import java.text.DecimalFormat;

import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.exlp.util.io.txt.ExlpTxtWriter;
import net.sf.otrcutmp4.data.jaxb.Cut;
import net.sf.otrcutmp4.data.jaxb.CutList;
import net.sf.otrcutmp4.data.jaxb.CutListsSelected;
import net.sf.otrcutmp4.data.jaxb.VideoFile;
import net.sf.otrcutmp4.data.jaxb.VideoFiles;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class BatchGenerator
{
	static Log logger = LogFactory.getLog(BatchGenerator.class);
	
	private File dirHqAvi,dirTmp,dirHqMp4,dirCur;
	private ExlpTxtWriter txt;
	private DecimalFormat df;
	
	private String cmdMp4Box,cmdLame,cmdFfmpeg, cmdFaac;
	private RelativePathFactory rpf;
	
	public BatchGenerator(Configuration config)
	{
		dirHqAvi = new File(config.getString(OtrConfig.dirHqAvi));
		dirTmp = new File(config.getString(OtrConfig.dirTmp));
		dirHqMp4 = new File(config.getString(OtrConfig.dirHqMp4));
		dirCur = new File(".");
		
		logger.debug("");
		logger.debug("Creating Batch in "+dirCur.getAbsolutePath());
		
		txt = new ExlpTxtWriter();
		df = new DecimalFormat("0");
		rpf = new RelativePathFactory();
		
		cmdMp4Box = rpf.relativate(dirCur, new File(dirCur,"Tools/MP4Box/MP4Box"));
		cmdLame = rpf.relativate(dirCur, new File(dirCur,"Tools/lame"));
		cmdFfmpeg = rpf.relativate(dirCur, new File(dirCur,"Tools/git-96/ffmpeg"));
		cmdFaac = rpf.relativate(dirCur, new File(dirCur,"Tools/faac"));
	}
	
	public void create(VideoFiles vFiles)
	{
		 for(VideoFile vf : vFiles.getVideoFile())
		 {
			 crateForVideo(vf);
		 }
		 txt.debug();
		 txt.writeFile(".", "bat.bat");
	}
	
	private void crateForVideo(VideoFile vf)
	{
		String sMp4 = rpf.relativate(dirCur, new File(dirTmp,"mp4.mp4"));
		
		txt.add("echo Processing: "+vf.getAvi().getValue());
		txt.add("");
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
		String sMp3 = rpf.relativate(dirCur, new File(dirTmp,"raw_audio.mp3"));
		String sAac = rpf.relativate(dirCur, new File(dirTmp,"aac.aac"));
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
		String fileName = vf.getAvi().getValue();
		if(cl.isSetFileName()){fileName=cl.getFileName().getValue();}
		
		StringBuffer sb = new StringBuffer();
		if(cl.getCut().size()>0)
		{
			sb.append(cmdMp4Box).append(" ");
			sb.append(rpf.relativate(dirCur, new File(dirTmp,index+"-1.mp4")));
			sb.append(" ");
			for(int i=2;i<=cl.getCut().size();i++)
			{
				sb.append("-cat ");
				sb.append(rpf.relativate(dirCur, new File(dirTmp,index+"-"+i+".mp4")));
				sb.append(" ");
			}
			sb.append("-out ");
			sb.append(rpf.relativate(dirCur, new File(dirHqMp4,fileName+".mp4")));
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
				String sCut = rpf.relativate(dirCur, new File(dirTmp,index+"-"+counter+".mp4"));
				txt.add(cmdFfmpeg+" -ss "+df.format(cut.getStart())+" -t "+df.format(cut.getDuration())+" -i "+sMp4+" -vcodec copy -acodec copy "+sCut);
				counter++;
			}
			
		}
	}
	
	private void createMp4(String sMp4)
	{
		String sH264 = rpf.relativate(dirCur, new File(dirTmp,"raw.h264"));
		String sAac = rpf.relativate(dirCur, new File(dirTmp,"aac.aac"));
		
		txt.add(cmdMp4Box+" -add "+sH264+" -add "+sAac+" "+sMp4);
	}
	
	private void rawExtract(VideoFile vf)
	{
		String sIn = rpf.relativate(dirCur, new File(dirHqAvi,vf.getAvi().getValue()));
		String sH264 = rpf.relativate(dirCur, new File(dirTmp,"raw.h264"));
		String sMp3 = rpf.relativate(dirCur, new File(dirTmp,"raw.h264"));
		
		txt.add(cmdMp4Box+" -aviraw video "+sIn+" -out "+sH264);
		txt.add(cmdMp4Box+" -aviraw audio "+sIn+" -out "+sMp3);
	}
}