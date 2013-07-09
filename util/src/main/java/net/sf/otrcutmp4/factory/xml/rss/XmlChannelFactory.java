package net.sf.otrcutmp4.factory.xml.rss;

import net.sf.otrcutmp4.interfaces.model.Cover;
import net.sf.otrcutmp4.interfaces.model.Episode;
import net.sf.otrcutmp4.interfaces.model.Movie;
import net.sf.otrcutmp4.interfaces.model.Season;
import net.sf.otrcutmp4.interfaces.model.Series;
import net.sf.otrcutmp4.interfaces.model.Storage;
import net.sf.otrcutmp4.model.xml.rss.Channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlChannelFactory<MOVIE extends Movie<COVER,STORAGE>,SERIES extends Series<SERIES,SEASON,EPISODE,COVER,STORAGE>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER,STORAGE>,COVER extends Cover,STORAGE extends Storage>
{	
	final static Logger logger = LoggerFactory.getLogger(XmlChannelFactory.class);
	
	public static Channel build(String title)
	{
		Channel xml = new Channel();
		xml.setTitle(XmlTitleFactory.build(title));
		xml.setLink(XmlLinkFactory.build("http://myLink"));
		xml.setDescription(XmlDescriptionFactory.build(title));
		return xml;
	}
}
