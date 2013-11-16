package net.sf.otrcutmp4.factory.xml.rss;

import net.sf.otrcutmp4.model.xml.rss.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlChannelFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlChannelFactory.class);
	
	public static Channel build5(String title)
	{
		Channel xml = new Channel();
		xml.setTitle(XmlTitleFactory.build(title));
		xml.setLink(XmlLinkFactory.build("http://myLink"));
		xml.setDescription(XmlDescriptionFactory.build(title));
		return xml;
	}
}
