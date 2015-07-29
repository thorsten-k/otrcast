package de.kisner.otrcast.controller.exception.ejb;

public class OtrIntegrityException extends Exception 
{ 
	private static final long serialVersionUID = 1;

	public OtrIntegrityException() 
	  { 
	  } 
	 
	  public OtrIntegrityException(String s) 
	  { 
	    super(s); 
	  } 

}
