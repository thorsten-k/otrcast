package net.sf.otrcutmp4.controller.noop;

import net.sf.otrcutmp4.interfaces.controller.CutlistChooser;
import net.sf.otrcutmp4.model.xml.cut.CutListsAvailable;
import net.sf.otrcutmp4.model.xml.cut.CutListsSelected;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.model.xml.series.Videos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoopCutlistChooserController implements CutlistChooser
{
	final static Logger logger = LoggerFactory.getLogger(NoopCutlistChooserController.class); 
	
	@Override public CutListsSelected select(CutListsAvailable clAvailable, boolean loadCutlist) {return null;}
	@Override public void loadCurlists(VideoFiles vf) {}
	@Override public Videos chooseCutlists(VideoFiles vFiles) {return null;}
}