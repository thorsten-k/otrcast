package de.kisner.otrcast.controller.web.rest;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.OtrCutMp4Bootstrap;
import de.kisner.otrcast.controller.facade.OtrMediacenterFacadeBean;
import de.kisner.otrcast.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class AbstractOtrRestService
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOtrRestService.class);
	
	protected EntityManager em;
    protected UtilsFacadeBean ufb;
    protected OtrMediacenterFacadeBean<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage> osfb;
	
	protected void init()
	{
		if(em==null)
		{
			EntityManagerFactory emf = OtrCutMp4Bootstrap.buildEmf();
			em = emf.createEntityManager();
		}
		if(ufb==null){ufb = new UtilsFacadeBean(em);}
		if(osfb==null){osfb = new OtrMediacenterFacadeBean<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage>(em);}
	}
}