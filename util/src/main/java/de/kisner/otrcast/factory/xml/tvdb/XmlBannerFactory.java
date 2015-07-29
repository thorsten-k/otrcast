package de.kisner.otrcast.factory.xml.tvdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.tvdb.Banner;

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
