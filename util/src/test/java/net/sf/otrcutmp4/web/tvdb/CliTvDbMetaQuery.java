package net.sf.otrcutmp4.web.tvdb;

import net.sf.otrcutmp4.test.AbstractUtilTest;
import net.sf.otrcutmp4.test.OtrUtilTestBootstrap;

import org.apache.commons.configuration.Configuration;

public class CliTvDbMetaQuery extends AbstractUtilTest
{
    public static void main(String args[]) throws Exception
    {
        Configuration config = OtrUtilTestBootstrap.init();

        TvDbMetaQuery dbQuery = new TvDbMetaQuery(config.getString("tvDbApiKey"));
        dbQuery.debug();
    }
 }