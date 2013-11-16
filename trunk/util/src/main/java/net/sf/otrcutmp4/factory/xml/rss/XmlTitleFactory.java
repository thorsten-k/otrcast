package net.sf.otrcutmp4.factory.xml.rss;

import net.sf.otrcutmp4.interfaces.model.*;
import net.sf.otrcutmp4.model.xml.rss.Title;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlTitleFactory<MOVIE extends Movie<COVER,STORAGE>,SERIES extends Series<SERIES,SEASON,EPISODE,COVER,STORAGE>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER,STORAGE>,COVER extends Cover,STORAGE extends Storage>
{	
	final static Logger logger = LoggerFactory.getLogger(XmlTitleFactory.class);

    public static Title build(Episode episode)
    {
        return build(episode.getName());
    }

	public static Title build(String title)
	{
		Title xml = new Title();
		xml.setValue(title);
		return xml;
	}
}
