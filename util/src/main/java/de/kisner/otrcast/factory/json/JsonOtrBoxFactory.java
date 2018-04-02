package de.kisner.otrcast.factory.json;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.controller.tag.util.Mp4BoxManager;
import de.kisner.otrcast.model.json.JsonOtrBox;
import de.kisner.otrcast.model.json.JsonOtrIdentifier;
import de.kisner.otrcast.model.xml.video.tv.Episode;

public class JsonOtrBoxFactory
{
	final static Logger logger = LoggerFactory.getLogger(JsonOtrBoxFactory.class);
	
	public static JsonOtrBox build(Episode episode)
	{
		if(!episode.isSetId()){throw new UnsupportedOperationException("id");}
		
		JsonOtrBox json = new JsonOtrBox();
		json.setIdentifier(new ArrayList<JsonOtrIdentifier>());
		
		JsonOtrIdentifier id = new JsonOtrIdentifier();
		id.setScheme("otrcast");
		id.setType(Mp4BoxManager.Type.SERIES.toString().toLowerCase());
		id.setId(episode.getId());
		json.getIdentifier().add(id);

		return json;
	}
}
