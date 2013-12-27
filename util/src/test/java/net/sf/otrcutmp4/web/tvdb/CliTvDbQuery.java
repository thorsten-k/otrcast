package net.sf.otrcutmp4.web.tvdb;

import java.util.Date;

import net.sf.otrcutmp4.test.AbstractUtilTest;
import net.sf.otrcutmp4.test.OtrUtilTestBootstrap;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliTvDbQuery extends AbstractUtilTest
{
    final static Logger logger = LoggerFactory.getLogger(CliTvDbQuery.class);

    public static void main(String args[]) throws Exception
    {
        Configuration config = OtrUtilTestBootstrap.init();

        TvDbQuery dbQuery = new TvDbQuery(config.getString("tvDbApiKey"));

        Date last = dbQuery.getLastModificationTime();
        logger.info(last.toString());
    }
 }