package net.sf.otrcutmp4.controller.processor.exlp.event;

import net.sf.exlp.event.AbstractEvent;
import net.sf.exlp.event.LogEvent;
import net.sf.otrcutmp4.model.xml.otr.Download;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DownloadEvent extends AbstractEvent implements LogEvent
{
	static Log logger = LogFactory.getLog(DownloadEvent.class);
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