package net.sf.otrcutmp4.web;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.exception.OtrProcessingException;
import net.sf.otrcutmp4.factory.xml.XmlVideoFileFactory;
import net.sf.otrcutmp4.interfaces.web.WebMovieFinder;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.series.Movies;
import net.sf.otrcutmp4.test.AbstractUtilTest;
import net.sf.otrcutmp4.test.OtrUtilTestBootstrap;
import net.sf.otrcutmp4.web.imdb.ImdbMovieQuery;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestImdbQuery extends AbstractUtilTest
{
    final static Logger logger = LoggerFactory.getLogger(TestImdbQuery.class);
		
    @Test public void dummy(){}
    
	public static void main(String args[]) throws ExlpConfigurationException, OtrProcessingException
    {
        OtrUtilTestBootstrap.init();
 
		try
		{
			VideoFile vf = XmlVideoFileFactory.create("Captain_America__The_First_Avenger_14.03.16_20-15_pro7_145_TVOON_DE.mpg.HQ.avi");
	        JaxbUtil.info(vf);
	        
			WebMovieFinder maf = new ImdbMovieQuery();
	        Movies movies = maf.find(vf);
	        JaxbUtil.info(movies);
		}
		catch (UtilsProcessingException e) {logger.error(e.getMessage());}
    }
 }