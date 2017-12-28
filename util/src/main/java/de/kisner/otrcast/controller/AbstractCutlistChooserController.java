package de.kisner.otrcast.controller;

import de.kisner.otrcast.interfaces.view.client.ViewCutlistChooser;

public class AbstractCutlistChooserController
{
	protected ViewCutlistChooser view;
	
	public AbstractCutlistChooserController(ViewCutlistChooser view)
	{
		this.view=view;
	}
}
