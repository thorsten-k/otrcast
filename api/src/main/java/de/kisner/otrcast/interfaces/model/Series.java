package de.kisner.otrcast.interfaces.model;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface Series<SERIES extends Series<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,SEASON extends Season<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,EPISODE extends Episode<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,IMAGE extends Image,STORAGE extends Storage>
		extends Serializable,EjbWithId,
					EjbPersistable,
					EjbWithCode,EjbWithName
{
	List<SEASON> getSeasons();
	void setSeasons(List<SEASON> seasons);
	
	IMAGE getBanner();
	void setBanner(IMAGE image);
}