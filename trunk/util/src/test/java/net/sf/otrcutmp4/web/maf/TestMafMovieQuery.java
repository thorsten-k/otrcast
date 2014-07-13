package net.sf.otrcutmp4.web.maf;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.factory.xml.XmlVideoFileFactory;
import net.sf.otrcutmp4.interfaces.web.WebMovieFinder;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.series.Movies;
import net.sf.otrcutmp4.test.AbstractUtilTest;
import net.sf.otrcutmp4.test.OtrUtilTestBootstrap;

import org.apache.commons.configuration.Configuration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMafMovieQuery extends AbstractUtilTest
{
    final static Logger logger = LoggerFactory.getLogger(TestMafMovieQuery.class);
	
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
	
	public static void main(String args[]) throws Exception
    {
        OtrUtilTestBootstrap.init();

        VideoFile vf = XmlVideoFileFactory.create("Captain_America__The_First_Avenger_14.03.16_20-15_pro7_145_TVOON_DE.mpg.HQ.avi");
        JaxbUtil.info(vf);
        
        WebMovieFinder maf = new MafMovieQuery();
        Movies movies = maf.find(vf);
        JaxbUtil.info(movies);
        
    }
 }