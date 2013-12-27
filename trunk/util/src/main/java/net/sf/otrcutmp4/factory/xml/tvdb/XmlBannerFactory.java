package net.sf.otrcutmp4.factory.xml.tvdb;

import net.sf.otrcutmp4.model.xml.tvdb.Banner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlBannerFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlBannerFactory.class);

    public static Banner build(String url)
    {
        Banner xml = new Banner();
        xml.setUrl(url);

        return xml;
    }
}
