package de.kisner.otrcast.util.query.xml;

import java.util.Hashtable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.mc.File;
import de.kisner.otrcast.model.xml.otr.Query;

public class XmlMcQuery
{
	final static Logger logger = LoggerFactory.getLogger(XmlMcQuery.class);
	public static enum Key {File}
	
	private static Map<Key,Query> mQueries;
	
	public static Query get(Key key)
	{
		if(mQueries==null){mQueries = new Hashtable<Key,Query>();}
		if(!mQueries.containsKey(key))
		{
			Query q = new Query();
			switch(key)
			{
				case File : q.setFile(file());break;
			}
//			logger.info("Query for key: "+key);
//			JaxbUtil.info(q);
			mQueries.put(key, q);
		}
		
		return mQueries.get(key);
	}
	
	private static File file()
	{
		File xml = new File();
		xml.setPath("");
		xml.setName("");
		return xml;
	}
}