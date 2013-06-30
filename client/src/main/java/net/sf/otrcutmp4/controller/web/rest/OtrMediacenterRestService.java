package net.sf.otrcutmp4.controller.web.rest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.otrcutmp4.OtrCutMp4Bootstrap;
import net.sf.otrcutmp4.interfaces.rest.OtrMediacenterRest;
import net.sf.otrcutmp4.model.OtrSeries;
import net.sf.otrcutmp4.model.xml.container.Otr;
import net.sf.otrcutmp4.model.xml.series.Series;

@Path("/rest/mediacenter")
public class OtrMediacenterRestService implements OtrMediacenterRest
{
	private EntityManager em;
	private UtilsFacadeBean ufb;
	
	private UtilsFacadeBean getUfb()
	{
		if(ufb==null)
		{
			EntityManagerFactory emf = OtrCutMp4Bootstrap.buildEmf();
			em = emf.createEntityManager();
			ufb = new UtilsFacadeBean(em);
		}
		return ufb;
	}
	
	@Override
	@GET @Path("/series/all")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.TEXT_PLAIN)
	public Otr allSeries()
	{
		Otr otr = new Otr();
		for(OtrSeries series : getUfb().all(OtrSeries.class))
		{
			Series xml = new Series();
			xml.setName(series.getName());
			otr.getSeries().add(xml);
		}
		return otr;
	}	
}