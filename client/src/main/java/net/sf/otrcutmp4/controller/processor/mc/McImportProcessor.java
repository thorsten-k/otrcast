package net.sf.otrcutmp4.controller.processor.mc;

import java.io.File;

import javax.persistence.EntityManager;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.file.GenericFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.facade.OtrMediacenterFacadeBean;
import net.sf.otrcutmp4.controller.hotfolder.McTargetFactory;
import net.sf.otrcutmp4.controller.tag.reader.Mp4TagReader;
import net.sf.otrcutmp4.model.OtrEpisode;
import net.sf.otrcutmp4.model.OtrImage;
import net.sf.otrcutmp4.model.OtrMovie;
import net.sf.otrcutmp4.model.OtrSeason;
import net.sf.otrcutmp4.model.OtrSeries;
import net.sf.otrcutmp4.model.OtrStorage;
import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.model.xml.series.Video;
import net.sf.otrcutmp4.util.OtrConfig;

public class McImportProcessor implements Processor
{
	final static Logger logger = LoggerFactory.getLogger(McImportProcessor.class);

	private EntityManager em;
	private Mp4TagReader tagReader;
	private McTargetFactory mcTarget;
	
	private OtrMediacenterFacadeBean<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage> fOtrMc;
	
	public McImportProcessor(OtrConfig config,EntityManager em)
	{
		this.em=em;
		tagReader = new Mp4TagReader(false);
		mcTarget = new McTargetFactory(config);
		fOtrMc = new OtrMediacenterFacadeBean<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage>(em);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void process(Exchange exchange) throws Exception
	{
		GenericFile camelFile = (GenericFile)exchange.getIn().getBody();
		File file = (File)camelFile.getFile();
			
		logger.info("MC Inbound Queue: "+file);
		Video video = tagReader.read(file);
		JaxbUtil.info(video);
		
		em.getTransaction().begin();
		
		if(video.isSetEpisode())
		{
			handleEpisode(video.getEpisode());
		}
		else if(video.isSetMovie())
		{
			logger.warn("NYI Handling of video.movie");
			logger.trace("\t"+video.getMovie().getName());
//			handleMovie(video.getMovie());
		}
		
		em.getTransaction().commit();
	}
	
	private void handleEpisode(Episode xmlEpisode)
	{
		OtrEpisode episode = fOtrMc.fcEpisode(OtrSeries.class, OtrSeason.class, OtrEpisode.class, OtrImage.class, xmlEpisode);
		logger.info("Persisted Episode "+episode.getId());
		mcTarget.getTarget();
		
	}
}