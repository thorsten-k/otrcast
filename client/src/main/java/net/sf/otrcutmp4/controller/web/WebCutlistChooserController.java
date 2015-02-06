package net.sf.otrcutmp4.controller.web;

import java.util.List;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.ahtutils.web.rest.auth.RestEasyPreemptiveClientExecutor;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.AbstractCutlistChooserController;
import net.sf.otrcutmp4.interfaces.controller.CutlistChooser;
import net.sf.otrcutmp4.interfaces.rest.OtrCutRest;
import net.sf.otrcutmp4.interfaces.view.ViewCutlistChooser;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.model.xml.series.Video;
import net.sf.otrcutmp4.model.xml.series.Videos;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.OtrConfig.Credential;

import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebCutlistChooserController extends AbstractCutlistChooserController implements CutlistChooser
{
	final static Logger logger = LoggerFactory.getLogger(WebCutlistChooserController.class);

	private OtrCutRest rest;
	
	public WebCutlistChooserController(ViewCutlistChooser view, OtrConfig otrConfig)
	{
		super(view);
		
		String host = otrConfig.getUrl(OtrConfig.Url.OTR);
		logger.info("Connecting to "+host);
		
		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
		ClientExecutor clientExecutor = RestEasyPreemptiveClientExecutor.factory(
				otrConfig.getCredential(Credential.EMAIL,""),
				otrConfig.getCredential(Credential.PWD,""));
		rest = ProxyFactory.create(OtrCutRest.class, host,clientExecutor);
	}
	
	@Override public Videos chooseCutlists(VideoFiles vFiles) throws UtilsProcessingException
	{
		view.welcome(vFiles);

		String token = rest.addCutPackage(vFiles);
		view.srcFolderProcessed(token);
		
		logger.info("Press Any Key To Continue...");
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