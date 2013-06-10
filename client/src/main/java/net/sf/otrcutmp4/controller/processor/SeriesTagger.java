package net.sf.otrcutmp4.controller.processor;

import java.io.File;
import java.io.IOException;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.ahtutils.web.rest.RestEasyPreemptiveClientExecutor;
import net.sf.otrcutmp4.AviToMp4;
import net.sf.otrcutmp4.controller.batch.video.TagGenerator;
import net.sf.otrcutmp4.controller.tagger.Mp4Tagger;
import net.sf.otrcutmp4.interfaces.rest.OtrSeriesRest;
import net.sf.otrcutmp4.model.xml.series.Video;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.OtrConfig.Credential;
import net.sf.otrcutmp4.util.OtrConfig.Dir;

import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeriesTagger
{
	final static Logger logger = LoggerFactory.getLogger(SeriesTagger.class);
	
	private OtrSeriesRest rest;
	private TagGenerator tagGenerator;
	private File dirCovers;
	
	public SeriesTagger(OtrConfig cfg, AviToMp4.Profile profile)
	{
		tagGenerator = new TagGenerator(cfg,profile,false);
		
		String host = cfg.getUrl(OtrConfig.Url.OTR);
		logger.info("Connecting to "+host);
		
		ClientExecutor clientExecutor = RestEasyPreemptiveClientExecutor.factory(
				cfg.getCredential(Credential.EMAIL,""),
				cfg.getCredential(Credential.PWD,""));
		rest = ProxyFactory.create(OtrSeriesRest.class, host,clientExecutor);
		dirCovers = cfg.getDir(Dir.COVER);
	}
	
	public void tag(long episodeId) throws UtilsProcessingException
	{
		Video video = new Video();
		video.setEpisode(rest.getEpisode(episodeId));
		
		String srcFile = tagGenerator.buildSrc(false);
		String dstFile = tagGenerator.buildDst(false,video);
		
		if(dstFile.startsWith("\"")){dstFile = dstFile.substring(1);}
		if(dstFile.endsWith("\"")){dstFile = dstFile.substring(0,dstFile.length()-1);}
		
		logger.info("Tagging "+srcFile+" to "+dstFile);
		Mp4Tagger mp4Tagger = new Mp4Tagger(dirCovers);
		try
		{
			mp4Tagger.tagEpisode(srcFile, video.getEpisode(), dstFile);
		} 
		catch (IOException e) {throw new UtilsProcessingException(e.getMessage());}
	}
}