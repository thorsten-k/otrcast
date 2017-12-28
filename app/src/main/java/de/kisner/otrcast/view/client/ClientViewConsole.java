package de.kisner.otrcast.view.client;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import de.kisner.otrcast.factory.txt.TxtFileNameFactoy;
import de.kisner.otrcast.interfaces.view.client.ViewClient;
import de.kisner.otrcast.model.xml.cut.CutList;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.cut.VideoFiles;
import net.sf.exlp.util.xml.JaxbUtil;

public class ClientViewConsole implements ViewClient
{
	final static Logger logger = LoggerFactory.getLogger(ClientViewConsole.class);

	@Override
	public void readFilesInDir(File srcDir)
	{
		logger.debug("Searching files in "+srcDir.getAbsolutePath());
	}

	@Override
	public void found(int i)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(i);
		sb.append(" ").append("file");
		if(i!=1){sb.append("s");}
		sb.append(" found.");
		logger.info(sb.toString());
	}

	@Override
	public void found(VideoFiles files) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void welcome(VideoFiles vFiles)
	{
		logger.info("");
		logger.info("Choose cutlist for "+vFiles.getVideoFile().size()+" files");
		logger.info("\tYou can select a single cutlist e.g. with '1'");
		logger.info("\tMultiple selections (e.g. '1,3') result in multiple output files");
		logger.info("\tA second file can be added with a +, e.g. '1+'");
		logger.info("\tIgnore the file by pressing ENTER");
	}

	@Override
	public void showFileInfo(int index, VideoFile vFile)
	{
		logger.info("");
		logger.info(TxtFileNameFactoy.build(vFile.getOtrId()));
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

	@Override
	public void additionalFile(VideoFiles vFiles)
	{
		logger.info("Select the second file!");
		for(int i=1;i<=vFiles.getVideoFile().size();i++)
		{
			VideoFile vFile = vFiles.getVideoFile().get(i-1);
			logger.info("\t"+i+" "+TxtFileNameFactoy.build(vFile.getOtrId()));
			for(int j=1;j<=vFile.getCutLists().getCutList().size();j++)
			{
				CutList cl = vFile.getCutLists().getCutList().get(j-1);
				logger.info("\t\t"+j+" "+cl.getAuthor().getValue());
				logger.info("\t\t  "+cl.getComment().getValue());
				logger.info("\t\t  "+cl.getFileName().getValue());
				
			}
		}
	}

	@Override
	public void cutlistsFound(VideoFiles vFiles) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cutlistsLoaded(VideoFiles vFiles) {
		// TODO Auto-generated method stub
		
	}

}