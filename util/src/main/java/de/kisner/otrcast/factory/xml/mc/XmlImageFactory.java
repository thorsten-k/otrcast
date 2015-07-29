package de.kisner.otrcast.factory.xml.mc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.model.Image;
import de.kisner.otrcast.model.xml.otr.Query;

public class XmlImageFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlImageFactory.class);
	
	private de.kisner.otrcast.model.xml.mc.Image q;
	
	public XmlImageFactory(Query query){this(query.getImage());}
	public XmlImageFactory(de.kisner.otrcast.model.xml.mc.Image q){this.q=q;}
	
	public de.kisner.otrcast.model.xml.mc.Image build(Image ejb)
	{
		de.kisner.otrcast.model.xml.mc.Image xml = new de.kisner.otrcast.model.xml.mc.Image();
		
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetFileType()){xml.setFileType(ejb.getFileType());}
		if(q.isSetData()){xml.setData(ejb.getData());}
		if(q.isSetUrl()){xml.setUrl(ejb.getUrl());}
		return xml;
	}
	
	public static de.kisner.otrcast.model.xml.mc.Image build(String url)
	{
		de.kisner.otrcast.model.xml.mc.Image xml = new de.kisner.otrcast.model.xml.mc.Image();
		xml.setUrl(url);
		return xml;
	}
}
