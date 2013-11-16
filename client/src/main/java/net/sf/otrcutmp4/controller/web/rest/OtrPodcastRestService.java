package net.sf.otrcutmp4.controller.web.rest;

import net.sf.otrcutmp4.controller.rss.RssLinkProcessor;
import net.sf.otrcutmp4.factory.xml.rss.XmlChannelFactory;
import net.sf.otrcutmp4.factory.xml.rss.XmlItemFactory;
import net.sf.otrcutmp4.factory.xml.rss.XmlRssFactory;
import net.sf.otrcutmp4.interfaces.rest.OtrPodcastRest;
import net.sf.otrcutmp4.model.OtrEpisode;
import net.sf.otrcutmp4.model.xml.rss.Rss;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Path;

@Path("/")
public class OtrPodcastRestService extends AbstractOtrRestService implements OtrPodcastRest
{
	final static Logger logger = LoggerFactory.getLogger(OtrPodcastRestService.class);
	
	@Override
	public Rss rss()
	{
        init();
		Rss rss = XmlRssFactory.build();
        rss.setChannel(XmlChannelFactory.build5("test"));

        OtrEpisode episode = ufb.all(OtrEpisode.class).get(0);
        logger.info(episode.toString());
        logger.info(episode.getStorage().toString());

        rss.getChannel().getItem().add(XmlItemFactory.build(episode));

        RssLinkProcessor.getFileByKey(rss,"http://localhost:8080/x");

        return rss;
	}
}