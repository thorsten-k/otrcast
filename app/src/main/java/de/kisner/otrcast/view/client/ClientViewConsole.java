package de.kisner.otrcast.view.client;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.view.client.ViewClient;
import de.kisner.otrcast.interfaces.view.client.ViewCutlistChooser;
import de.kisner.otrcast.interfaces.view.client.ViewSrcDirProcessor;
import de.kisner.otrcast.model.xml.cut.CutList;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.cut.VideoFiles;
import de.kisner.otrcast.view.client.console.ConsoleViewCutlistChooser;
import de.kisner.otrcast.view.client.console.ConsoleViewSrcDirProcessor;

public class ClientViewConsole implements ViewClient
{
	final static Logger logger = LoggerFactory.getLogger(ClientViewConsole.class);

	private final ViewSrcDirProcessor viewSrsDir;
	private final ViewCutlistChooser viewCutlistChooser;
	
	public ClientViewConsole()
	{
		viewSrsDir = new ConsoleViewSrcDirProcessor();
		viewCutlistChooser = new ConsoleViewCutlistChooser();
	}
	
	// ViewSrcDirProcessor
	@Override public void readFilesInDir(File srcDir){viewSrsDir.readFilesInDir(srcDir);}
	@Override public void found(int i){viewSrsDir.found(i);}
	

	//
	@Override
	public void found(VideoFiles files) {
		// TODO Auto-generated method stub
		
	}
	
	// ViewCutlistChooser
	@Override public void welcome(VideoFiles vFiles) {viewCutlistChooser.welcome(vFiles);}
	@Override public void showFileInfo(int index, VideoFile vFile){viewCutlistChooser.showFileInfo(index, vFile);}
	@Override public void showCutlistInfo(int i, CutList cl, boolean showAuthor, boolean showRanking, boolean showComment, boolean showFile) {viewCutlistChooser.showCutlistInfo(i, cl, showAuthor, showRanking, showComment, showFile);}
	@Override public void srcFolderProcessed(String s) {viewCutlistChooser.srcFolderProcessed(s);}
	@Override public void cutlistsSelected() {viewCutlistChooser.cutlistsSelected();}
	@Override public void additionalFile(VideoFiles vFiles) {viewCutlistChooser.additionalFile(vFiles);}
	



	@Override
	public void cutlistsFound(VideoFiles vFiles) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cutlistsLoaded(VideoFiles vFiles) {
		// TODO Auto-generated method stub
		
	}

}