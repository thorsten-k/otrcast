package net.sf.otrcutmp4.interfaces.controller;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.otrcutmp4.model.xml.cut.CutLists;
import net.sf.otrcutmp4.model.xml.cut.CutListsSelected;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.model.xml.series.Videos;

public interface CutlistChooser
{
	Videos chooseCutlists(VideoFiles vFiles) throws UtilsProcessingException;
	
	CutListsSelected select(CutLists clAvailable, boolean loadCutlist);
	
	void loadCurlists(VideoFiles vf);
	void loadCutlists(Videos videos);
}