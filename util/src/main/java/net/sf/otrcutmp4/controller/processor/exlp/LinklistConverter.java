package net.sf.otrcutmp4.controller.processor.exlp;

import java.util.Hashtable;
import java.util.Map;

import net.sf.exlp.event.LogEvent;
import net.sf.exlp.event.handler.EhResultContainer;
import net.sf.exlp.listener.LogListener;
import net.sf.exlp.listener.impl.LogListenerString;
import net.sf.exlp.parser.LogParser;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.processor.exlp.event.DownloadEvent;
import net.sf.otrcutmp4.controller.processor.exlp.parser.LinkListParser;
import net.sf.otrcutmp4.model.xml.otr.Download;
import net.sf.otrcutmp4.model.xml.otr.Linklist;
import net.sf.otrcutmp4.model.xml.otr.OtrId;
import net.sf.otrcutmp4.model.xml.otr.Recording;

public class LinklistConverter
{
	public LinklistConverter()
	{
		
	}
	
	public Linklist convert(String sLL)
	{
		Linklist xml = new Linklist();
		
		Linklist xmlParse = parse(sLL);
		for(Download xmlDlParse : xmlParse.getDownload())
		{
			xml.getDownload().add(convertDownload(xmlDlParse));
		}
		
		return xml;
	}
	
	private Download convertDownload(Download xmlParse)
	{
		Map<String,OtrId> ids = new Hashtable<String,OtrId>();
		
		for(Recording r : xmlParse.getRecording())
		{
			String key = r.getOtrId().getKey();
			if(!ids.containsKey(key))
			{
				OtrId otrId = new OtrId();
				otrId.setKey(key);
				ids.put(key, otrId);
			}
			
			Recording rNew = new Recording();
			rNew.setFormat(r.getOtrId().getFormat());
			rNew.setLink(r.getLink());
			ids.get(key).getRecording().add(rNew);
		}
		
		Download xml = new Download();
		xml.setType(xmlParse.getType());
		for(OtrId v :ids.values())
		{
			xml.getOtrId().add(v);
		}
		
		return xml;
	}
	
	private Linklist parse(String sLL)
	{
		EhResultContainer leh = new EhResultContainer();
		LogParser lp = new LinkListParser(leh);
		LogListener ll = new LogListenerString(sLL,lp);
		ll.processSingle();
		lp.debugStats();
		
		Linklist xml = new Linklist();
		
		for(LogEvent le : leh.getAlResults())
		{
			DownloadEvent de = (DownloadEvent)le;
			xml.getDownload().add(de.getDownload());
		}
		return xml;
	}
}
