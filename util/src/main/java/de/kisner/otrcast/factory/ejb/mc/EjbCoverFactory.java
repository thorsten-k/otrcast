package de.kisner.otrcast.factory.ejb.mc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.model.Image;

public class EjbCoverFactory<IMAGE extends Image>
{	
final static Logger logger = LoggerFactory.getLogger(EjbCoverFactory.class);
	
	final Class<IMAGE> imageClass;
	
	 public EjbCoverFactory(final Class<IMAGE> imageClass)
	 {
	        this.imageClass=imageClass;
	 }
	 
	 public static <IMAGE extends Image> EjbCoverFactory<IMAGE> factory(final Class<IMAGE> imageClass)
	 {
		 return new EjbCoverFactory<IMAGE>(imageClass);
	 }
	
	public IMAGE build(de.kisner.otrcast.model.xml.mc.Image cover)
	{
		IMAGE ejb = null;
		
		try{ejb = imageClass.newInstance();}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		ejb.setFileType(cover.getFileType());
		ejb.setData(cover.getData());
		
		return ejb;
	}
}