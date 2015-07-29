package de.kisner.otrcast.web;

import net.sf.exlp.core.event.JaxbEvent;
import net.sf.exlp.core.handler.EhResultContainer;
import net.sf.exlp.core.listener.LogListenerHttp;
import net.sf.exlp.interfaces.LogListener;
import net.sf.exlp.interfaces.LogParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.processor.exlp.parser.CutlistParser;
import de.kisner.otrcast.model.xml.cut.CutList;

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