package de.kisner.otrcast.controller.exception.ejb;

public class OtrLockingException extends Exception 
{ 
	private static final long serialVersionUID = 1;

	public OtrLockingException() 
	  { 
	  } 
	 
	  public OtrLockingException(String s) 
	  { 
	    super(s); 
	  } 

}
