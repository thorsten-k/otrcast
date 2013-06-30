package net.sf.otrcutmp4.controller.facade;

import java.io.Serializable;

import javax.persistence.EntityManager;

import net.sf.otrcutmp4.interfaces.facade.OtrSeriesFacade;
import net.sf.otrcutmp4.interfaces.model.Series;
import net.sf.otrcutmp4.model.xml.series.Season;

public class OtrSeriesFacadeBean implements Serializable
{	
	static final long serialVersionUID=1;

	protected EntityManager em;
	
	public OtrSeriesFacadeBean(EntityManager em)
	{
		this.em=em;
	}
	
	
}