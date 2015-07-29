package de.kisner.otrcast.controller.web.rest;

import java.util.Date;

import de.kisner.otrcast.interfaces.rest.OtrSimpleRest;

public class OtrSimpleRestService implements OtrSimpleRest
{
	@Override 
	public String hello()
	{
		return "Hello World "+this.getClass().getSimpleName()+" "+(new Date());
	}

	@Override 
	public String getBooks()
	{
		return "books";
	}

	@Override 
	public String getBook(String id)
	{
		return id;
	}
}