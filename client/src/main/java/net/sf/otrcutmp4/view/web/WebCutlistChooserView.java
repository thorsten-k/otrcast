package net.sf.otrcutmp4.view.web;

import net.sf.otrcutmp4.interfaces.view.ViewCutlistChooser;
import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebCutlistChooserView implements ViewCutlistChooser
{
	final static Logger logger = LoggerFactory.getLogger(WebCutlistChooserView.class);
	
	@Override public void welcome(VideoFiles vFiles)
	{
		logger.info("The Web GUI will be prepared");
	}
	
	@Override
	public void finished(String s)
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



}
