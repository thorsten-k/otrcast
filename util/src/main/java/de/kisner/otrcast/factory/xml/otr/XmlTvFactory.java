package de.kisner.otrcast.factory.xml.otr;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.exception.OtrProcessingException;
import de.kisner.otrcast.model.xml.otr.Tv;
import net.sf.exlp.util.DateUtil;

public class XmlTvFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlTvFactory.class);
	
	private static Pattern p = Pattern.compile("([-\\w_\\d]+)_(\\d{2})\\.(\\d{2})\\.(\\d{2})_(\\d{2})-(\\d{2})_([a-zA-Z\\d]*)_(\\d+)_(.*)");
	
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
		LocalDateTime ldt = DateUtil.ldtOf(""+(2000+new Integer(m.group(2))), m.group(3), m.group(4), m.group(5), m.group(6), "0");
		return DateUtil.toXmlGc(ldt);
	}
	
	private static String buildName(String name)
	{
		name = name.replace("__", " ");
		name = name.replace("_", " ");
		return name;
	}

}