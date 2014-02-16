package net.sf.otrcutmp4.controller.processor.exlp;

import net.sf.exlp.core.handler.EhResultContainer;
import net.sf.exlp.core.listener.LogListenerString;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.interfaces.LogEvent;
import net.sf.exlp.interfaces.LogListener;
import net.sf.exlp.interfaces.LogParser;
import net.sf.otrcutmp4.controller.exception.OtrProcessingException;
import net.sf.otrcutmp4.controller.processor.exlp.event.DownloadEvent;
import net.sf.otrcutmp4.controller.processor.exlp.parser.LinkListParser;
import net.sf.otrcutmp4.controller.xpath.OtrXpath;
import net.sf.otrcutmp4.factory.xml.otr.XmlTvFactory;
import net.sf.otrcutmp4.model.xml.otr.Download;
import net.sf.otrcutmp4.model.xml.otr.Linklist;
import net.sf.otrcutmp4.model.xml.otr.OtrId;
import net.sf.otrcutmp4.model.xml.otr.Quality;
import net.sf.otrcutmp4.model.xml.otr.Recording;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinklistConverter
{
	final static Logger logger = LoggerFactory.getLogger(LinklistConverter.class);
	
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
			catch (ExlpXpathNotUniqueException e) {logger.error("",e);}
			catch (ExlpXpathNotFoundException e)
			{
				otrId = new OtrId();
				otrId.setKey(keyId);
				try
				{
					otrId.setTv(XmlTvFactory.createForFileName(keyId));
				}
				catch (OtrProcessingException e1) {logger.warn(e1.getMessage());}
				download.getOtrId().add(otrId);
			}
			
			Quality quality=null;
			try {quality = OtrXpath.getQualityByKey(otrId, keyQuality);}
			catch (ExlpXpathNotUniqueException e) {logger.error("",e);}
			catch (ExlpXpathNotFoundException e)
			{
				quality = new Quality();
				quality.setType(keyQuality);
				otrId.getQuality().add(quality);
			}
			
			Recording rNew = new Recording();
			rNew.setFormat(r.getOtrId().getFormat());
			if(rNew.getFormat().isCut()){rNew.setCutList(r.getCutList());}
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
