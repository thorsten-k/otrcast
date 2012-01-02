package net.sf.otrcutmp4.interfaces.controller;

import net.sf.otrcutmp4.model.xml.cut.CutListsAvailable;
import net.sf.otrcutmp4.model.xml.cut.CutListsSelected;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;

public interface CutlistChooser
{
	VideoFiles chooseCutlists(VideoFiles vFiles);
	
	CutListsSelected select(CutListsAvailable clAvailable, boolean loadCutlist);
	
	void loadCurlists(VideoFiles vf);
}