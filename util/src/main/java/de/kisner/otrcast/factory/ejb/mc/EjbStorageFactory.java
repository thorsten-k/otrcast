package de.kisner.otrcast.factory.ejb.mc;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.model.Storage;

public class EjbStorageFactory<STORAGE extends Storage>
{	
final static Logger logger = LoggerFactory.getLogger(EjbStorageFactory.class);
	
	final Class<STORAGE> cStorage;
	
	 public EjbStorageFactory(final Class<STORAGE> cStorage)
	 {
	        this.cStorage=cStorage;
	 }
	 
	 public static <STORAGE extends Storage> EjbStorageFactory<STORAGE> factory(final Class<STORAGE> cStorage)
	 {
		 return new EjbStorageFactory<STORAGE>(cStorage);
	 }
	
	public STORAGE build(de.kisner.otrcast.model.xml.mc.File xml)
	{
		return build(xml.getName(),xml.getHash(),xml.getSize(),xml.getLastModified().toGregorianCalendar().getTime());
	}
	
	public STORAGE build(java.io.File f)
	{
		
		return build(f.getAbsolutePath(),null,f.length(),new Date(f.lastModified()));
	}
	
	public STORAGE build(String name, String hash, long size, Date lastModifed)
	{
		STORAGE ejb = null;
		
		try{ejb = cStorage.newInstance();}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		ejb.setName(name);
		ejb.setHash(hash);
		ejb.setSize(size);
		ejb.setRecord(lastModifed);
		
		return ejb;
	}
}