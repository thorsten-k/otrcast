package de.kisner.otrcast.web.tvdb;

import org.apache.commons.configuration.Configuration;

import de.kisner.otrcast.test.AbstractUtilTest;
import de.kisner.otrcast.test.OtrUtilTestBootstrap;
import de.kisner.otrcast.web.tvdb.TvDbSyncQuery;

public class CliTvDbSyncQuery extends AbstractUtilTest
{
    public static void main(String args[]) throws Exception
    {
        Configuration config = OtrUtilTestBootstrap.init();

        TvDbSyncQuery dbQuery = new TvDbSyncQuery(config.getString("tvDbApiKey"));
        dbQuery.debug();
    }
 }