package de.kisner.otrcast.factory.xml.mc;

import net.sf.exlp.util.DateUtil;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.model.Storage;

public class XmlStorageFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlStorageFactory.class);
	
	private de.kisner.otrcast.model.xml.mc.Storage q;
	
//	public XmlStorageFactory(Query query){this(query.getCover());}
	public XmlStorageFactory(de.kisner.otrcast.model.xml.mc.Storage q){this.q=q;}
	
	public de.kisner.otrcast.model.xml.mc.Storage build(Storage ejb)
	{
		de.kisner.otrcast.model.xml.mc.Storage xml = new de.kisner.otrcast.model.xml.mc.Storage();
		
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetName()){xml.setName(ejb.getName());}
		if(q.isSetHash()){xml.setHash(ejb.getHash());}
		if(q.isSetSize()){xml.setSize(ejb.getSize());}
		if(q.isSetLastModified()){xml.setLastModified(DateUtil.toXmlGc(ejb.getRecord()));}
		return xml;
	}
	
	public static de.kisner.otrcast.model.xml.mc.Storage buildPathName(File file)
	{
		de.kisner.otrcast.model.xml.mc.Storage xml = new de.kisner.otrcast.model.xml.mc.Storage();
		xml.setPath(file.getParentFile().getAbsolutePath());
		xml.setName(file.getName());
		return xml;
	}
}
