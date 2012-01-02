package net.sf.otrcutmp4.controller.web;

import net.sf.otrcutmp4.controller.AbstractCutlistChooserController;
import net.sf.otrcutmp4.controller.cli.CliCutlistChooserController;
import net.sf.otrcutmp4.controller.rest.CutRestClient;
import net.sf.otrcutmp4.interfaces.controller.CutlistChooser;
import net.sf.otrcutmp4.interfaces.view.ViewCutlistChooser;
import net.sf.otrcutmp4.model.xml.cut.CutListsAvailable;
import net.sf.otrcutmp4.model.xml.cut.CutListsSelected;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.view.cli.CliCutlistChooserView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebCutlistChooserController extends AbstractCutlistChooserController implements CutlistChooser
{
	final static Logger logger = LoggerFactory.getLogger(WebCutlistChooserController.class);

	private CutRestClient rest;
	private CliCutlistChooserController cli;
	
	public WebCutlistChooserController(ViewCutlistChooser view, OtrConfig otrConfig)
	{
		super(view);
		cli = new CliCutlistChooserController(new CliCutlistChooserView());
		rest = new CutRestClient(otrConfig);
	}
	
	@Override
	public VideoFiles chooseCutlists(VideoFiles vFiles)
	{
		view.welcome(vFiles);
		String s = rest.request(vFiles);
		view.finished(s);
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