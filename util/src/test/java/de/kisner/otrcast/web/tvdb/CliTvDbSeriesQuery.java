package de.kisner.otrcast.web.tvdb;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;

import de.kisner.otrcast.model.xml.container.Otr;
import de.kisner.otrcast.test.AbstractOtrcastTest;
import de.kisner.otrcast.test.OtrCastUtilTestBootstrap;
import de.kisner.otrcast.web.tvdb.TvDbSeriesQuery;

public class CliTvDbSeriesQuery extends AbstractOtrcastTest
{
    public static void main(String args[]) throws Exception
    {
        Configuration config = OtrCastUtilTestBootstrap.init();

        TvDbSeriesQuery dbQuery = new TvDbSeriesQuery(config.getString("tvDbApiKey"));
        dbQuery.setDebugPlainResponses(true);
        Otr otr = dbQuery.findSeriesUrlEncode("CSI: Den Tätern auf der Spur");
        JaxbUtil.info(otr);

        Otr otrSeries = dbQuery.querySeries(72546);
        JaxbUtil.info(otrSeries);
    }
 }