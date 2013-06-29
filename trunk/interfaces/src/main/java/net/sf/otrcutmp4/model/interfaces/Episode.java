package net.sf.otrcutmp4.model.interfaces;

public interface Episode<SERIES extends Series<SERIES,SEASON,EPISODE>, SEASON extends Season<SERIES,SEASON,EPISODE>,EPISODE extends Episode<SERIES,SEASON,EPISODE>>
{
	String getName();
	public void setName(String name);
	
	int getNr();
	void setNr(int nr);
	
	SEASON getSeason();
	void setSeason(SEASON season);
}