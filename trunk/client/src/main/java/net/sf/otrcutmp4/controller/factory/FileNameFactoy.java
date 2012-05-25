package net.sf.otrcutmp4.controller.factory;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

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
	
	public String convert(Map<String,String>  ds) throws TemplateException, IOException
	{	
		StringWriter sw = new StringWriter();
        t.process(ds, sw);
        sw.flush();
        return sw.toString();
	}
}
