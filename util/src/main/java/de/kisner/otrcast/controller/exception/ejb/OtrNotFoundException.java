package de.kisner.otrcast.controller.exception.ejb;

public class OtrNotFoundException extends Exception 
{ 
	private static final long serialVersionUID = 1;

	public OtrNotFoundException() 
	  { 
	  } 
	 
	  public OtrNotFoundException(String s) 
	  { 
	    super(s); 
	  } 

}
