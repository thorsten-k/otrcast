package net.sf.otrcutmp4.controller.cutlist.chooser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.sf.exlp.event.handler.EhResultContainer;
import net.sf.exlp.event.impl.JaxbEvent;
import net.sf.exlp.listener.LogListener;
import net.sf.exlp.listener.impl.LogListenerHttp;
import net.sf.exlp.parser.LogParser;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.controller.processor.exlp.parser.CutlistParser;
import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.cut.CutListsAvailable;
import net.sf.otrcutmp4.model.xml.cut.CutListsSelected;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;
import net.sf.otrcutmp4.view.interfaces.ViewCutlistChooser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CutlistChooserProcessing
{
	final static Logger logger = LoggerFactory.getLogger(CutlistChooserProcessing.class);
	
	private boolean showAuthor, showRanking, showComment, showFile;

	private ViewCutlistChooser view;
	private ControllerCutlistChooser controller;
	
	public CutlistChooserProcessing(ViewCutlistChooser view,ControllerCutlistChooser controller)
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

	public VideoFiles chooseCutlists(VideoFiles vFiles)
	{
		view.welcome(vFiles);
		for(int i=0;i<vFiles.getVideoFile().size();i++)
		{
			VideoFile vf = vFiles.getVideoFile().get(i);
			JaxbUtil.debug(vf);
			if(vf.isSetCutListsAvailable() && vf.getCutListsAvailable().isSetCutList())
			{
				chooseCutlist(i,vf,true);
			}
			JaxbUtil.debug(vf);
		}
		return vFiles;
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
				chooseCutlist(-1,vf,false);
			}
		}
		return vFiles;
	}
	
	private void chooseCutlist(int index, VideoFile vf,boolean loadCutlist)
	{
		view.showFileInfo(index,vf);
		for(int i=0;i<vf.getCutListsAvailable().getCutList().size();i++)
		{
			CutList cl = vf.getCutListsAvailable().getCutList().get(i);
			view.showCutlistInfo(i, cl,showAuthor,showRanking,showComment,showFile);
		}
		
		vf.setCutListsSelected(controller.select(vf.getCutListsAvailable(), loadCutlist));
	}
	
	private CutListsSelected loadCutlists(List<Integer> selected, CutListsAvailable clAvailable)
	{
		CutListsSelected clSelected = new CutListsSelected();
		
		for(int i : selected)
		{		
			clSelected.getCutList().add(loadCutlist(i, clAvailable.getCutList().get(i)));
		}
		
		return clSelected;
	}
	
	private CutList loadCutlist(int i, CutList clChosen)
	{
		String http = "http://cutlist.at/getfile.php?id="+clChosen.getId();
		
		logger.info("Trying to download selection "+i);
		logger.debug("\t"+http);
	
		EhResultContainer leh = new EhResultContainer();
		LogParser lp = new CutlistParser(leh);
		
		LogListener ll = new LogListenerHttp(lp);
		ll.processSingle(http);
		
		JaxbEvent event = (JaxbEvent)leh.getSingleResult();
		CutList cl = (CutList)event.getObject();
		
		return cl;
	}
	
	public boolean isShowAuthor() {return showAuthor;}
	public void setShowAuthor(boolean showAuthor) {this.showAuthor = showAuthor;}

	public boolean isShowComment() {return showComment;}
	public void setShowComment(boolean showComment) {this.showComment = showComment;}

	public boolean isShowFile() {return showFile;}
	public void setShowFile(boolean showFile) {this.showFile = showFile;}
	
	public boolean isShowRanking() {return showRanking;}
	public void setShowRanking(boolean showRanking) {this.showRanking = showRanking;}
}