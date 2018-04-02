package de.kisner.otrcast.controller.media;

import java.io.IOException;

import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.api.rest.OtrVideoRest;
import de.kisner.otrcast.controller.batch.video.TagGenerator;
import de.kisner.otrcast.controller.tag.writer.SeriesTagWriter;
import de.kisner.otrcast.interfaces.OtrCastInterface;
import de.kisner.otrcast.interfaces.controller.CoverManager;
import de.kisner.otrcast.interfaces.rest.OtrUserRest;
import de.kisner.otrcast.model.xml.video.Video;
import de.kisner.otrcast.util.OtrConfig;
import de.kisner.otrcast.util.OtrConfig.Credential;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;

public class SeriesTagger
{
	final static Logger logger = LoggerFactory.getLogger(SeriesTagger.class);
	
	private OtrVideoRest rest;
	private OtrUserRest restUser;
	private TagGenerator tagGenerator;
	private CoverManager coverManager;
	
	public SeriesTagger(OtrConfig cfg, OtrCastInterface.Profile profile, CoverManager coverManager)
	{
		this.coverManager=coverManager;
		tagGenerator = new TagGenerator(cfg,profile,false,false);
		
		String host = cfg.getUrl(OtrConfig.Url.OTR);
		logger.info("Connecting to "+host);
		
		ResteasyClient client = new ResteasyClientBuilder().build();
		client.register(new BasicAuthentication(cfg.getCredential(Credential.EMAIL,""),cfg.getCredential(Credential.PWD,"")));
		ResteasyWebTarget restTarget = client.target(host);
        rest = restTarget.proxy(OtrVideoRest.class);
        restUser = restTarget.proxy(OtrUserRest.class);
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
		SeriesTagWriter mp4Tagger = new SeriesTagWriter(coverManager);
		try
		{
			mp4Tagger.tagEpisode(srcFile, video.getEpisode(), dstFile);
		} 
		catch (IOException e) {throw new UtilsProcessingException(e.getMessage());}
	}
	
	public void processed(long cutlistId)
	{
		restUser.processedCutlist(cutlistId);
	}
}