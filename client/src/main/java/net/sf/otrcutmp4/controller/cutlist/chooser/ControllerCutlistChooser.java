package net.sf.otrcutmp4.controller.cutlist.chooser;

import net.sf.otrcutmp4.model.xml.cut.CutListsAvailable;
import net.sf.otrcutmp4.model.xml.cut.CutListsSelected;

public interface ControllerCutlistChooser
{
	CutListsSelected select(CutListsAvailable clAvailable, boolean loadCutlist); 
}