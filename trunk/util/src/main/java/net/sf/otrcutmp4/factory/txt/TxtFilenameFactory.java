package net.sf.otrcutmp4.factory.txt;

import java.util.Date;

import net.sf.otrcutmp4.controller.exception.OtrProcessingException;
import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.otr.OtrId;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtFilenameFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtFilenameFactory.class);

	public static String build(String name, String channel, int duration, Date airTime)
	{
		DateTime dt = new DateTime(airTime);
		
		StringBuffer sb = new StringBuffer();
		sb.append(name).append("_");
		sb.append(dt.getYearOfCentury()).append(".");
		sb.append(tZ(dt.getMonthOfYear())).append(".");
		sb.append(tZ(dt.getDayOfMonth()));
		sb.append("_");
		sb.append(tZ(dt.getHourOfDay())).append("-").append(tZ(dt.getMinuteOfHour()));
		sb.append("_");
		sb.append(channel).append("_").append(duration).append("_");
		sb.append("TVOON_DE.mpg.HQ.avi");
		return sb.toString();
	}
	
	private static String tZ(int in)
	{
		Integer i = new Integer(in);
		if(i<10){return "0"+in;}
		else{return ""+in;}
	}
	
	public static String build(OtrId otrId)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(otrId.getKey());
		sb.append(".");
		sb.append(otrId.getFormat().getType());
		return sb.toString();
	}
	
	public String create(CutList cl) throws OtrProcessingException
	{
		return "test";
	}
}
