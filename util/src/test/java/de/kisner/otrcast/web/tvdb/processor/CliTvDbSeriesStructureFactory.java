package de.kisner.otrcast.web.tvdb.processor;

import java.io.File;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.jdom2.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.series.Series;
import de.kisner.otrcast.test.AbstractOtrcastTest;
import de.kisner.otrcast.test.OtrCastUtilTestBootstrap;
import de.kisner.otrcast.web.tvdb.TvDbQuery;
import de.kisner.otrcast.web.tvdb.processor.TvDbSeriesStructureFactory;

public class CliTvDbSeriesStructureFactory extends AbstractOtrcastTest
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
		Series series = fStructure.build();
		JaxbUtil.info(series);
	}
	
    public static void main(String args[]) throws Exception
    {
        Configuration config = OtrCastUtilTestBootstrap.init();

        CliTvDbSeriesStructureFactory test = new CliTvDbSeriesStructureFactory(config);
        test.de();
    }
 }