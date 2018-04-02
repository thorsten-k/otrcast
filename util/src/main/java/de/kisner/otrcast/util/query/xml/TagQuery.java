package de.kisner.otrcast.util.query.xml;

import de.kisner.otrcast.model.xml.otr.OtrId;
import de.kisner.otrcast.model.xml.video.tv.Tag;
import de.kisner.otrcast.util.query.xml.XmlTvQuery;

public class TagQuery
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
	public static Tag tag()
	{
		Tag xml = new Tag();

		OtrId id = new OtrId();
		id.setKey("");
		xml.setOtrId(id);
		
		xml.setEpisode(XmlTvQuery.episodeWithSeasonandSeries());
    	
    	return xml;
	}
	
}
