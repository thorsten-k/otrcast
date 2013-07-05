package net.sf.otrcutmp4.controller.processor.exlp.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.exlp.core.parser.AbstractLogParser;
import net.sf.exlp.interfaces.LogEvent;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogParser;
import net.sf.otrcutmp4.controller.processor.exlp.event.DownloadEvent;
import net.sf.otrcutmp4.factory.xml.otr.XmlOtrIdFactory;
import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.otr.Download;
import net.sf.otrcutmp4.model.xml.otr.Link;
import net.sf.otrcutmp4.model.xml.otr.Recording;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkListParser extends AbstractLogParser implements LogParser  
{
	final static Logger logger = LoggerFactory.getLogger(LinkListParser.class);
	
	public static final String secNormal = "NORMAL DOWNLOAD";
	public static final String secPrio = "PRIORISIERTER DOWNLOAD";
	public static final String secPay = "BEZAHLTER DOWNLOAD";

	private Download download;
	
	public LinkListParser(LogEventHandler leh)
	{
		super(leh);
		
		pattern.add(Pattern.compile("# "+secNormal+"(.*)"));
		pattern.add(Pattern.compile("# "+secPrio+"(.*)"));
		pattern.add(Pattern.compile("# "+secPay+"(.*)"));
		pattern.add(Pattern.compile("http(.*)"));
		pattern.add(Pattern.compile("# Diese Datei ist leider nicht verfügbar"));
	}

	public void parseLine(String line)
	{
		allLines++;
		boolean unknownPattern = true;
		for(int i=0;i<pattern.size();i++)
		{
			Matcher m=pattern.get(i).matcher(line);
			if(m.matches())
			{
				switch(i)
				{
					case 0: createSection(secNormal);break;
					case 1: createSection(secPrio);break;
					case 2: createSection(secPay);break;
					case 3: createRecording(m);break;
					case 4: break;
					
					default: unknownHandling++;break;
				}
				unknownPattern=false;
			}
		}
		if(unknownPattern)
		{			
			logger.warn("Unknown pattern: " +line);
			unknownLines++;
		}
	}
	
	@Override
	public void close()
	{
		if(download!=null)
		{
			LogEvent e = new DownloadEvent(download);
			leh.handleEvent(e);
			download = null;
		}
	}
	
	private void createSection(String type)
	{
		close();
		download = new Download();
		download.setType(type);
	}
	
	private void createRecording(Matcher m)
	{
		logger.debug(m.group(0));
		String s = m.group(0);
		
		int lastSlashIndex = s.lastIndexOf("/");
		Link link = new Link();
		link.setUrl(s.substring(0, lastSlashIndex));
		
		Recording r = new Recording();
		String fileId = s.substring(lastSlashIndex+1, s.length());
		if(fileId.contains("cut.mp4"))
		{
			CutList cl = new CutList();
			int cutIndex = fileId.indexOf("_");
			cl.setId(fileId.substring(0,cutIndex));
			r.setCutList(cl);
			fileId = fileId.substring(cutIndex+1, fileId.length());
		}
		
		r.setLink(link);
		r.setOtrId(XmlOtrIdFactory.createForId(fileId));
		download.getRecording().add(r);
	}
	
	@Override
	public void debugMe(){super.debugMe(this.getClass().getSimpleName());}
}