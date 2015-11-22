package net.sf.otrcutmp4.controller.processor.mc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.api.rest.OtrVideoRest;
import de.kisner.otrcast.controller.cover.FileSystemCoverManager;
import de.kisner.otrcast.controller.processor.mc.McLibraryTagger;
import de.kisner.otrcast.factory.xml.series.XmlSeriesFactory;
import de.kisner.otrcast.interfaces.controller.TestPropertyKeys;
import de.kisner.otrcast.model.xml.container.Otr;
import de.kisner.otrcast.model.xml.series.Video;
import de.kisner.otrcast.model.xml.series.Videos;
import de.kisner.otrcast.util.OtrBootstrap;
import de.kisner.otrcast.util.OtrConfig;
import de.kisner.otrcast.util.query.io.FileQuery;
import net.sf.ahtutils.web.rest.RestUrlDelay;
import net.sf.exlp.util.io.StringUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class CliMcLibraryTagger
{
	final static Logger logger = LoggerFactory.getLogger(CliMcLibraryTagger.class);
	
	private Configuration config;
	private OtrVideoRest rest;
	
	private McLibraryTagger tagger;
	private File fLibrary,fCovers,fMcXmlLib;
	
	public CliMcLibraryTagger(Configuration config)
	{
		this.config=config;
		fLibrary = new File(config.getString(TestPropertyKeys.dirTaggerDst));
		File fTmp = new File(config.getString(TestPropertyKeys.dirTaggerTmp));
		File fBackup = new File(config.getString(TestPropertyKeys.dirMcBackup));
		fCovers  = new File(config.getString(OtrConfig.dirCover));
		
		fMcXmlLib = new File(config.getString(OtrConfig.fileMcXmlLib));
		
		if(fLibrary.listFiles(FileQuery.mp4FileFilter()).length==0)
		{
			logger.warn("No Files in directory "+fLibrary.getAbsolutePath());
			logger.info("Probably you need to create some test files with CliMp4TagWriter");
			System.exit(-1);
		}
		
		tagger = new McLibraryTagger(fTmp,fBackup);
		tagger.setCoverManager(new FileSystemCoverManager(fCovers));
	}
	
	public void scan() throws IOException
	{
		tagger.scan(fLibrary);
		tagger.saveToXml(fMcXmlLib);
	}
	
	public void checkSeries() throws FileNotFoundException
	{
		logger.info(StringUtil.stars());
		Videos videos = JaxbUtil.loadJAXB(fMcXmlLib, Videos.class);
		Set<String> set = new HashSet<String>();
		for(Video video : videos.getVideo())
		{
			if(video.isSetEpisode())
			{
				String seriesName = video.getEpisode().getSeason().getSeries().getName();
				if(!set.contains(seriesName))
				{
					set.add(seriesName);
				}
			}
		}
		logger.info("Library contains "+set.size()+" Series");
		for(String s : set)
		{
			Otr otr = getRest().resolveSeries(XmlSeriesFactory.build(s));
			if(otr.getSeries().size()!=1)
			{
				logger.info("Unknown "+s);
			}
		}
	}
	
	public void tag() throws FileNotFoundException
	{
		logger.info(StringUtil.stars());
		Videos videos = JaxbUtil.loadJAXB(fMcXmlLib, Videos.class);
		tagger.setRest(getRest());
		tagger.tagLibrary(videos);
	}
	
	private OtrVideoRest getRest()
	{
		if(rest==null)
		{
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target(RestUrlDelay.getUrl(config, OtrConfig.urlOtrSeries)); 
			rest = target.proxy(OtrVideoRest.class);
		}
		return rest;
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = OtrBootstrap.init();

		CliMcLibraryTagger cli = new CliMcLibraryTagger(config);
		cli.checkSeries();
		cli.tag();
	}
}