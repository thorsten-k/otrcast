package net.sf.otrcutmp4.controller;

import net.sf.otrcutmp4.interfaces.view.ViewCutlistChooser;

public class AbstractCutlistChooserController
{
	protected ViewCutlistChooser view;
	
	public AbstractCutlistChooserController(ViewCutlistChooser view)
	{
		this.view=view;
	}
}
