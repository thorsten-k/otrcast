package de.kisner.otrcast.controller.web.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.jdom2.output.Format;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.api.facade.OtrMediacenterFacade;
import de.kisner.otrcast.controller.OtrCastBootstrap;
import de.kisner.otrcast.controller.facade.OtrMediacenterFacadeBean;
import de.kisner.otrcast.controller.processor.RssXmlProcessor;
import de.kisner.otrcast.controller.web.rss.OtrCastUrlGenerator;
import de.kisner.otrcast.interfaces.web.UrlGenerator;
import de.kisner.otrcast.model.ejb.OtrEpisode;
import de.kisner.otrcast.model.ejb.OtrImage;
import de.kisner.otrcast.model.ejb.OtrMovie;
import de.kisner.otrcast.model.ejb.OtrSeason;
import de.kisner.otrcast.model.ejb.OtrSeries;
import de.kisner.otrcast.model.ejb.OtrStorage;
import de.kisner.otrcast.model.xml.rss.Rss;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.exlp.util.xml.JDomUtil;

public class PodcastServlet extends HttpServlet
{
	final static Logger logger = LoggerFactory.getLogger(PodcastServlet.class);
	private static final long serialVersionUID = 1;
	
	private OtrMediacenterFacade<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage> fMc;
	private RssXmlProcessor rssProcessor;
	
	public PodcastServlet()
	{
		UrlGenerator urlGenerator = new OtrCastUrlGenerator();
		fMc = new OtrMediacenterFacadeBean<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage>(OtrCastBootstrap.buildEmf().createEntityManager(),urlGenerator);
		rssProcessor = new RssXmlProcessor();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html; charset=utf-8");
        
        InputStream in = null;
        OutputStream out = null;
        
		try
		{
			OtrSeason season = fMc.find(OtrSeason.class, 2);
			Rss rss = fMc.rss(season);
			in = JDomUtil.toInputStream(rssProcessor.transform(rss), Format.getPrettyFormat());
			
//			File f = new File("/Volumes/ramdisk/rss-orig.xml");
//			in = new FileInputStream(f);
			
			out = response.getOutputStream();
	        IOUtils.copy(in,out);
		}
		catch (UtilsNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
		   IOUtils.closeQuietly(in);
		   IOUtils.closeQuietly(out);
		}
    }
}