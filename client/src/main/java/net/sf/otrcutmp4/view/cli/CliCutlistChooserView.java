package net.sf.otrcutmp4.view.cli;

import net.sf.otrcutmp4.interfaces.view.ViewCutlistChooser;
import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliCutlistChooserView implements ViewCutlistChooser
{
	final static Logger logger = LoggerFactory.getLogger(CliCutlistChooserView.class);

	@Override
	public void welcome(VideoFiles vFiles)
	{
		logger.info("");
		logger.info("Choose cutlist for "+vFiles.getVideoFile().size()+" files");
		logger.info("\tYou can select a single cutlist e.g. with '1'");
		logger.info("\tMultiple selections (e.g. '1,3') result in multiple output files");
		logger.info("\tIgnore the file by pressing ENTER");
	}

	@Override
	public void showFileInfo(int index, VideoFile vFile)
	{
		logger.info("");
		logger.info(vFile.getOtrId().getKey()+"."+vFile.getOtrId().getFormat().getType());
	}
	
	public void showCutlistInfo(int i, CutList cl, boolean showAuthor, boolean showRanking, boolean showComment, boolean showFile)
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

	@Override
	public void srcFolderProcessed(String s)
	{
		logger.info("Src Folder processed");
	}

	@Override
	public void cutlistsSelected()
	{
		logger.info("Selection finished");
	}

}
