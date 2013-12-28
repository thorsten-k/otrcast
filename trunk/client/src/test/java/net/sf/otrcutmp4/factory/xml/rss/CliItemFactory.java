package net.sf.otrcutmp4.factory.xml.rss;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.OtrCutMp4Bootstrap;
import net.sf.otrcutmp4.controller.facade.OtrMediacenterFacadeBean;
import net.sf.otrcutmp4.model.*;
import net.sf.otrcutmp4.model.xml.rss.Item;
import net.sf.otrcutmp4.test.AbstractClientTest;
import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;

public class CliItemFactory extends AbstractClientTest
{ 
	final static Logger logger = LoggerFactory.getLogger(CliItemFactory.class);

    private UtilsFacadeBean fUtils;
    private OtrMediacenterFacadeBean<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage> fOtrMc;

	public CliItemFactory(EntityManager em)
    {
        fUtils = new UtilsFacadeBean(em);
        fOtrMc = new OtrMediacenterFacadeBean<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage>(em,fUtils);
    }

    private void xmlEpisode()
    {
        OtrEpisode episode = fUtils.all(OtrEpisode.class).get(0);
        logger.info(episode.toString());
        logger.info(episode.getStorage().toString());

        Item item = XmlItemFactory.build(episode);
        JaxbUtil.info(item);
    }

    public static void main(String args[]) throws Exception
    {
        Configuration config = OtrCutMp4Bootstrap.init();

        CliItemFactory cli = new CliItemFactory(OtrCutMp4Bootstrap.buildEmf(config).createEntityManager());
        cli.xmlEpisode();
    }
}