package de.kisner.otrcast.interfaces.web;

public interface UrlGenerator
{
	String enclosure(long id);
	String image(long id,String type);
}
