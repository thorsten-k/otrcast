package de.kisner.otrcast.web;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.exception.OtrProcessingException;
import de.kisner.otrcast.factory.xml.XmlVideoFileFactory;
import de.kisner.otrcast.interfaces.web.WebMovieFinder;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.video.tv.Movies;
import de.kisner.otrcast.test.AbstractOtrcastTest;
import de.kisner.otrcast.test.OtrCastUtilTestBootstrap;
import de.kisner.otrcast.web.myapifilms.MafMovieQuery;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.xml.JaxbUtil;

public class TestMafQuery extends AbstractOtrcastTest
{
    final static Logger logger = LoggerFactory.getLogger(TestMafQuery.class);
	
    private MafMovieQuery maf;
    
    @Before
    public void init()
    {
    	maf = new MafMovieQuery();
    }
    
	@Test
	public void year()
    {
		Assert.assertEquals(2001, maf.year("2001"));
		Assert.assertEquals(2005, maf.year("4 Feb. 2005"));
    }
	
	public static void main(String args[]) throws ExlpConfigurationException, OtrProcessingException
    {
        OtrCastUtilTestBootstrap.init();
 
		try
		{
			VideoFile vf = XmlVideoFileFactory.create("Captain_America__The_First_Avenger_14.03.16_20-15_pro7_145_TVOON_DE.mpg.HQ.avi");
	        JaxbUtil.info(vf);
	        
			WebMovieFinder maf = new MafMovieQuery();
	        Movies movies = maf.find(vf);
	        JaxbUtil.info(movies);
		}
		catch (UtilsProcessingException e) {logger.error(e.getMessage());}
       
        
    }
 }