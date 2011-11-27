package net.sf.otrcutmp4.controller.processor;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.otrcutmp4.controller.SrcDirProcessor;
import net.sf.otrcutmp4.controller.rest.RestSeriesClient;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.model.xml.series.Tags;
import net.sf.otrcutmp4.test.OtrClientTstBootstrap;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.view.cli.CliView;
import net.sf.otrcutmp4.view.interfaces.ViewInterface;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstSeriesTagger
{
	final static Logger logger = LoggerFactory.getLogger(TstSeriesTagger.class);
	
	private ViewInterface view;
	private SeriesTagger tagger;
	private VideoFiles vFiles;
	private RestSeriesClient rest;
	
	public TstSeriesTagger(Configuration config)
	{	
		view = new CliView();
		tagger = new SeriesTagger(config);
		rest = new RestSeriesClient(config);
		
		SrcDirProcessor dirProcessor = new SrcDirProcessor(view);
		vFiles = dirProcessor.readFiles(new File(config.getString(OtrConfig.dirHqAvi)),SrcDirProcessor.VideType.avi); 
	}
	
	public void tag()
	{
//		JaxbUtil.debug2(this.getClass(), vFiles, new OtrCutNsPrefixMapper());
		for(VideoFile vf : vFiles.getVideoFile())
		{
			String id = vf.getOtrId().getKey();
			Tags tags = rest.getTags(id);
			logger.debug(id+" Response: "+tags.getTag().size());
			if(tags.getTag().size()==0)
			{
				tagger.tag(vf);
			}
		}
	}
	
	public static void main(String[] args) throws ExlpConfigurationException, FileNotFoundException
	{
		Configuration config = OtrClientTstBootstrap.init();
		TstSeriesTagger rest = new TstSeriesTagger(config);
		rest.tag();	
	}
}