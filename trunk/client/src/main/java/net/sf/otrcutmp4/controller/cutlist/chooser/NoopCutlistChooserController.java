package net.sf.otrcutmp4.controller.cutlist.chooser;

import net.sf.otrcutmp4.model.xml.cut.CutListsAvailable;
import net.sf.otrcutmp4.model.xml.cut.CutListsSelected;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoopCutlistChooserController implements ControllerCutlistChooser
{
	final static Logger logger = LoggerFactory.getLogger(NoopCutlistChooserController.class);

	@Override
	public CutListsSelected select(CutListsAvailable clAvailable, boolean loadCutlist) {return null;}
	
}