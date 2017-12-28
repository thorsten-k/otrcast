package de.kisner.otrcast.interfaces.view.client;

import java.io.File;

import de.kisner.otrcast.model.xml.cut.VideoFiles;

public interface ViewSrcDirProcessor
{
	void readFilesInDir(File srcDir);
	void found(int i);
	void found(VideoFiles files);
}
