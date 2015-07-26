package net.sf.otrcutmp4.controller.web.servlet;

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

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.otrcutmp4.controller.OtrCutMp4Bootstrap;
import net.sf.otrcutmp4.controller.facade.OtrMediacenterFacadeBean;
import net.sf.otrcutmp4.controller.processor.RssXmlProcessor;
import net.sf.otrcutmp4.controller.web.rss.OtrCastUrlGenerator;
import net.sf.otrcutmp4.interfaces.facade.OtrMediacenterFacade;
import net.sf.otrcutmp4.interfaces.web.UrlGenerator;
import net.sf.otrcutmp4.model.OtrEpisode;
import net.sf.otrcutmp4.model.OtrImage;
import net.sf.otrcutmp4.model.OtrMovie;
import net.sf.otrcutmp4.model.OtrSeason;
import net.sf.otrcutmp4.model.OtrSeries;
import net.sf.otrcutmp4.model.OtrStorage;
import net.sf.otrcutmp4.model.xml.rss.Rss;

public class PodcastServlet extends HttpServlet
{
	final static Logger logger = LoggerFactory.getLogger(PodcastServlet.class);
	private static final long serialVersionUID = 1;
	
	private OtrMediacenterFacade<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage> fMc;
	private RssXmlProcessor rssProcessor;
	
	public PodcastServlet()
	{
		UrlGenerator urlGenerator = new OtrCastUrlGenerator();
		fMc = new OtrMediacenterFacadeBean<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage>(OtrCutMp4Bootstrap.buildEmf().createEntityManager(),urlGenerator);
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