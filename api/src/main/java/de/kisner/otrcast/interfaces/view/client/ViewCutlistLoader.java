package de.kisner.otrcast.interfaces.view.client;

import de.kisner.otrcast.model.xml.cut.VideoFiles;

public interface ViewCutlistLoader
{
	void cutlistsFound(VideoFiles vFiles);
	void cutlistsLoaded(VideoFiles vFiles);
}
