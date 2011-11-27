package net.sf.otrcutmp4.controller.processor;

import java.util.Hashtable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinklistProcessor
{
	final static Logger logger = LoggerFactory.getLogger(LinklistProcessor.class);
	
	private Map<String,Map<String,Boolean>> processSelector;	

	public LinklistProcessor()
	{
		processSelector = new Hashtable<String,Map<String,Boolean>>(); 
	}
	
	public void processActivate(String otrKey, String qualityType, String formatType, boolean cut, boolean key)
	{
		process(true, otrKey, qualityType, formatType, cut, key);
	}
	
	public void processDeactivate(String otrKey, String qualityType, String formatType, boolean cut, boolean key)
	{
		process(false, otrKey, qualityType, formatType, cut, key);
	}
	
	protected void process(boolean action, String otrKey, String qualityType, String formatType, boolean cut, boolean key)
	{
		if(!processSelector.containsKey(otrKey)){processSelector.put(otrKey, new Hashtable<String,Boolean>());}
		
		StringBuffer sbAccessKey = new StringBuffer();
		sbAccessKey.append(qualityType);
		sbAccessKey.append("-").append(formatType);
		sbAccessKey.append("-").append(cut);
		sbAccessKey.append("-").append(key);
		
		processSelector.get(otrKey).put(createAccessKey(qualityType, formatType, cut, key), action);
	}
	
	public Map<String, Map<String, Boolean>> getProcessSelector() {return processSelector;}
	
	protected static String createAccessKey(String qualityType, String formatType, boolean cut, boolean key)
	{
		StringBuffer sbAccessKey = new StringBuffer();
		sbAccessKey.append(qualityType);
		sbAccessKey.append("-").append(formatType);
		sbAccessKey.append("-").append(cut);
		sbAccessKey.append("-").append(key);
		return sbAccessKey.toString();
	}
}
