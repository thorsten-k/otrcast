package net.sf.otrcutmp4.model.interfaces;

import java.util.List;

public interface Series<SERIES extends Series<SERIES,SEASON,EPISODE>, SEASON extends Season<SERIES,SEASON,EPISODE>,EPISODE extends Episode<SERIES,SEASON,EPISODE>>
{
	String getName();
	public void setName(String name);
}