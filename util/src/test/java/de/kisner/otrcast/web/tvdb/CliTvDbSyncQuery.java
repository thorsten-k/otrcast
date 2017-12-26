package de.kisner.otrcast.web.tvdb;

import org.apache.commons.configuration.Configuration;

import de.kisner.otrcast.test.AbstractOtrcastTest;
import de.kisner.otrcast.test.OtrCastUtilTestBootstrap;
import de.kisner.otrcast.web.tvdb.jdom.TvDbSyncQuery;

public class CliTvDbSyncQuery extends AbstractOtrcastTest
{
    public static void main(String args[]) throws Exception
    {
        Configuration config = OtrCastUtilTestBootstrap.init();

        TvDbSyncQuery dbQuery = new TvDbSyncQuery(config.getString("tvDbApiKey"));
        dbQuery.debug();
    }
 }