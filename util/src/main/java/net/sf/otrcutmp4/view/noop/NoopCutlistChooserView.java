package net.sf.otrcutmp4.view.noop;

import net.sf.otrcutmp4.interfaces.view.ViewCutlistChooser;
import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoopCutlistChooserView implements ViewCutlistChooser
{
	final static Logger logger = LoggerFactory.getLogger(NoopCutlistChooserView.class);
	
	@Override public void welcome(VideoFiles vFiles){}
	@Override public void showCutlistInfo(int i, CutList cl, boolean showAuthor,boolean showRanking, boolean showComment, boolean showFile) {}
	@Override public void showFileInfo(int index, VideoFile vFile) {}
	@Override public void srcFolderProcessed(String s) {}
	@Override public void cutlistsSelected() {}

}
