package de.kisner.otrcast.controller.web.servlet;

import java.io.File;
import java.io.FileInputStream;
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

import de.kisner.otrcast.controller.OtrCutMp4Bootstrap;
import de.kisner.otrcast.controller.facade.OtrMediacenterFacadeBean;
import de.kisner.otrcast.interfaces.facade.OtrMediacenterFacade;
import de.kisner.otrcast.model.OtrEpisode;
import de.kisner.otrcast.model.OtrImage;
import de.kisner.otrcast.model.OtrMovie;
import de.kisner.otrcast.model.OtrSeason;
import de.kisner.otrcast.model.OtrSeries;
import de.kisner.otrcast.model.OtrStorage;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;

public class StorageServlet extends HttpServlet
{
	final static Logger logger = LoggerFactory.getLogger(StorageServlet.class);
	private static final long serialVersionUID = 1;

	private OtrMediacenterFacade<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage> fMc;
	
	public StorageServlet()
	{
		fMc = new OtrMediacenterFacadeBean<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage>(OtrCutMp4Bootstrap.buildEmf().createEntityManager());
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
		logger.info("Storage Request");
		
        InputStream in = null;
        OutputStream out = null;
        
		try
		{
			OtrStorage storage = fMc.find(OtrStorage.class, 2);
			response.setContentType(getServletContext().getMimeType("x.mp4"));
			response.setContentLength(new Long(storage.getSize()).intValue());
			
			File f = new File(storage.getName());
			in = new FileInputStream(f);
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