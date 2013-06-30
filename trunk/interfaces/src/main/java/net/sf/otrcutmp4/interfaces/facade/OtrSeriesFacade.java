package net.sf.otrcutmp4.interfaces.facade;

import net.sf.ahtutils.controller.interfaces.UtilsFacade;
import net.sf.otrcutmp4.interfaces.model.Season;

public interface OtrSeriesFacade extends UtilsFacade
{	
	<T extends Season> T load(Class<T> type, T season);
}