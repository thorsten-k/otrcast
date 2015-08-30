package de.kisner.otrcast.controller.batch.video;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.shell.cmd.ShellCmdMove;
import net.sf.exlp.util.io.FilenameIllegalCharRemover;
import net.sf.exlp.util.xml.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.app.AviToMp4;
import de.kisner.otrcast.controller.batch.AbstactBatchGenerator;
import de.kisner.otrcast.factory.txt.TxtFileNameFactoy;
import de.kisner.otrcast.model.xml.series.Episode;
import de.kisner.otrcast.model.xml.series.Video;
import de.kisner.otrcast.util.OtrConfig;
import de.kisner.otrcast.util.OtrConfig.Cmd;
import de.kisner.otrcast.util.OtrConfig.Dir;
import de.kisner.otrcast.util.OtrConfig.Template;
import freemarker.template.TemplateException;

public class TagGenerator extends AbstactBatchGenerator
{	
	final static Logger logger = LoggerFactory.getLogger(TagGenerator.class);
	
	private boolean tagMp4;
	
	public TagGenerator(OtrConfig cfg, AviToMp4.Profile profile,boolean tagMp4)
	{
		super(cfg, profile);
		this.tagMp4=tagMp4;
	}
	
	public List<String> mvToMp4(Video video) throws UtilsProcessingException
	{
		String sFrom = buildSrc();
		String sTo = buildDst(video);
		
		if(tagMp4)
		{
			if(video.isSetEpisode() && video.getEpisode().isSetId())
			{
				return tag(video.getEpisode());
			}
			else
			{
				JaxbUtil.info(video);
				
				logger.warn("Tagging is selected, but we don't have an episode and/or id");
			}
		}
		return move(sFrom,sTo);
	}
	
	public String buildSrc(){return buildSrc(true);}
	public String buildSrc(boolean relativate)
	{
		File srcFile = new File(cfg.getDir(Dir.TMP),"final.mp4");
		if(relativate){return rpf.relativate(srcFile);}
		else {return srcFile.getAbsolutePath();}
	}
	
	public String buildDst(Video video) throws UtilsProcessingException{return buildDst(true,video);}
	public String buildDst(boolean relativate, Video video) throws UtilsProcessingException
	{
		File dstFile = new File(cfg.getDir(Dir.MP4),buildFinalName(video));
		if(relativate){return rpf.relativate(dstFile);}
		else {return dstFile.getAbsolutePath();}
	}
	
	public List<String> tag(Episode episode) throws UtilsProcessingException
	{
		StringBuffer sb = new StringBuffer();
		sb.append(cfg.getCmd(Cmd.TAGGER));
		sb.append(" -tagMp4 ");
		sb.append(episode.getId());
		
		List<String> result = new ArrayList<String>();
		result.add(sb.toString());
		return result;
	}
	
	public List<String> move(String sFrom, String sTo) throws UtilsProcessingException
	{
		List<String> result = new ArrayList<String>();
			
		try
		{
			result.add(ShellCmdMove.moveFile(sFrom, sTo));
		}
		catch (ExlpUnsupportedOsException e) {throw new UtilsProcessingException(e.getMessage());}
	
		return result;
	}
		
	public String buildFinalName(Video video) throws UtilsProcessingException
	{
		logger.trace("buildFinalName");
		JaxbUtil.trace(video);
		String fileName;
		if(video.isSetEpisode())
		{
			try
			{
				TxtFileNameFactoy fnf = new TxtFileNameFactoy();
				fnf.initTemplate(cfg.getTemplate(Template.fnSeries));
				fileName = fnf.convert(video.getEpisode())+".mp4";
			}
			catch (TemplateException e) {throw new UtilsProcessingException(e.getMessage());}
			catch (IOException e) {throw new UtilsProcessingException(e.getMessage());}
			
		}
		else if(video.isSetMovie())
		{
			logger.trace("build Movie Name");
			fileName=video.getMovie().getName()+".mp4";
		}
		else
		{
			logger.warn("Unknown type");
			fileName="Unknown";
		}
		return FilenameIllegalCharRemover.convert(fileName); 
	}
}