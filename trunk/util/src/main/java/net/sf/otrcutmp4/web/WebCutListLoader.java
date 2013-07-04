package net.sf.otrcutmp4.web;

import net.sf.exlp.event.handler.EhResultContainer;
import net.sf.exlp.event.impl.JaxbEvent;
import net.sf.exlp.interfaces.LogParser;
import net.sf.exlp.listener.LogListener;
import net.sf.exlp.listener.impl.LogListenerHttp;
import net.sf.otrcutmp4.controller.processor.exlp.parser.CutlistParser;
import net.sf.otrcutmp4.model.xml.cut.CutList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebCutListLoader
{
	final static Logger logger = LoggerFactory.getLogger(WebCutListLoader.class);
	
	public WebCutListLoader()
	{
	}
	
	public CutList loadCutlist(String id)
	{
		String http = "http://cutlist.at/getfile.php?id="+id;
		
		logger.info("Trying to download cutlist "+id);
		logger.debug("\t"+http);
	
		EhResultContainer leh = new EhResultContainer();
		LogParser lp = new CutlistParser(leh);
		
		LogListener ll = new LogListenerHttp(lp);
		ll.processSingle(http);
		
		JaxbEvent event = (JaxbEvent)leh.getSingleResult();
		return (CutList)event.getObject();
	}
}