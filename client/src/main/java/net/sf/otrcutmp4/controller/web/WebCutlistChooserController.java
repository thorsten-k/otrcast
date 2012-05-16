package net.sf.otrcutmp4.controller.web;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.ahtutils.web.rest.RestEasyPreemptiveClientExecutor;
import net.sf.otrcutmp4.controller.AbstractCutlistChooserController;
import net.sf.otrcutmp4.controller.cli.CliCutlistChooserController;
import net.sf.otrcutmp4.interfaces.controller.CutlistChooser;
import net.sf.otrcutmp4.interfaces.rest.OtrCutRest;
import net.sf.otrcutmp4.interfaces.view.ViewCutlistChooser;
import net.sf.otrcutmp4.model.xml.cut.CutListsAvailable;
import net.sf.otrcutmp4.model.xml.cut.CutListsSelected;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.view.cli.CliCutlistChooserView;

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
	private CliCutlistChooserController cli;
	
	public WebCutlistChooserController(ViewCutlistChooser view, OtrConfig otrConfig)
	{
		super(view);
		cli = new CliCutlistChooserController(new CliCutlistChooserView());
		
		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
		ClientExecutor clientExecutor = RestEasyPreemptiveClientExecutor.factory("user","pwd");
		rest = ProxyFactory.create(OtrCutRest.class, "http://localhost:8080/otr",clientExecutor);
	}
	
	@Override
	public VideoFiles chooseCutlists(VideoFiles vFiles) throws UtilsProcessingException
	{
		view.welcome(vFiles);
		String token = rest.addCutPackage(vFiles);
		view.srcFolderProcessed(token);
		
		System.out.println("Press Any Key To Continue...");
        new java.util.Scanner(System.in).nextLine();
		
		vFiles = rest.findCutPackage(token);
		view.cutlistsSelected();

		return vFiles;
	}
	
	@Override public CutListsSelected select(CutListsAvailable clAvailable, boolean loadCutlist)
	{
		logger.debug("Uploading ...");
		return null;
	}
	
	@Override public void loadCurlists(VideoFiles vf)
	{
		cli.loadCurlists(vf);
	}
}