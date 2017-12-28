package de.kisner.otrcast.view.noop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.interfaces.view.client.ViewCutlistLoader;
import de.kisner.otrcast.model.xml.cut.VideoFiles;

public class NoopCutlistLoaderView implements ViewCutlistLoader
{
	final static Logger logger = LoggerFactory.getLogger(NoopCutlistLoaderView.class);

	@Override
	public void cutlistsLoaded(VideoFiles vFiles) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cutlistsFound(VideoFiles vFiles) {
		// TODO Auto-generated method stub
		
	}
	


}
