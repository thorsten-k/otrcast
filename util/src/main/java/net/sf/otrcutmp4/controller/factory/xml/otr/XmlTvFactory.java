package net.sf.otrcutmp4.controller.factory.xml.otr;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.datatype.XMLGregorianCalendar;

import net.sf.exlp.util.DateUtil;
import net.sf.otrcutmp4.controller.exception.OtrProcessingException;
import net.sf.otrcutmp4.model.xml.otr.Tv;

import org.joda.time.MutableDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlTvFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlTvFactory.class);
	
	private static Pattern p = Pattern.compile("([\\w_\\d]+)_(\\d{2})\\.(\\d{2})\\.(\\d{2})_(\\d{2})-(\\d{2})_([a-zA-Z\\d]*)_(\\d+)_(.*)");
	
	public static Tv createForFileName(String fileName) throws OtrProcessingException
	{	
		Matcher m = p.matcher(fileName);
		
		if(m.matches())
		{
			if(logger.isTraceEnabled())
			{
				logger.debug("FileName: "+fileName);
				for(int i=0;i<m.groupCount();i++)
				{
					logger.debug(i+" "+m.group(i));
				}
				
			}
			Tv xml = new Tv();
			xml.setName(buildName(m.group(1)));
			xml.setAirtime(buildAirTime(m));
			xml.setChannel(m.group(7));
			xml.setDuration(new Integer(m.group(8)));
			
			return xml;
		}
		else
		{
			throw new OtrProcessingException("Filename does not match pattern: "+fileName);
		}
		
	}
	
	private static XMLGregorianCalendar buildAirTime(Matcher m)
	{
		MutableDateTime dt = new MutableDateTime();
		
		dt.setYear(2000+new Integer(m.group(2)));
		dt.setMonthOfYear(new Integer(m.group(3)));
		dt.setDayOfMonth(new Integer(m.group(4)));
				
		dt.setHourOfDay(new Integer(m.group(5)));
		dt.setMinuteOfHour(new Integer(m.group(6)));
		dt.setSecondOfMinute(0);
		dt.setMillisOfSecond(0);
		
		return DateUtil.getXmlGc4D(dt.toDate());
	}
	
	private static String buildName(String name)
	{
		name = name.replace("_", " ");
		return name;
	}

}