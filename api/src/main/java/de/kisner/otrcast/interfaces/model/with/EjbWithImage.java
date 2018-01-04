package de.kisner.otrcast.interfaces.model.with;

import de.kisner.otrcast.interfaces.model.Image;

public interface EjbWithImage <IMAGE extends Image>
{	
	IMAGE getBanner();
	void setBanner(IMAGE image);
}