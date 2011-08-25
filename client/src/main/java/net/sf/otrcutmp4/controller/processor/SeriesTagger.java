package net.sf.otrcutmp4.controller.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.rest.RestSeriesClient;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.otr.Otr;
import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.model.xml.series.Season;
import net.sf.otrcutmp4.model.xml.series.Series;
import net.sf.otrcutmp4.model.xml.series.Tag;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SeriesTagger
{
	static Log logger = LogFactory.getLog(SeriesTagger.class);
	
	private RestSeriesClient rest;
	
	public SeriesTagger(Configuration config)
	{
		rest = new RestSeriesClient(config);
	}
	
	
	public void tag(VideoFile vf)
	{
		logger.debug("Tagging "+vf.getOtrId().getName());
		
		Series series = getSeries();
//		JaxbUtil.debug(series);
		Episode episode = getEpisode(series);
		if(episode!=null)
		{
//			JaxbUtil.debug(episode);
			Tag tag = rest.tag(episode.getId(), vf.getOtrId().getName());
			JaxbUtil.debug(tag);
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