package de.kisner.otrcast.controller.web.rest;

import java.util.List;

import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.api.rest.OtrCutRest;
import de.kisner.otrcast.controller.AbstractCutlistChooserController;
import de.kisner.otrcast.interfaces.controller.CutlistChooser;
import de.kisner.otrcast.interfaces.view.client.ViewCutlistChooser;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.cut.VideoFiles;
import de.kisner.otrcast.model.xml.video.Video;
import de.kisner.otrcast.model.xml.video.Videos;
import de.kisner.otrcast.util.OtrConfig;
import de.kisner.otrcast.util.OtrConfig.Credential;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.exlp.util.xml.JaxbUtil;

public class WebCutlistChooserController extends AbstractCutlistChooserController implements CutlistChooser
{
	final static Logger logger = LoggerFactory.getLogger(WebCutlistChooserController.class);

	private OtrCutRest rest;
	
	public WebCutlistChooserController(ViewCutlistChooser view, OtrConfig otrConfig)
	{
		super(view);
		
		String host = otrConfig.getUrl(OtrConfig.Url.OTR);
		logger.info("Connecting to "+host);
		
		ResteasyClient client = new ResteasyClientBuilder().build();
		client.register(new BasicAuthentication(otrConfig.getCredential(Credential.EMAIL,""),otrConfig.getCredential(Credential.PWD,"")));
		ResteasyWebTarget restTarget = client.target(host);
		rest = restTarget.proxy(OtrCutRest.class);
	}
	
	@SuppressWarnings("resource")
	@Override public Videos chooseCutlists(VideoFiles vFiles) throws UtilsProcessingException
	{
		view.welcome(vFiles);

		String token = rest.addCutPackage(vFiles);
		view.srcFolderProcessed(token);
		
		logger.info("Press Any Key To Continue... ... waitning for "+token);
        new java.util.Scanner(System.in).nextLine();
		
        Videos videos = rest.findCutPackage(token);
		JaxbUtil.warn(vFiles);
		view.cutlistsSelected();
		
		return videos;
	}
	
	@Override public List<Video> select(VideoFile vf)
	{
		logger.debug("Uploading ...");
		return null;
	}
}