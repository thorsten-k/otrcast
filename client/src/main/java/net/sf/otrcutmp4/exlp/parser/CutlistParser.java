package net.sf.otrcutmp4.exlp.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.exlp.event.LogEvent;
import net.sf.exlp.event.LogEventHandler;
import net.sf.exlp.event.impl.JaxbEvent;
import net.sf.exlp.parser.AbstractLogParser;
import net.sf.exlp.parser.LogParser;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.otrcutmp4.model.xml.cut.Cut;
import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.cut.FileName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CutlistParser extends AbstractLogParser implements LogParser  
{
	static Log logger = LogFactory.getLog(CutlistParser.class);
	
	private CutList cl;
	private Cut cut;
	
	public CutlistParser(LogEventHandler leh)
	{
		super(leh);
		pattern.add(Pattern.compile("^\\[General\\]")); //0
		pattern.add(Pattern.compile("^Application=(.*)"));
		pattern.add(Pattern.compile("^Version=(.*)"));
		pattern.add(Pattern.compile("^comment1=(.*)"));
		pattern.add(Pattern.compile("^comment2=(.*)"));
		pattern.add(Pattern.compile("^ApplyToFile=(.*)"));
		pattern.add(Pattern.compile("^OriginalFileSizeBytes=(.*)"));
		pattern.add(Pattern.compile("^FramesPerSecond=(.*)"));
		pattern.add(Pattern.compile("^DisplayAspectRatio=(.*)"));
		pattern.add(Pattern.compile("^IntendedCutApplicationName=(.*)"));
		pattern.add(Pattern.compile("^IntendedCutApplication=(.*)"));
		pattern.add(Pattern.compile("^IntendedCutApplicationVersion=(.*)"));
		pattern.add(Pattern.compile("^VDUseSmartRendering=(.*)"));
		pattern.add(Pattern.compile("^VDSmartRenderingCodecFourCC=(.*)"));
		pattern.add(Pattern.compile("^VDSmartRenderingCodecVersion=(.*)"));
		pattern.add(Pattern.compile("^NoOfCuts=(.*)"));
		
		pattern.add(Pattern.compile("^\\[Info\\]")); //16
		pattern.add(Pattern.compile("^Author=(.*)"));
		pattern.add(Pattern.compile("^RatingByAuthor=(.*)"));
		pattern.add(Pattern.compile("^EPGError=(.*)"));
		pattern.add(Pattern.compile("^ActualContent=(.*)"));
		pattern.add(Pattern.compile("^MissingBeginning=(.*)"));
		pattern.add(Pattern.compile("^MissingEnding=(.*)"));
		pattern.add(Pattern.compile("^MissingVideo=(.*)"));
		pattern.add(Pattern.compile("^MissingAudio=(.*)"));
		pattern.add(Pattern.compile("^OtherError=(.*)"));
		pattern.add(Pattern.compile("^OtherErrorDescription=(.*)"));
		pattern.add(Pattern.compile("^SuggestedMovieName=(.*)"));
		pattern.add(Pattern.compile("^UserComment=(.*)"));
			
		pattern.add(Pattern.compile("^\\[Cut(\\d*)\\]")); //29
		pattern.add(Pattern.compile("^Start=(.*)"));
		pattern.add(Pattern.compile("^StartFrame=(.*)"));
		pattern.add(Pattern.compile("^Duration=(.*)"));
		pattern.add(Pattern.compile("^DurationFrames=(.*)"));
		
		pattern.add(Pattern.compile("^$"));
		logger.debug("Pattern defined: "+pattern.size());
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
					case 0: general();break;
					case 27: name(m);break;
					case 29: cut(m);break;
					case 30: cutStart(m);break;
					case 32: cutDuration(m);break;
				}
				i=pattern.size();
				unknownPattern=false;
			}
		}
		if(unknownPattern)
		{
			logger.warn("Unknown pattern: " +line);
			unknownLines++;
		}
	}
	
	private void general()
	{
		cl = new CutList();
	}
	
	private void cut(Matcher m)
	{
		if(cl!=null && cut!=null){cl.getCut().add(cut);}
		cut = new Cut();
	}
	
	private void name(Matcher m)
	{
		if(cl!=null)
		{
			FileName fn = new FileName();
			fn.setValue(m.group(1));
			cl.setFileName(fn);
		}
	}
	
	private void cutStart(Matcher m)
	{
		if(cut!=null)
		{
			cut.setStart(Double.parseDouble(m.group(1)));
		}
	}
	
	private void cutDuration(Matcher m)
	{
		if(cut!=null)
		{
			double duration = Double.parseDouble(m.group(1)); 
			cut.setDuration(duration);
			if(duration>0){cut.setInclude(true);}
			else{cut.setInclude(false);}
		}
	}
	
	@Override
	public void close()
	{
		if(cl!=null && cut!=null){cl.getCut().add(cut);}
		if(cl!=null){event();};
	}
	
	public void event()
	{
		LogEvent event = new JaxbEvent(cl);
		leh.handleEvent(event);
	}
	
	public void debugMe(){debugMe(this.getClass().getSimpleName());}
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
			
		
	}
}