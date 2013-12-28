package net.sf.otrcutmp4.factory.xml.mc;

import net.sf.otrcutmp4.interfaces.model.Image;
import net.sf.otrcutmp4.model.xml.otr.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlCoverFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlCoverFactory.class);
	
	private net.sf.otrcutmp4.model.xml.mc.Image q;
	
	public XmlCoverFactory(Query query){this(query.getImage());}
	public XmlCoverFactory(net.sf.otrcutmp4.model.xml.mc.Image q){this.q=q;}
	
	public net.sf.otrcutmp4.model.xml.mc.Image build(Image ejb)
	{
		net.sf.otrcutmp4.model.xml.mc.Image xml = new net.sf.otrcutmp4.model.xml.mc.Image();
		
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetType()){xml.setType(ejb.getType());}
		if(q.isSetData()){xml.setData(ejb.getData());}
		return xml;
	}
}