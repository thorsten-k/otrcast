package net.sf.otrcutmp4.web.tvdb;

import net.sf.otrcutmp4.test.AbstractUtilTest;
import net.sf.otrcutmp4.test.OtrUtilTestBootstrap;

import org.apache.commons.configuration.Configuration;

public class CliTvDbSeriesQuery extends AbstractUtilTest
{
    public static void main(String args[]) throws Exception
    {
        Configuration config = OtrUtilTestBootstrap.init();

        TvDbSeriesQuery dbQuery = new TvDbSeriesQuery(config.getString("tvDbApiKey"));
        dbQuery.findSeries("Lost");

//        dbQuery.querySeries(274058l);
    }
 }