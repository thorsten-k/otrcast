package net.sf.otrcutmp4.controller.web.rest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.otrcutmp4.OtrCutMp4Bootstrap;
import net.sf.otrcutmp4.controller.facade.OtrSeriesFacadeBean;
import net.sf.otrcutmp4.interfaces.rest.OtrMediacenterRest;
import net.sf.otrcutmp4.model.OtrSeries;
import net.sf.otrcutmp4.model.xml.container.Otr;
import net.sf.otrcutmp4.model.xml.series.Series;

@Path("/rest/mediacenter")
public class OtrMediacenterRestService implements OtrMediacenterRest
{
	private EntityManager em;
	private UtilsFacadeBean ufb;
	private OtrSeriesFacadeBean osfb;
	
	private void init()
	{
		if(em==null)
		{
			EntityManagerFactory emf = OtrCutMp4Bootstrap.buildEmf();
			em = emf.createEntityManager();
		}
		if(ufb==null){ufb = new UtilsFacadeBean(em);}
		if(osfb==null){osfb = new OtrSeriesFacadeBean(em);}
	}
	
	@Override
	@GET @Path("/series/all")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.TEXT_PLAIN)
	public Otr allSeries()
	{
		init();
		Otr otr = new Otr();
		for(OtrSeries ejb : ufb.all(OtrSeries.class))
		{
			Series xml = new Series();
			xml.setId(ejb.getId());
			xml.setName(ejb.getName());
			otr.getSeries().add(xml);
		}
		return otr;
	}

	@Override
	@GET @Path("/series/{id}")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.TEXT_PLAIN)
	public Series getSeason(@PathParam("id") long seriesId)
	{
		init();
		Series xml = new Series();
		xml.setId(seriesId);
		return xml;
	}	
}