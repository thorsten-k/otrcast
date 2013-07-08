package net.sf.otrcutmp4.factory.xml.mc;

import net.sf.exlp.util.DateUtil;
import net.sf.otrcutmp4.interfaces.model.Storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlStorageFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlStorageFactory.class);
	
	private net.sf.otrcutmp4.model.xml.mc.Storage q;
	
//	public XmlStorageFactory(Query query){this(query.getCover());}
	public XmlStorageFactory(net.sf.otrcutmp4.model.xml.mc.Storage q){this.q=q;}
	
	public net.sf.otrcutmp4.model.xml.mc.Storage build(Storage ejb)
	{
		net.sf.otrcutmp4.model.xml.mc.Storage xml = new net.sf.otrcutmp4.model.xml.mc.Storage();
		
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetName()){xml.setName(ejb.getName());}
		if(q.isSetHash()){xml.setHash(ejb.getHash());}
		if(q.isSetSize()){xml.setSize(ejb.getSize());}
		if(q.isSetLastModified()){xml.setLastModified(DateUtil.toXmlGc(ejb.getRecord()));}
		return xml;
	}
}
