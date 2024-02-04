package de.kisner.otrcast.util;

import org.exlp.util.jx.JaxbUtil;
import org.jdom2.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.video.Video;
import net.sf.exlp.util.xml.JDomUtil;

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
