package de.kisner.otrcast.dav;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.app.WebDavServer;
import io.milton.annotations.ChildrenOf;
import io.milton.annotations.ResourceController;
import io.milton.annotations.Root;

@ResourceController
public class HelloWorldController
{
	final static Logger logger = LoggerFactory.getLogger(WebDavServer.class);
	
	private List<Product> products = new ArrayList<Product>();
	
	public HelloWorldController()
	{
	        products.add(new Product("hello"));
	        products.add(new Product("world"));
	}
	 
	@Root
    public HelloWorldController getRoot() {
        return this;
    }
	
	@ChildrenOf
    public List<Product> getProducts(HelloWorldController root)
	{
        return products;
    }
}
