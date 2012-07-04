package net.sf.otrcutmp4.controller.factory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import net.sf.otrcutmp4.controller.factory.txt.TxtDsFactory;
import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.model.xml.series.Season;
import net.sf.otrcutmp4.model.xml.series.Series;
import net.sf.otrcutmp4.test.AbstractClientTest;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.TemplateException;

public class TestFileNameFactory extends AbstractClientTest
{ 
	final static Logger logger = LoggerFactory.getLogger(TestFileNameFactory.class);
	
	private FileNameFactoy fnf;
	private Map<String,String>  ds;
	
	private static String seriesName = "My Series";
	private static String seriesKey = "KEY";
	private static int seasonNr = 1;
	private static String seasonName = "The beginning";
	private static String episodeName = "Pilot";
	private static int episodeNr = 2;
	
	@Before
	public void init()
	{
		fnf = new FileNameFactoy();
		
		ds = new HashMap<String,String>();
		ds.put(TxtDsFactory.Key.seriesName.toString(), seriesName);
		ds.put(TxtDsFactory.Key.seriesKey.toString(), seriesKey);
		ds.put(TxtDsFactory.Key.seasonNr.toString(), ""+seasonNr);
		ds.put(TxtDsFactory.Key.seasonName.toString(), seasonName);
		ds.put(TxtDsFactory.Key.episodeName.toString(), episodeName);
		ds.put(TxtDsFactory.Key.episodeNr.toString(), ""+episodeNr);
	}
	
	@Test
	public void simple() throws IOException, TemplateException
	{
		String template = "${seriesKey} ${seasonNr}x${episodeNr} ${episodeName}";
		fnf.initTemplate(template);
		String actual = fnf.convert(ds);
		String expected = seriesKey+" "+seasonNr+"x"+episodeNr+" "+episodeName;
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void xmlEpisode() throws IOException, TemplateException
	{
		String template = "${seriesKey} ${seasonNr}x${episodeNr} ${episodeName}";
		fnf.initTemplate(template);
		
		Series series = new Series();
		series.setName(seriesName);
		series.setKey(seriesKey);
		
		Season season = new Season();
		season.setSeries(series);
		season.setName(seasonName);
		season.setNr(seasonNr);
		
		Episode episode = new Episode();
		episode.setNr(episodeNr);
		episode.setName(episodeName);
		episode.setSeason(season);
		
		String actual = fnf.convert(episode);
		String expected = seriesKey+" "+seasonNr+"x"+episodeNr+" "+episodeName;
		Assert.assertEquals(expected, actual);
	}
}