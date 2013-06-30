package net.sf.otrcutmp4.interfaces.model;

import java.util.List;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface Series<SERIES extends Series<SERIES,SEASON,EPISODE>, SEASON extends Season<SERIES,SEASON,EPISODE>,EPISODE extends Episode<SERIES,SEASON,EPISODE>>
		extends EjbWithId,EjbWithName
{
	List<SEASON> getSeasons();
	void setSeasons(List<SEASON> seasons);
}