package de.kisner.otrcast.dav;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.app.OtrWebDavServer;
import io.milton.annotations.ChildrenOf;
import io.milton.annotations.ResourceController;
import io.milton.annotations.Root;

@ResourceController
public class OtrWebDavController
{
	final static Logger logger = LoggerFactory.getLogger(OtrWebDavServer.class);
	
	private List<DavSeries> products = new ArrayList<DavSeries>();
	
	public OtrWebDavController()
	{
	        products.add(new DavSeries("hello"));
	        products.add(new DavSeries("world"));
	}
	 
	@Root
    public OtrWebDavController getRoot()
	{
        return this;
    }
	
	@ChildrenOf
    public List<DavSeries> getSeries(OtrWebDavController root)
	{
        return products;
    }
	
	@ChildrenOf
	public List<DavEpisode> getProductFiles(DavSeries series)
	   {
	        return series.getEpisodes();
	    }
}
