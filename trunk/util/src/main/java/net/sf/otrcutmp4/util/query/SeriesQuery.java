package net.sf.otrcutmp4.util.query;

import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.model.xml.series.Season;
import net.sf.otrcutmp4.model.xml.series.Series;

public class SeriesQuery
{
	public static enum QueryKey {ProjectStatus, Project, ProjectWithCountry}
/*	
	private static Map<QueryKey,AhtQuery> mQueries;
	
	public static AhtQuery get(QueryKey key)
	{
		if(mQueries==null){mQueries = new Hashtable<QueryKey,AhtQuery>();}
		if(!mQueries.containsKey(key))
		{
			AhtQuery q = new AhtQuery();
	    	q.setLang("de");
	    	
			switch(key)
			{
				case ProjectStatus: q.setProjectStatus(createProjectStatus());mQueries.put(key, q);break;
				case Project: mQueries.put(key, createProject());break;
				case ProjectWithCountry: mQueries.put(key, createProjectWithCountry());break;
			}
		}
		
		return mQueries.get(key);
	}
*/	
	public static Episode episodeInfo()
	{
		Series series = new Series();
		series.setName("");
		series.setKey("");
		
		Season season = new Season();
		season.setNr(0);
		season.setName("");
		season.setSeries(series);
		
		Episode episode = new Episode();
		episode.setId(0);
		episode.setNr(0);
		episode.setName("");
		episode.setSeason(season);
		
    	return episode;
	}
	
	
}
