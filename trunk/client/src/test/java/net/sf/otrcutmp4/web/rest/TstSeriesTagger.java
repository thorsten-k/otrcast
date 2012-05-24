package net.sf.otrcutmp4.web.rest;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.web.rest.RestEasyPreemptiveClientExecutor;
import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.otrcutmp4.controller.processor.SeriesTagger;
import net.sf.otrcutmp4.controller.processor.SrcDirProcessor;
import net.sf.otrcutmp4.interfaces.rest.OtrSeriesRest;
import net.sf.otrcutmp4.interfaces.view.ViewSrcDirProcessor;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.model.xml.series.Tags;
import net.sf.otrcutmp4.test.OtrClientTstBootstrap;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.view.cli.CliSrcDirProcessorView;

import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstSeriesTagger
{
	final static Logger logger = LoggerFactory.getLogger(TstSeriesTagger.class);
	
	private ViewSrcDirProcessor view;
	private SeriesTagger tagger;
	private VideoFiles vFiles;
	private OtrSeriesRest rest;
	
	public TstSeriesTagger(Configuration config)
	{	
		view = new CliSrcDirProcessorView();
		tagger = new SeriesTagger(config);
		
		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
		ClientExecutor clientExecutor = RestEasyPreemptiveClientExecutor.factory("user","pwd");
		rest = ProxyFactory.create(OtrSeriesRest.class, "http://localhost:8080/otr",clientExecutor);
		
		SrcDirProcessor dirProcessor = new SrcDirProcessor(view);
		vFiles = dirProcessor.readFiles(new File(config.getString(OtrConfig.dirAvi))); 
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