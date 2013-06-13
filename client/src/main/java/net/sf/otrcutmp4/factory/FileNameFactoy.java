package net.sf.otrcutmp4.factory;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import net.sf.otrcutmp4.controller.factory.txt.TxtDsFactory;
import net.sf.otrcutmp4.model.xml.series.Episode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FileNameFactoy
{
	final static Logger logger = LoggerFactory.getLogger(FileNameFactoy.class);
	
	private Template t;
	
	public FileNameFactoy()
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
}
