package de.kisner.otrcast.controller.xpath;

import java.util.List;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.apache.commons.jxpath.JXPathContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.series.Season;
import de.kisner.otrcast.model.xml.series.Series;

public class SeriesXpath
{
	final static Logger logger = LoggerFactory.getLogger(SeriesXpath.class);
	
	public static synchronized Season getSeason(Series series, long nr) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(series);
		
		StringBuffer sb = new StringBuffer();
		sb.append("season[@nr='"+nr+"']");
		
		@SuppressWarnings("unchecked")
		List<Season> list = (List<Season>)context.selectNodes(sb.toString());
		if(list.size()==0){throw new ExlpXpathNotFoundException("No "+Season.class.getSimpleName()+" for nr="+nr);}
		else if(list.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Season.class.getSimpleName()+" for nr="+nr);}
		return list.get(0);
	}
}