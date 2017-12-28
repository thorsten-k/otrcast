package de.kisner.otrcast.view.noop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.view.client.ViewCutlistChooser;
import de.kisner.otrcast.model.xml.cut.CutList;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.cut.VideoFiles;

public class NoopCutlistChooserView implements ViewCutlistChooser
{
	final static Logger logger = LoggerFactory.getLogger(NoopCutlistChooserView.class);
	
	@Override public void welcome(VideoFiles vFiles){}
	@Override public void showCutlistInfo(int i, CutList cl, boolean showAuthor,boolean showRanking, boolean showComment, boolean showFile) {}
	@Override public void showFileInfo(int index, VideoFile vFile) {}
	@Override public void srcFolderProcessed(String s) {}
	@Override public void cutlistsSelected() {}
	@Override public void additionalFile(VideoFiles vFiles) {}

}
