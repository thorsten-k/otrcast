package de.kisner.otrcast.controller.exception;

public class OtrInternalErrorException extends Exception 
{ 
	private static final long serialVersionUID = 1;

	public OtrInternalErrorException() 
	  { 
	  } 
	 
	  public OtrInternalErrorException(String s) 
	  { 
	    super(s); 
	  } 

}
