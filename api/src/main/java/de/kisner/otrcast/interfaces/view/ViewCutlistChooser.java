package de.kisner.otrcast.interfaces.view;

import de.kisner.otrcast.model.xml.cut.CutList;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.cut.VideoFiles;

public interface ViewCutlistChooser
{
	void welcome(VideoFiles vFiles);
	void srcFolderProcessed(String s);
	void showFileInfo(int index, VideoFile vFile);
	void showCutlistInfo(int i, CutList cl, boolean showAuthor, boolean showRanking, boolean showComment, boolean showFile);
	void cutlistsSelected();
	void additionalFile(VideoFiles vFiles);
}
