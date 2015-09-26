package de.kisner.otrcast.interfaces.model;

import java.util.List;

import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface Series<SERIES extends Series<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,SEASON extends Season<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,EPISODE extends Episode<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,IMAGE extends Image,STORAGE extends Storage>
		extends EjbWithId,EjbWithName,EjbWithCode
{
	List<SEASON> getSeasons();
	void setSeasons(List<SEASON> seasons);
	
	IMAGE getBanner();
	void setBanner(IMAGE image);
}