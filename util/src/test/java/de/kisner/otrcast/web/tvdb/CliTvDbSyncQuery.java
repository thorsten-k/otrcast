package de.kisner.otrcast.web.tvdb;

import org.apache.commons.configuration.Configuration;

import de.kisner.otrcast.AbstractOtrcastTest;
import de.kisner.otrcast.OtrUtilTestBootstrap;
import de.kisner.otrcast.web.tvdb.TvDbSyncQuery;

public class CliTvDbSyncQuery extends AbstractOtrcastTest
{
    public static void main(String args[]) throws Exception
    {
        Configuration config = OtrUtilTestBootstrap.init();

        TvDbSyncQuery dbQuery = new TvDbSyncQuery(config.getString("tvDbApiKey"));
        dbQuery.debug();
    }
 }