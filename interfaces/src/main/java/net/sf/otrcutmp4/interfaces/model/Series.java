package net.sf.otrcutmp4.interfaces.model;

import java.util.List;

import net.sf.ahtutils.model.interfaces.with.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface Series<SERIES extends Series<SERIES,SEASON,EPISODE,COVER,STORAGE>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER,STORAGE>,COVER extends Image,STORAGE extends Storage>
		extends EjbWithId,EjbWithName,EjbWithCode
{
	List<SEASON> getSeasons();
	void setSeasons(List<SEASON> seasons);
}