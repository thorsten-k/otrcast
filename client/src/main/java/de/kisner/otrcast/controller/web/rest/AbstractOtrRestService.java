package de.kisner.otrcast.controller.web.rest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.jeesl.controller.facade.jx.JeeslFacadeBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.OtrCastBootstrap;
import de.kisner.otrcast.controller.facade.OtrMediacenterFacadeBean;
import de.kisner.otrcast.model.ejb.OtrEpisode;
import de.kisner.otrcast.model.ejb.OtrImage;
import de.kisner.otrcast.model.ejb.OtrMovie;
import de.kisner.otrcast.model.ejb.OtrSeason;
import de.kisner.otrcast.model.ejb.OtrSeries;
import de.kisner.otrcast.model.ejb.OtrStorage;

public class AbstractOtrRestService
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOtrRestService.class);
	
	protected EntityManager em;
    protected JeeslFacadeBean ufb;
    protected OtrMediacenterFacadeBean<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage> osfb;
	
	protected void init()
	{
		if(em==null)
		{
			EntityManagerFactory emf = OtrCastBootstrap.buildEmf();
			em = emf.createEntityManager();
		}
		if(ufb==null){ufb = new JeeslFacadeBean(em);}
		if(osfb==null){osfb = new OtrMediacenterFacadeBean<OtrMovie,OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage>(em);}
	}
}