package de.kisner.otrcast.factory.xml.tvdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.tvdb.Banners;

public class XmlBannersFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlBannersFactory.class);

    public static Banners build()
    {
        return new Banners();
    }
}
