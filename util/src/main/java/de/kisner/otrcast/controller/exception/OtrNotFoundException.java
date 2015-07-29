package de.kisner.otrcast.controller.exception;

import java.io.Serializable;

public class OtrNotFoundException extends Exception implements Serializable
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
