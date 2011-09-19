package net.sf.otrcutmp4.controller.exception;

import java.io.Serializable;

public class OtrProcessingException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public OtrProcessingException() 
	{ 
	} 
 
	public OtrProcessingException(String s) 
	{ 
		super(s); 
	} 
}
