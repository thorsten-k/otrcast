package net.sf.otrcutmp4.web.tvdb;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.model.xml.container.Otr;
import net.sf.otrcutmp4.test.AbstractUtilTest;
import net.sf.otrcutmp4.test.OtrUtilTestBootstrap;

import org.apache.commons.configuration.Configuration;

public class CliTvDbSeriesQuery extends AbstractUtilTest
{
    public static void main(String args[]) throws Exception
    {
        Configuration config = OtrUtilTestBootstrap.init();

        TvDbSeriesQuery dbQuery = new TvDbSeriesQuery(config.getString("tvDbApiKey"));
        dbQuery.setDebugPlainResponses(true);
        Otr otr = dbQuery.findSeriesUrlEncode("CSI: Den Tätern auf der Spur");
        JaxbUtil.info(otr);

        Otr otrSeries = dbQuery.querySeries(72546);
        JaxbUtil.info(otrSeries);
    }
 }