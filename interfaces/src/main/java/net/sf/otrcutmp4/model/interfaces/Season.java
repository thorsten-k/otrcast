package net.sf.otrcutmp4.model.interfaces;

import java.util.List;

public interface Season<SERIES extends Series<SERIES,SEASON,EPISODE>, SEASON extends Season<SERIES,SEASON,EPISODE>,EPISODE extends Episode<SERIES,SEASON,EPISODE>>
{
	String getName();
	public void setName(String name);
	
	int getNr();
	void setNr(int nr);
	
	List<EPISODE> getEpisodes();
	void setEpisodes(List<EPISODE> episodes);
}