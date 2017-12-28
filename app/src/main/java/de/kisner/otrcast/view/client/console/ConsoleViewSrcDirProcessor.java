package de.kisner.otrcast.view.client.console;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.view.client.ViewSrcDirProcessor;
import de.kisner.otrcast.model.xml.cut.VideoFiles;

public class ConsoleViewSrcDirProcessor implements ViewSrcDirProcessor
{
	final static Logger logger = LoggerFactory.getLogger(ConsoleViewSrcDirProcessor.class);
	
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

}
