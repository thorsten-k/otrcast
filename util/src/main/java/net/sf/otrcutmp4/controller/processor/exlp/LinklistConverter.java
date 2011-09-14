package net.sf.otrcutmp4.controller.processor.exlp;

import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.exlp.event.LogEvent;
import net.sf.exlp.event.handler.EhResultContainer;
import net.sf.exlp.listener.LogListener;
import net.sf.exlp.listener.impl.LogListenerString;
import net.sf.exlp.parser.LogParser;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.processor.exlp.event.DownloadEvent;
import net.sf.otrcutmp4.controller.processor.exlp.parser.LinkListParser;
import net.sf.otrcutmp4.model.xml.otr.Download;
import net.sf.otrcutmp4.model.xml.otr.Linklist;
import net.sf.otrcutmp4.model.xml.otr.OtrId;
import net.sf.otrcutmp4.model.xml.otr.Quality;
import net.sf.otrcutmp4.model.xml.otr.Recording;
import net.sf.otrcutmp4.model.xml.xpath.OtrXpath;

public class LinklistConverter
{
	static Log logger = LogFactory.getLog(LinklistConverter.class);
	
	public LinklistConverter()
	{
		
	}
	
	public Linklist convert(String sLinkList)
	{
		Linklist xml = new Linklist();
		
		Linklist xmlParse = parse(sLinkList);
		for(Download xmlDlParse : xmlParse.getDownload())
		{
			xml.getDownload().add(convertDownload(xmlDlParse));
		}
		
		return xml;
	}
	
	private Download convertDownload(Download xmlParse)
	{
		Download download = new Download();
		download.setType(xmlParse.getType());
		
		for(Recording r : xmlParse.getRecording())
		{
			String keyId = r.getOtrId().getKey();
			String keyQuality = r.getOtrId().getFormat().getQuality().getType();
			
			OtrId otrId=null;
			try {otrId = OtrXpath.getOtrIdByKey(download, keyId);}
			catch (ExlpXpathNotUniqueException e) {logger.error(e);}
			catch (ExlpXpathNotFoundException e)
			{
				otrId = new OtrId();
				otrId.setKey(keyId);
				download.getOtrId().add(otrId);
			}
			
			Quality quality=null;
			try {quality = OtrXpath.getQualityByKey(otrId, keyQuality);}
			catch (ExlpXpathNotUniqueException e) {logger.error(e);}
			catch (ExlpXpathNotFoundException e)
			{
				quality = new Quality();
				quality.setType(keyQuality);
				otrId.getQuality().add(quality);
			}
			
			Recording rNew = new Recording();
			rNew.setFormat(r.getOtrId().getFormat());
			rNew.getFormat().setQuality(null);
			rNew.setLink(r.getLink());
			quality.getRecording().add(rNew);
		}
	
		return download;
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
