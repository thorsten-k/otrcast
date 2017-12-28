package de.kisner.otrcast.view.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.view.client.ViewCutlistChooser;
import de.kisner.otrcast.model.xml.cut.CutList;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.cut.VideoFiles;

public class WebCutlistChooserView implements ViewCutlistChooser
{
	final static Logger logger = LoggerFactory.getLogger(WebCutlistChooserView.class);
	
	@Override public void welcome(VideoFiles vFiles)
	{
		logger.info("The Web GUI will be prepared");
	}
	
	@Override
	public void srcFolderProcessed(String s)
	{
		logger.info("GUI available at http://otr-series.appspot.com/client with tolken '"+s+"'");
	}

	@Override
	public void showCutlistInfo(int i, CutList cl, boolean showAuthor,boolean showRanking, boolean showComment, boolean showFile)
	{
		logger.trace("nothing to do here");
	}

	@Override
	public void showFileInfo(int index,VideoFile vFile)
	{
		logger.trace("nothing to do here");
	}

	@Override
	public void cutlistsSelected()
	{
		logger.info("Cutlists selected");
	}

	@Override public void additionalFile(VideoFiles vFiles) {}
}
