package de.kisner.otrcast.factory.txt;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.exception.OtrProcessingException;
import de.kisner.otrcast.model.xml.cut.CutList;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.otr.OtrId;
import de.kisner.otrcast.model.xml.video.tv.Episode;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TxtFileNameFactoy
{
	final static Logger logger = LoggerFactory.getLogger(TxtFileNameFactoy.class);
	
	private Template t;
	
	public TxtFileNameFactoy()
	{
		
	}
	
	public void initTemplate(String templateStr)
	{
		try
		{
			logger.debug("initializing template with: "+templateStr);
			t = new Template("otr", new StringReader(templateStr), new Configuration());
		}
		catch (IOException e) {e.printStackTrace();}
	}
	
	public String convert(Episode e) throws TemplateException, IOException
	{
		Map<String,String> ds = new HashMap<String,String>();
		ds.put(TxtDsFactory.Key.seriesName.toString(), e.getSeason().getSeries().getName());
		ds.put(TxtDsFactory.Key.seriesKey.toString(), e.getSeason().getSeries().getKey());
		ds.put(TxtDsFactory.Key.seasonNr.toString(), ""+e.getSeason().getNr());
		ds.put(TxtDsFactory.Key.seasonName.toString(), e.getSeason().getName());
		ds.put(TxtDsFactory.Key.episodeName.toString(), e.getName());
		ds.put(TxtDsFactory.Key.episodeNr.toString(), ""+e.getNr());
		
		return convert(ds);
	}
	
	public String convert(Map<String,String> ds) throws TemplateException, IOException
	{	
		StringWriter sw = new StringWriter();
        t.process(ds, sw);
        sw.flush();
        return sw.toString();
	}
	
	
	
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
	
	public static String build(VideoFile vf)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(vf.getOtrId().getKey());
		sb.append(".");
		sb.append(vf.getOtrId().getFormat().getType());
		return sb.toString();
	}
}