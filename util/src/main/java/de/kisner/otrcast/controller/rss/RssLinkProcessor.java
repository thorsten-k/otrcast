package de.kisner.otrcast.controller.rss;

import org.apache.commons.jxpath.JXPathContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.rss.Enclosure;
import de.kisner.otrcast.model.xml.rss.Rss;

import java.util.List;

public class RssLinkProcessor
{
	final static Logger logger = LoggerFactory.getLogger(RssLinkProcessor.class);

	public static void getFileByKey(Rss rss, String url)
	{
		JXPathContext context = JXPathContext.newContext(rss);
		
		StringBuffer sb = new StringBuffer();
		sb.append("channel/item/enclosure");
		
		@SuppressWarnings("unchecked")
		List<Enclosure> listResult = (List<Enclosure>)context.selectNodes(sb.toString());
		for(Enclosure xml : listResult)
        {
            xml.setUrl(url+"/"+xml.getUrl());
        }
	}
}