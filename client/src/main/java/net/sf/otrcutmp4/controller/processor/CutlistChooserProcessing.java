package net.sf.otrcutmp4.controller.processor;

import net.sf.otrcutmp4.interfaces.controller.CutlistChooser;
import net.sf.otrcutmp4.interfaces.view.ViewCutlistChooser;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CutlistChooserProcessing
{
	final static Logger logger = LoggerFactory.getLogger(CutlistChooserProcessing.class);
	
	private boolean showAuthor, showRanking, showComment, showFile;

	private ViewCutlistChooser view;
	private CutlistChooser controller;
	
	public CutlistChooserProcessing(ViewCutlistChooser view,CutlistChooser controller)
	{
		this.view=view;
		this.controller=controller;
		showAuthor = true;
		showRanking = true;
		showComment = true;
		showFile = true;
	}
	
	public void setRenameOutput()
	{
		 setShowAuthor(false);
         setShowRanking(false);
         setShowComment(false);
	}


	
	public VideoFiles chooseFileRename(VideoFiles vFiles)
	{
		logger.info("");
		logger.info("Choose file renames for "+vFiles.getVideoFile().size()+" files");
		logger.info("\tYou have to choose a single name e.g. with '1'");
		logger.info("\tIgnore the file by pressing ENTER");
		for(VideoFile vf : vFiles.getVideoFile())
		{
			if(vf.isSetCutListsAvailable() && vf.getCutListsAvailable().isSetCutList())
			{
				logger.warn("NYI");
				//chooseCutlist(-1,vf,false);
			}
		}
		return vFiles;
	}
	
	
	
	public void setShowAuthor(boolean showAuthor) {this.showAuthor = showAuthor;}
	public void setShowComment(boolean showComment) {this.showComment = showComment;}
	public void setShowRanking(boolean showRanking) {this.showRanking = showRanking;}
}