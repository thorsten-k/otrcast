package de.kisner.otrcast.controller.media;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.configuration.Configuration;
import org.exlp.util.io.StringUtil;
import org.exlp.util.jx.JaxbUtil;
import org.jeesl.controller.handler.io.log.LoggedExit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.api.rest.OtrVideoRest;
import de.kisner.otrcast.app.OtrCastApp;
import de.kisner.otrcast.controller.OtrCastBootstrap;
import de.kisner.otrcast.controller.cover.FileSystemCoverManager;
import de.kisner.otrcast.factory.txt.TxtEpisodeFactory;
import de.kisner.otrcast.factory.txt.TxtSeriesFactory;
import de.kisner.otrcast.factory.xml.video.tv.XmlSeriesFactory;
import de.kisner.otrcast.interfaces.controller.TestPropertyKeys;
import de.kisner.otrcast.model.xml.container.Otr;
import de.kisner.otrcast.model.xml.video.Video;
import de.kisner.otrcast.model.xml.video.Videos;
import de.kisner.otrcast.model.xml.video.tv.Episode;
import de.kisner.otrcast.util.OtrConfig;
import de.kisner.otrcast.util.query.io.FileQuery;

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
		rest = OtrCastApp.rest(config,OtrVideoRest.class);
		
		fLibrary = new File(config.getString(TestPropertyKeys.dirTaggerDst));
		File fTmp = new File(config.getString(TestPropertyKeys.dirTaggerTmp));
		File fBackup = new File(config.getString(TestPropertyKeys.dirMcBackup));
		fCovers  = new File(config.getString(OtrConfig.dirCover));
		
		fMcXmlLib = new File(config.getString(OtrConfig.fileMcXmlLib));
		
		tagger = new McLibraryTagger(fTmp,fBackup);
		tagger.setCoverManager(new FileSystemCoverManager(fCovers));
	}
	
	public void scan() throws IOException
	{
		if(fLibrary.listFiles(FileQuery.mp4FileFilter()).length==0)
		{
			logger.warn("No Files in directory "+fLibrary.getAbsolutePath());
			logger.info("Probably you need to create some test files with CliMp4TagWriter");
			LoggedExit.silent(true);
		}
		
		tagger.scan(fLibrary);
		tagger.saveToXml(fMcXmlLib);
	}
	
	public void checkSeries() throws FileNotFoundException
	{
		logger.info(StringUtil.stars());
		logger.info("Using metadata file: "+fMcXmlLib.getPath());
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
			Otr otr = rest.resolveSeries(XmlSeriesFactory.build(s));
			if(otr.getSeries().size()!=1)
			{
				logger.warn("Unknown "+s);
			}
		}
	}
	
	public void checkEpisode() throws FileNotFoundException
	{
		logger.info(StringUtil.stars());
		logger.info("Using metadata file: "+fMcXmlLib.getPath());
		Videos videos = JaxbUtil.loadJAXB(fMcXmlLib, Videos.class);
		for(Video video : videos.getVideo())
		{
			if(video.isSetEpisode())
			{
				Episode eMc = video.getEpisode();
				Otr otrEpisode = rest.resolveEpisode(eMc);
				if(otrEpisode.getEpisode().size()!=1)
				{
					Otr otr = rest.resolveSeries(XmlSeriesFactory.build(eMc.getSeason().getSeries().getName()));
					if(otr.getSeries().size()!=1)
					{
						logger.warn("Unknown Series "+TxtSeriesFactory.build(eMc));
					}
					else if(otr.getSeries().size()==1)
					{
						logger.info(StringUtil.stars());
						logger.info(TxtEpisodeFactory.build(eMc, true));
						Episode eOtr = rest.getEpisode(otr.getSeries().get(0).getId(), eMc.getSeason().getNr(), eMc.getNr());
						logger.info(TxtEpisodeFactory.build(eOtr, true));
					}
				}
			}
		}
	}
	
	public void tag() throws FileNotFoundException
	{
		logger.info(StringUtil.stars());
		Videos videos = JaxbUtil.loadJAXB(fMcXmlLib, Videos.class);
		tagger.setRest(OtrCastApp.rest(config,OtrVideoRest.class));
		tagger.tagLibrary(videos);
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = OtrCastBootstrap.init();

		CliMcLibraryTagger cli = new CliMcLibraryTagger(config);
//		cli.checkSeries();
		cli.checkEpisode();
//		cli.tag();
	}
}