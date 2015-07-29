package de.kisner.otrcast.controller;

import de.kisner.otrcast.interfaces.view.ViewCutlistChooser;

public class AbstractCutlistChooserController
{
	protected ViewCutlistChooser view;
	
	public AbstractCutlistChooserController(ViewCutlistChooser view)
	{
		this.view=view;
	}
}
