package de.kisner.otrcast.dav;

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
import de.kisner.otrcast.model.xml.video.tv.Series;
import io.milton.annotations.Authenticate;
import io.milton.annotations.ChildrenOf;
import io.milton.annotations.Get;
import io.milton.annotations.ResourceController;
import io.milton.annotations.Root;
import io.milton.annotations.Users;
import io.milton.http.http11.auth.DigestResponse;
import net.sf.exlp.util.xml.JaxbUtil;

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
        JaxbUtil.info(otr);
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
	
	
	@Get
	public InputStream read(DavEpisode episode) throws IOException
	{
        return FileUtils.openInputStream(episode.toFile());   
	}
	
	@ChildrenOf
	@Users
	public List<DavUser> getUsers(OtrWebDavController root)
	{
		logger.info("Get Users");
		List<DavUser> result = new ArrayList<>();
		result.add(new DavUser("x"));
		return result;
	}
	
	@Authenticate
    public Boolean verifyPassword(DavUser m, String requestedPassword)
    {
		logger.info("verify "+requestedPassword);
		return true;
    }
	
	@Authenticate
	public Boolean verifyDigestPassword(DavUser m, DigestResponse digest)
	{
		logger.info("verify "+digest.toString());
		return true;
	}
}