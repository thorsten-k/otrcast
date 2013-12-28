package net.sf.otrcutmp4.factory.ejb.mc;

import net.sf.otrcutmp4.interfaces.model.Image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	public IMAGE build(net.sf.otrcutmp4.model.xml.mc.Image cover)
	{
		IMAGE ejb = null;
		
		try{ejb = imageClass.newInstance();}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		ejb.setType(cover.getType());
		ejb.setData(cover.getData());
		
		return ejb;
	}
}