package net.sf.otrcutmp4.web.tvdb.processor;

import java.io.File;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.otrcutmp4.test.AbstractUtilTest;
import net.sf.otrcutmp4.test.OtrUtilTestBootstrap;
import net.sf.otrcutmp4.web.tvdb.TvDbQuery;

import org.apache.commons.configuration.Configuration;
import org.jdom2.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliTvDbSeriesStructureFactory extends AbstractUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TvDbQuery.class);
	
	private Configuration config;
	
	public CliTvDbSeriesStructureFactory(Configuration config)
	{
		this.config=config;
	}
	
	public void de()
	{
		File f = new File(config.getString("test.xml.tvdb.de"));
		logger.info("Using de.xml with "+f);
		Document doc = JDomUtil.load(f);
		
		TvDbSeriesStructureFactory fStructure = new TvDbSeriesStructureFactory(doc);
		fStructure.build();
	}
	
    public static void main(String args[]) throws Exception
    {
        Configuration config = OtrUtilTestBootstrap.init();

        CliTvDbSeriesStructureFactory test = new CliTvDbSeriesStructureFactory(config);
        test.de();
    }
 }