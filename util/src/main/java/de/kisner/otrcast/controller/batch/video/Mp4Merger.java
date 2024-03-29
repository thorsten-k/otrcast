package de.kisner.otrcast.controller.batch.video;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.exlp.cmd.file.ShellCmdCopy;
import org.exlp.util.jx.JaxbUtil;
import org.jeesl.exception.processing.UtilsProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.batch.AbstactBatchGenerator;
import de.kisner.otrcast.controller.exception.OtrInternalErrorException;
import de.kisner.otrcast.factory.txt.TxtFileNameFactoy;
import de.kisner.otrcast.factory.xml.otr.XmlOtrIdFactory;
import de.kisner.otrcast.interfaces.OtrCastInterface;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.otr.Format;
import de.kisner.otrcast.model.xml.video.Video;
import de.kisner.otrcast.util.OtrConfig;
import de.kisner.otrcast.util.OtrConfig.Dir;
import de.kisner.otrcast.util.OtrConfig.Template;
import freemarker.template.TemplateException;
import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.util.io.FilenameIllegalCharRemover;

public class Mp4Merger extends AbstactBatchGenerator
{	
	final static Logger logger = LoggerFactory.getLogger(Mp4Merger.class);
	
	public Mp4Merger(OtrConfig cfg, OtrCastInterface.Profile profile)
	{
		super(cfg, profile);
	}
	
	public List<String> merge(Video video) throws OtrInternalErrorException, UtilsProcessingException
	{
		JaxbUtil.trace(video);
		List<String> result = new ArrayList<String>();
		
		List<String> fragments = getFragments(video);

		logger.trace("Fragments:");
		for(String s : fragments){logger.trace("\t:"+s);}
		
		Format format = new Format();
		format.setType(XmlOtrIdFactory.typeAviHq);
		logger.warn("NYI Format in video");
		
		String fileName = "final.mp4";//buildFinalName(video);
		if(fragments.size()==1){result.add(mergeSingle(fragments.get(0), fileName));}
		else if(fragments.size()>1){result.add(mergeMulti(fragments,fileName,format));}
		else{logger.trace("No fragements");}
		
		return result;
	}
	
	private List<String> getFragments(Video video)
	{
		List<String> fragments = new ArrayList<String>();
		if(video.getVideoFiles().isSetVideoFile())
		{
			for(int indexVf=0;indexVf<video.getVideoFiles().getVideoFile().size();indexVf++)
			{
				VideoFile vf = video.getVideoFiles().getVideoFile().get(indexVf);
				for(int indexCut=0;indexCut<vf.getCutList().getCut().size();indexCut++)
				{
					StringBuffer sb = new StringBuffer();
					sb.append(indexVf+1);
					sb.append("-");
					sb.append(indexCut+1);
					sb.append(".mp4");
					fragments.add(sb.toString());
				}
			}
		}
		return fragments;
	}
	
	private String mergeSingle(String input, String output)
	{
		logger.info("mergeSingle");
		
		String sFrom = rpf.relativate(new File(cfg.getDir(Dir.TMP),input));
		String sTo = rpf.relativate(new File(cfg.getDir(Dir.TMP),output));
		
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
		logger.info("mergeMulti");
		StringBuffer sb = new StringBuffer();
		sb.append(cmdMp4Box).append(" ");
		
		switch(XmlOtrIdFactory.getType(format.getType()))
		{
			case hd: sb.append("-fps 50 ");break;
			default: break;
		}
		sb.append(rpf.relativate(new File(cfg.getDir(Dir.TMP),fragments.get(0))));
		sb.append(" ");
		for(int i=1;i<fragments.size();i++)
		{
			sb.append("-cat ");
			sb.append(rpf.relativate(new File(cfg.getDir(Dir.TMP),fragments.get(i))));
			sb.append(" ");
		}
		sb.append("-out ");
		sb.append(rpf.relativate(new File(cfg.getDir(Dir.TMP),output)));	
		
		return sb.toString();
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