package net.sf.otrcutmp4.controller.batch;

import java.io.File;
import java.io.IOException;

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
import net.sf.otrcutmp4.controller.batch.video.H264Transcode;
import net.sf.otrcutmp4.controller.batch.video.VideoCutter;
import net.sf.otrcutmp4.controller.exception.OtrInternalErrorException;
import net.sf.otrcutmp4.controller.factory.FileNameFactoy;
import net.sf.otrcutmp4.controller.factory.xml.otr.XmlOtrIdFactory;
import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.model.xml.series.Video;
import net.sf.otrcutmp4.model.xml.series.Videos;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.OtrConfig.Dir;
import net.sf.otrcutmp4.util.OtrConfig.Template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.TemplateException;

public class BatchGenerator extends AbstactBatchGenerator
{
	final static Logger logger = LoggerFactory.getLogger(BatchGenerator.class);
	
	private AviExtract rawExtract;
	private VideoCutter videoCutter;
	
	private Mp3ToAac mp3ToAac;
	private Ac3ToAac ac3ToAac;
	private H264Transcode h264Transcode;
	
	private ExlpTxtWriter txt;
	
	public BatchGenerator(OtrConfig cfg,AviToMp4.Profile profile) throws OtrInternalErrorException
	{
		super(cfg,profile);
		
		mp3ToAac = new Mp3ToAac(cfg,profile);
		ac3ToAac = new Ac3ToAac(cfg,profile);
		h264Transcode = new H264Transcode(cfg,profile);
		rawExtract = new AviExtract(cfg,profile);
		videoCutter = new VideoCutter(cfg,profile);
		
		logger.debug("");
		logger.debug("Creating Batch in "+cfg.getDir(Dir.BAT).getAbsolutePath());
		
		txt = new ExlpTxtWriter();
	}
	
	public void build(Videos videos) throws OtrInternalErrorException
	{
		for(Video video : videos.getVideo())
		{
			try {build(video);}
			catch (UtilsProcessingException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		txt.debug();
		File f = new File(cfg.getDir(Dir.BAT),"cut.bat");
		txt.writeFile(f);
		logger.info("");
		logger.info("Batch file written to: "+rpf.relativate(new File("."), f));
	}
	
	private void build(Video video) throws OtrInternalErrorException, UtilsProcessingException
	{
		if(!video.isSetVideoFiles() || !video.getVideoFiles().isSetVideoFile())
		{
			 txt.add("echo No Cutlist available for video: "+video);
			 txt.add("");
		}
		if(video.getVideoFiles().getVideoFile().size()!=1)
		{
			throw new OtrInternalErrorException("Multiple files currently not supported");
		}
		crateForVideo(video,profile);
	}
	
	@Deprecated
	public void create(VideoFiles vFiles) throws OtrInternalErrorException
	{
		 logger.error("Deprecated");
	}
	
	private void crateForVideo(Video video, AviToMp4.Profile profile) throws OtrInternalErrorException, UtilsProcessingException
	{
		txt.add("echo Processing: "+video);
		txt.add("");
		
		try {txt.add(ShellCmdRm.rmDir(rpf.relativate(cfg.getDir(Dir.TMP)), true));}
		catch (ExlpUnsupportedOsException e) {logger.error("",e);}
	
		extract(video);
		transcode(video);
//		cut(vf,profile);
//		merge(vf);
		
		txt.add("");
		txt.add("");
	}
	
	private void extract(Video video) throws OtrInternalErrorException, UtilsProcessingException
	{
		switch(profile)
		{
			case P0: txt.add(rawExtract.rawExtract(video));break;
		}	
		
		logger.warn("No AC3 processing available!");
/*		if(vf.getOtrId().getFormat().isAc3())
		{
			txt.add(ac3ToAac.extract(vf));
		}
		else
*/		{
			txt.add(mp3ToAac.extract(video));
		}
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
		if(cl.isSetVideo() && cl.getVideo().get(0).isSetEpisode())
		{
			
			try
			{
				FileNameFactoy fnf = new FileNameFactoy();
				fnf.initTemplate(cfg.getTemplate(Template.fnSeries));
				fileName = fnf.convert(cl.getVideo().get(0).getEpisode());
			}
			catch (TemplateException e) {throw new UtilsProcessingException(e.getMessage());}
			catch (IOException e) {throw new UtilsProcessingException(e.getMessage());}
			
/*			Episode xmlEpisode = cl.getVideo().get(0).getEpisode();
			StringBuffer sb = new StringBuffer();
			sb.append("S").append(xmlEpisode.getSeason().getNr()).append("E").append(xmlEpisode.getNr()).append("-");
			sb.append(xmlEpisode.getName());
			sb.append("-").append(xmlEpisode.getSeason().getSeries().getName());
			fileName = sb.toString();
*/		}
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
	
	private void transcode(Video video) throws UtilsProcessingException, OtrInternalErrorException
	{
		logger.warn("No AC3 handling");
		txt.add(mp3ToAac.transcode(video));
		txt.add(h264Transcode.transcode(video));
	}	
}