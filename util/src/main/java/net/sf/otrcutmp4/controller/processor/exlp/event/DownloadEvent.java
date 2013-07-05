package net.sf.otrcutmp4.controller.processor.exlp.event;

import net.sf.exlp.core.event.AbstractEvent;
import net.sf.exlp.interfaces.LogEvent;
import net.sf.otrcutmp4.model.xml.otr.Download;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownloadEvent extends AbstractEvent implements LogEvent
{
	final static Logger logger = LoggerFactory.getLogger(DownloadEvent.class);
	static final long serialVersionUID=1;
	
	private Download download;
	
	public DownloadEvent(Download download)
	{
		this.download=download;
	}
	
	public void debug()
	{
		super.debug();
		logger.debug("size: "+download.getRecording().size());
	}
	
	public Download getDownload() {return download;}
}