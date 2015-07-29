package de.kisner.otrcast.factory.txt;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.series.Tag;
import de.kisner.otrcast.model.xml.series.Tags;

public class TxtDsFactory
{
	public static enum Key {seriesName,seriesKey,seasonNr,seasonName,episodeName,episodeNr}
	final static Logger logger = LoggerFactory.getLogger(TxtDsFactory.class);
	
	private Set<String> seriesName;
	private Set<String> seriesKey;
	private Set<String> seasonName;
	private Set<String> seasonNr;
	private Set<String> episodeName;
	private Set<String> episodeNr;
	
	public TxtDsFactory()
	{
		seriesName = new HashSet<String>();
		seriesKey = new HashSet<String>();
		seasonName = new HashSet<String>();
		seasonNr = new HashSet<String>();
		episodeName = new HashSet<String>();
		episodeNr = new HashSet<String>();
	}
	
	public Map<String,String> build(Tags tags)
	{
		for(Tag tag : tags.getTag())
		{
			seriesName.add(tag.getEpisode().getSeason().getSeries().getName());
//			seriesKey.add(tag.getEpisode().getSeason().getSeries())
			seasonName.add(tag.getEpisode().getSeason().getName());
			seasonNr.add(tag.getEpisode().getSeason().getNr()+"");
			episodeName.add(tag.getEpisode().getName());
			episodeNr.add(tag.getEpisode().getNr()+"");
		}
		
		
		Map<String,String> ds = new Hashtable<String,String>();
		ds.put(TxtDsFactory.Key.seriesName.toString(), merge(seriesName));
		ds.put(TxtDsFactory.Key.seriesKey.toString(), merge(seriesKey));
		ds.put(TxtDsFactory.Key.seasonNr.toString(), merge(seasonNr));
		ds.put(TxtDsFactory.Key.seasonName.toString(), merge(seasonName));
		ds.put(TxtDsFactory.Key.episodeName.toString(), merge(episodeName));
		ds.put(TxtDsFactory.Key.episodeNr.toString(), merge(episodeNr));
		
		return ds;
	}
	
	private String merge(Set<String> set)
	{
		StringBuffer sb = new StringBuffer();
		
		Iterator<String> i = set.iterator();
		while(i.hasNext())
		{
			sb.append(i.next());
			sb.append(",");
		}
		
		String result;
		if(sb.length()>0){result=sb.toString().substring(0,sb.length()-1);}
		else{result=sb.toString();}
		
		return result;
	}
}
