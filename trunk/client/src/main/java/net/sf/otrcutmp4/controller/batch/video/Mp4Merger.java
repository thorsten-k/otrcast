package net.sf.otrcutmp4.controller.batch.video;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.exlp.util.exception.ExlpUnsupportedOsException;
import net.sf.exlp.util.io.FilenameIllegalCharRemover;
import net.sf.exlp.util.os.shell.ShellCmdCopy;
import net.sf.otrcutmp4.AviToMp4;
import net.sf.otrcutmp4.controller.batch.AbstactBatchGenerator;
import net.sf.otrcutmp4.controller.exception.OtrInternalErrorException;
import net.sf.otrcutmp4.controller.factory.FileNameFactoy;
import net.sf.otrcutmp4.controller.factory.xml.otr.XmlOtrIdFactory;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.otr.Format;
import net.sf.otrcutmp4.model.xml.series.Video;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.OtrConfig.Dir;
import net.sf.otrcutmp4.util.OtrConfig.Template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.TemplateException;

public class Mp4Merger extends AbstactBatchGenerator
{	
	final static Logger logger = LoggerFactory.getLogger(Mp4Merger.class);
	
	public Mp4Merger(OtrConfig cfg, AviToMp4.Profile profile)
	{
		super(cfg, profile);
	}
	
	public List<String> merge(Video video) throws OtrInternalErrorException, UtilsProcessingException
	{
		List<String> result = new ArrayList<String>();
		
		List<String> fragments = getFragments(video);

		logger.debug("Fragments:");
		for(String s : fragments){logger.debug("\t:"+s);}
		
		Format format = new Format();
		format.setType(XmlOtrIdFactory.typeAviHq);
		logger.warn("NYI Format in video");
		
		String fileName = buildFinalName(video);
		if(fragments.size()==1){mergeSingle(fragments.get(0), fileName);}
		else{result.add(mergeMulti(fragments,fileName,format));}
		
		return result;
	}
	
	private List<String> getFragments(Video video)
	{
		List<String> fragments = new ArrayList<String>();
		int indexVf=1;
		for(VideoFile vf : video.getVideoFiles().getVideoFile())
		{
			StringBuffer sb = new StringBuffer();
			sb.append("mp4-").append(indexVf);
			sb.append(".").append(1);
			sb.append(".mp4");
			fragments.add(sb.toString());
			indexVf++;
		}
		return fragments;
	}
	
	private String mergeSingle(String input, String output)
	{
		String sFrom = rpf.relativate(new File(cfg.getDir(Dir.TMP),input));
		String sTo = rpf.relativate(new File(cfg.getDir(Dir.MP4),output));
		
		try {return ShellCmdCopy.copyFile(sFrom, sTo);}
		catch (ExlpUnsupportedOsException e)
		{
			logger.error("",e);
			logger.error("File was not copied! ");
			logger.error("\tFrom: "+sFrom);
			logger.error("\tTo  : "+sTo);
			return "echo ERROR";
		}
	}
	
	private String mergeMulti(List<String> fragments, String output, Format format) throws UtilsProcessingException
	{
		StringBuffer sb = new StringBuffer();
		sb.append(cmdMp4Box).append(" ");
		
		
		switch(XmlOtrIdFactory.getType(format.getType()))
		{
			case hd: sb.append("-fps 50 ");break;
			default: break;
		}
		sb.append(rpf.relativate(new File(cfg.getDir(Dir.TMP),fragments.get(0))));
		sb.append(" ");
		for(int i=2;i<=fragments.size();i++)
		{
			sb.append("-cat ");
			sb.append(rpf.relativate(new File(cfg.getDir(Dir.TMP),fragments.get(i))));
			sb.append(" ");
		}
		sb.append("-out ");
		sb.append(rpf.relativate(new File(cfg.getDir(Dir.MP4),output)));	
		
		return sb.toString();
	}
		
	public String buildFinalName(Video video) throws UtilsProcessingException
	{
		String fileName;
		if(video.isSetEpisode())
		{
			try
			{
				FileNameFactoy fnf = new FileNameFactoy();
				fnf.initTemplate(cfg.getTemplate(Template.fnSeries));
				fileName = fnf.convert(video.getEpisode());
			}
			catch (TemplateException e) {throw new UtilsProcessingException(e.getMessage());}
			catch (IOException e) {throw new UtilsProcessingException(e.getMessage());}
			
		}
		else if(video.isSetMovie())
		{
			logger.warn("build Movie Name");
			fileName="xx";
		}
		else
		{
			logger.warn("Unknown type");
			fileName="Unknown";
		}
		return FilenameIllegalCharRemover.convert(fileName); 
	}
}