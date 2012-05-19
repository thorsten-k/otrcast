package net.sf.otrcutmp4.controller.processor;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import net.sf.otrcutmp4.interfaces.VideoType;
import net.sf.plist.NSDictionary;
import net.sf.plist.NSObject;
import net.sf.plist.io.PropertyListException;
import net.sf.plist.io.PropertyListParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImlProcessor
{
	final static Logger logger = LoggerFactory.getLogger(ImlProcessor.class);
	
	private static final String keyTvShow = "TV Show";
	private static final String keyMovie = "Movie";
	
	private NSDictionary rootNode;
	
	
	
	public ImlProcessor()
	{
		
	}
	
	public void laod(File file)
	{
		try
		{
		    NSObject root = PropertyListParser.parse(file);
		    if (root instanceof NSDictionary)
		    {
		    	rootNode = (NSDictionary)root;
		    }
		}
		catch (PropertyListException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
	}
	
	public void parse()
	{
		Map<String,NSObject> nodeValues = rootNode.map();
		try
		{
			Map<String, NSObject> mapTracks = toDict(nodeValues, "Tracks");
			logger.info("Tracks: "+mapTracks.size());
			for(String key : mapTracks.keySet())
			{
				VideoType type = getType(mapTracks.get(key));
				switch(type)
				{
					case Series: logger.debug("Series");break;
				}
			}
		}
		catch (PropertyListException e) {e.printStackTrace();}
	}
	
	private Map<String,NSObject> toDict(Map<String,NSObject> map, String key) throws PropertyListException
	{
		if(!map.containsKey(key))
		{
			throw new PropertyListException("No Entry for key="+key);
		}
		else
		{
			NSObject dict = map.get("Tracks");
	        if (dict instanceof NSDictionary)
		    {
		        return ((NSDictionary)dict).map();
		
		    }
	        else
	        {
	        	throw new PropertyListException("Entry not a dict");
	        }
		}
	}
	
	private VideoType getType(NSObject nso) throws PropertyListException
	{
		if (nso instanceof NSDictionary)
	    {
			Map<String,NSObject> map = ((NSDictionary)nso).map();			
			if(map.containsKey(keyTvShow) )
			{
				NSObject ns = map.get(keyTvShow);
				if(new Boolean(""+ns.getValue())){return VideoType.Series;}
			}
			if(map.containsKey(keyMovie) )
			{
				NSObject ns = map.get(keyMovie);
				if(new Boolean(""+ns.getValue())){return VideoType.Movie;}
			}
			throw new PropertyListException("Unknown Type");
	    }
		throw new PropertyListException("Unknown Entry");
	}
}
