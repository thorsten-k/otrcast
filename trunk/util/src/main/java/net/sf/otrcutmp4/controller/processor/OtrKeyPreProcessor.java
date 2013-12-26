package net.sf.otrcutmp4.controller.processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.exlp.interfaces.util.PatternLibrary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OtrKeyPreProcessor
{	
	final static Logger logger = LoggerFactory.getLogger(OtrKeyPreProcessor.class);

    private static String prefixDatenkeller = "http://otr.datenkeller.at";
	private Pattern pOtrDownload;
	
	public OtrKeyPreProcessor()
	{
		buildOtrDownloadPattern();
	}
	
	public String guess(String input)
	{
		if(input.endsWith(".otrkey"))
		{
			int index = input.lastIndexOf(".otrkey");
			input = input.substring(0,index);
		}

		if(input.startsWith(prefixDatenkeller))
		{
            input = input.substring(prefixDatenkeller.length(),input.length());

			int index = input.indexOf("?getFile=")+"?getFile=".length();
			return input.substring(index,input.length());
		}
		
		logger.trace("Matching? " + input);
		Matcher m = pOtrDownload.matcher(input);
		if(m.matches())
		{
//			logger.error("Match");
//			logger.error(m.group(0));
			return m.group(1);
		}
		return input;

	}
	
	private void buildOtrDownloadPattern()
	{
		//http://81.95.11.3/download/2094360/1/7524402/e0a155c7b9253defa2f52726148661fe/de/
		StringBuffer sb = new StringBuffer();
		sb.append("http://");
		sb.append(PatternLibrary.ipPattern);
		sb.append("/download");
		sb.append("/[\\d]+/[\\d]+/[\\d]+");
		sb.append("/[\\d\\w]+");
		sb.append("/[\\w]+/");
		sb.append("(.*)");
		pOtrDownload = Pattern.compile(sb.toString());
	}
}