package de.kisner.otrcast.controller.exception.ejb;

public class OtrContraintViolationException extends Exception 
{ 
	private static final long serialVersionUID = 1;

	public OtrContraintViolationException() 
	  { 
	  } 
	 
	  public OtrContraintViolationException(String s) 
	  { 
	    super(s); 
	  } 

}
