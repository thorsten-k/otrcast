package net.sf.otrcutmp4.cutlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.sf.exlp.event.handler.EhResultContainer;
import net.sf.exlp.event.impl.JaxbEvent;
import net.sf.exlp.listener.LogListener;
import net.sf.exlp.listener.impl.LogListenerHttp;
import net.sf.exlp.parser.LogParser;
import net.sf.otrcutmp4.exlp.parser.CutlistParser;
import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.cut.CutListsAvailable;
import net.sf.otrcutmp4.model.xml.cut.CutListsSelected;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class CutlistChooser
{
	static Log logger = LogFactory.getLog(CutlistChooser.class);
	
	private boolean showAuthor, showRanking, showComment, showFile;

	public CutlistChooser()
	{
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
		logger.info("");
		logger.info("Choose cutlist for "+vFiles.getVideoFile().size()+" files");
		logger.info("\tYou can select a single cutlist e.g. with '1'");
		logger.info("\tMultiple selections (e.g. '1,3') result in multiple output files");
		logger.info("\tIgnore the file by pressing ENTER");
		for(VideoFile vf : vFiles.getVideoFile())
		{
			if(vf.isSetCutListsAvailable() && vf.getCutListsAvailable().isSetCutList())
			{
				chooseCutlist(vf,true);
			}
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
				chooseCutlist(vf,false);
			}
		}
		return vFiles;
	}
	
	private void chooseCutlist(VideoFile vf,boolean loadCutlist)
	{
		logger.info("");
		logger.info(vf.getOtrId().getValue());
		for(int i=0;i<vf.getCutListsAvailable().getCutList().size();i++)
		{
			CutList cl = vf.getCutListsAvailable().getCutList().get(i);
			askCutlist(i, vf, cl);
		}
		
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		String[] tokens = line.split( "," );
		
		if(line.length()>0)
		{
			List<Integer> selectedIndexes = new ArrayList<Integer>();
			for(String token : tokens)
			{
				selectedIndexes.add(Integer.parseInt(token));
			}	
			
			if(loadCutlist)
			{
				vf.setCutListsSelected(loadCutlists(selectedIndexes, vf.getCutListsAvailable()));
			}
			else
			{
				if(selectedIndexes.size()==1)
				{
					CutListsSelected clSelected = new CutListsSelected();
					clSelected.getCutList().add(vf.getCutListsAvailable().getCutList().get(selectedIndexes.get(0)));
					vf.setCutListsSelected(clSelected);
				}
				else
				{
					logger.warn("You have to chosse only one cutlist");
				}
			}
		}
	}
	
	private void askCutlist(int i, VideoFile vf, CutList cl)
	{
		boolean printedNumber=false;
		if(showAuthor)
		{
			StringBuffer sb = new StringBuffer();
			sb.append(i);printedNumber=true;
			sb.append("\tAuthor: ").append(cl.getAuthor().getValue());
			logger.info(sb.toString());
		}
		
		if(showRanking)
		{
			StringBuffer sb = new StringBuffer();
			if(!printedNumber){sb.append(i);printedNumber=true;}
			sb.append("\tRating: ");
			if(cl.isSetRating()){sb.append(cl.getRating());}
			else {sb.append("n/a");}
			sb.append(" (rated ").append(cl.getRatingCount()).append(" times)");
			logger.info(sb.toString());
		}
		
		if(showComment && cl.isSetComment())
		{
			StringBuffer sb = new StringBuffer();
			if(!printedNumber){sb.append(i);printedNumber=true;}
			sb.append("\tComment: "+cl.getComment().getValue());
			logger.info(sb.toString());
		}
		
		if(showFile && cl.isSetFileName())
		{
			StringBuffer sb = new StringBuffer();
			if(!printedNumber){sb.append(i);printedNumber=true;}
			sb.append("\tFile: "+cl.getFileName().getValue());
			logger.info(sb.toString());
		}
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
		
//		EhResultContainer leh = new EhResultContainer();
//		LogParser lp = new XmlParser(leh);
	
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