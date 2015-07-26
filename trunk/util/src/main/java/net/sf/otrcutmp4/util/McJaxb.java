package net.sf.otrcutmp4.util;

import org.jdom2.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.model.xml.series.Video;

public class McJaxb
{
	final static Logger logger = LoggerFactory.getLogger(McJaxb.class);
	
	public static void debug(Video video)
	{
		Document doc = JaxbUtil.toDocument(video);
		Video xml = JDomUtil.toJaxb(doc, Video.class);
		
		if(xml.isSetEpisode())
		{
			if(xml.getEpisode().isSetImage() && xml.getEpisode().getImage().isSetData())
			{
				xml.getEpisode().getImage().setData(null);
			}
		}
		
		JaxbUtil.info(xml);
	}
}
