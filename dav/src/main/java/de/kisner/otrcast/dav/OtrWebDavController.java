package de.kisner.otrcast.dav;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.api.rest.OtrDavRest;
import de.kisner.otrcast.app.OtrWebDavServer;
import de.kisner.otrcast.model.xml.container.Otr;
import de.kisner.otrcast.model.xml.series.Series;
import io.milton.annotations.ChildrenOf;
import io.milton.annotations.Get;
import io.milton.annotations.ResourceController;
import io.milton.annotations.Root;

@ResourceController
public class OtrWebDavController
{
	final static Logger logger = LoggerFactory.getLogger(OtrWebDavServer.class);
	
	private OtrDavRest rest;
	private List<DavSeries> products = new ArrayList<DavSeries>();
	
	public OtrWebDavController()
	{   

		
		ResteasyClient client = new ResteasyClientBuilder().build();
		client.register(new BasicAuthentication("myUser","myPwd"));
		ResteasyWebTarget restTarget = client.target("http://192.168.202.26:8080/otrcast");
        rest = restTarget.proxy(OtrDavRest.class);
		
        Otr otr = rest.getContent("x");
		for(Series series : otr.getSeries())
		{
			products.add(new DavSeries(series));
		}
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
	
	public static final String test = "/Users/thorsten/Dropbox/Presentation Manual.pptx";
	
	@Get
	public InputStream read(DavEpisode episode) throws IOException
	{
		File content = new File(test);
        return FileUtils.openInputStream(content);   
	}
}
