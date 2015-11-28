package de.kisner.otrcast.controller.web.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.api.facade.OtrMediacenterFacade;
import de.kisner.otrcast.controller.OtrCastBootstrap;
import de.kisner.otrcast.controller.facade.OtrMediacenterFacadeBean;
import de.kisner.otrcast.model.ejb.OtrEpisode;
import de.kisner.otrcast.model.ejb.OtrImage;
import de.kisner.otrcast.model.ejb.OtrMovie;
import de.kisner.otrcast.model.ejb.OtrSeason;
import de.kisner.otrcast.model.ejb.OtrSeries;
import de.kisner.otrcast.model.ejb.OtrStorage;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;

public class ImageServlet extends HttpServlet
{
	final static Logger logger = LoggerFactory.getLogger(ImageServlet.class);
	private static final long serialVersionUID = 1;

	private OtrMediacenterFacade<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage> fMc;
	
	public ImageServlet()
	{
		fMc = new OtrMediacenterFacadeBean<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage>(OtrCastBootstrap.buildEmf().createEntityManager());
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
		logger.info("Image Request");
		
        InputStream in = null;
        OutputStream out = null;
        
		try
		{
			OtrSeason season = fMc.find(OtrSeason.class, 2);
			response.setContentType(getServletContext().getMimeType("x."+season.getCover().getFileType()));
			
			in = new ByteArrayInputStream(season.getCover().getData());
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