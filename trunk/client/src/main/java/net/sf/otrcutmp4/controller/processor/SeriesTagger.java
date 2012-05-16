package net.sf.otrcutmp4.controller.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.sf.ahtutils.web.rest.RestEasyPreemptiveClientExecutor;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.interfaces.rest.OtrSeriesRest;
import net.sf.otrcutmp4.model.xml.container.Otr;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.model.xml.series.Season;
import net.sf.otrcutmp4.model.xml.series.Series;
import net.sf.otrcutmp4.model.xml.series.Tag;

import org.apache.commons.configuration.Configuration;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeriesTagger
{
	final static Logger logger = LoggerFactory.getLogger(SeriesTagger.class);
	
	private OtrSeriesRest rest;
	
	public SeriesTagger(Configuration config)
	{
		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
		ClientExecutor clientExecutor = RestEasyPreemptiveClientExecutor.factory("user","pwd");
		rest = ProxyFactory.create(OtrSeriesRest.class, "http://localhost:8080/otr",clientExecutor);
	}
	
	
	public void tag(VideoFile vf)
	{
		logger.debug("Tagging "+vf.getOtrId().getKey());
		
		Series series = getSeries();
//		JaxbUtil.debug(series);
		Episode episode = getEpisode(series);
		if(episode!=null)
		{
//			JaxbUtil.debug(episode);
			Tag tag = rest.tag(episode.getId(), vf.getOtrId().getKey());
			JaxbUtil.debug(this.getClass(),tag);
		}

	}
	
	private Series getSeries()
	{
		Otr otr = rest.allSeries();
		for(int i=0;i<otr.getSeries().size();i++)
		{
			Series s = otr.getSeries().get(i);
			logger.debug(i+"\t"+s.getName()+" ("+s.getId()+")");
		}
		Scanner sc = new Scanner(System.in);
		int i = sc.nextInt();
		return otr.getSeries().get(i);
	}
	
	private Episode getEpisode(Series series)
	{
		Series xml = rest.series(series.getId());
		int i=0;
		List<Episode> list = new ArrayList<Episode>();
		for(Season season : xml.getSeason())
		{
			for(Episode episode : season.getEpisode())
			{
				logger.debug(i+" ("+season.getNr()+"/"+episode.getNr()+") "+episode.getName());i++;
				list.add(episode);
			}
		}
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		try
		{
			int index = new Integer(line);
			return list.get(index);
		}
		catch (NumberFormatException e){}
		return null;
	}
}