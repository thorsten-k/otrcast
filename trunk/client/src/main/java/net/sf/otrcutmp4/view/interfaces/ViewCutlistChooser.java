package net.sf.otrcutmp4.view.interfaces;

import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;

public interface ViewCutlistChooser
{
	void welcome(VideoFiles vFiles);
	void showFileInfo(int index, VideoFile vFile);
	void showCutlistInfo(int i, CutList cl, boolean showAuthor, boolean showRanking, boolean showComment, boolean showFile);
}
