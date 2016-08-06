package de.kisner.otrcast.web;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.AbstractOtrcastTest;
import de.kisner.otrcast.OtrUtilTestBootstrap;
import de.kisner.otrcast.controller.exception.OtrProcessingException;
import de.kisner.otrcast.factory.xml.XmlVideoFileFactory;
import de.kisner.otrcast.interfaces.web.WebMovieFinder;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.series.Movies;
import de.kisner.otrcast.web.omdbapi.OmdbMovieQuery;
import de.kisner.otrcast.web.omdbapi.OmdbMovieSearch;

public class TestOmdbQuery extends AbstractOtrcastTest
{
    final static Logger logger = LoggerFactory.getLogger(TestOmdbQuery.class);
		
    @Test public void dummy(){}
    
	public static void main(String args[]) throws ExlpConfigurationException, OtrProcessingException
    {
        OtrUtilTestBootstrap.init();
 
		try
		{
			VideoFile vf = XmlVideoFileFactory.create("Captain_America__The_First_Avenger_14.03.16_20-15_pro7_145_TVOON_DE.mpg.HQ.avi");
	        JaxbUtil.info(vf);
	        
	        WebMovieFinder omdb = new OmdbMovieSearch();
	        Movies movies = omdb.find(vf);
	        JaxbUtil.info(movies);
	        
	        OmdbMovieQuery q = new OmdbMovieQuery();
	        movies = q.details(movies);
	        JaxbUtil.info(movies);
	        
		}
		catch (UtilsProcessingException e) {logger.error(e.getMessage());}
    }
 }