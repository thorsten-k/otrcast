package net.sf.otrcutmp4.factory.xml.mc;

import net.sf.otrcutmp4.interfaces.model.Image;
import net.sf.otrcutmp4.model.xml.otr.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlImageFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlImageFactory.class);
	
	private net.sf.otrcutmp4.model.xml.mc.Image q;
	
	public XmlImageFactory(Query query){this(query.getImage());}
	public XmlImageFactory(net.sf.otrcutmp4.model.xml.mc.Image q){this.q=q;}
	
	public net.sf.otrcutmp4.model.xml.mc.Image build(Image ejb)
	{
		net.sf.otrcutmp4.model.xml.mc.Image xml = new net.sf.otrcutmp4.model.xml.mc.Image();
		
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetFileType()){xml.setFileType(ejb.getFileType());}
		if(q.isSetData()){xml.setData(ejb.getData());}
		if(q.isSetUrl()){xml.setUrl(ejb.getUrl());}
		return xml;
	}
	
	public static net.sf.otrcutmp4.model.xml.mc.Image build(String url)
	{
		net.sf.otrcutmp4.model.xml.mc.Image xml = new net.sf.otrcutmp4.model.xml.mc.Image();
		xml.setUrl(url);
		return xml;
	}
}
