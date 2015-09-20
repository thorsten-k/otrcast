package de.kisner.otrcast.controller.tag.reader;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.UnknownBox;
import com.coremedia.iso.boxes.apple.AppleCoverBox;
import com.coremedia.iso.boxes.apple.AppleDataBox;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.kisner.otrcast.interfaces.Version;
import de.kisner.otrcast.model.json.JsonOtrBox;
import de.kisner.otrcast.model.json.JsonOtrIdentifier;
import de.kisner.otrcast.model.xml.mc.Image;

public class AbstractTagReader
{
	final static Logger logger = LoggerFactory.getLogger(AbstractTagReader.class);	

	private ObjectMapper jom;
	
	public AbstractTagReader()
	{
		jom = new ObjectMapper();
	}
	
	protected Image getCover(AppleCoverBox box) throws NoSuchFieldException
	{
		if(box.getBoxes(AppleDataBox.class).size()==0){throw new NoSuchFieldException();}	
		AppleDataBox adb = box.getBoxes(AppleDataBox.class).get(0);
		
		Image cover = new Image();
		cover.setData(adb.getData());
		
		//see source of AppleCoverBox.java
		if(adb.getFlags()==0xe){cover.setFileType("png");}
		else if(adb.getFlags()==0xd){cover.setFileType("jpg");}
		else {logger.warn("Unknown flag for cover "+adb.getFlags());}
		
		return cover;
	}
	
	protected JsonOtrIdentifier readOtrIdentifier(MovieBox moov) throws NoSuchFieldException
	{
		for(Box box : moov.getBoxes())
		{
			if(box.getType().equals(Version.OtrCastMp4Box))
			{
				UnknownBox ub = (UnknownBox)box;
				ByteBuffer bb = ub.getData().duplicate();
				bb.flip();
				byte[] bytes = new byte[bb.remaining()];
				bb.get(bytes);
				try
				{
					JsonOtrBox otrBox = jom.readValue(bytes, JsonOtrBox.class);
					for(JsonOtrIdentifier id : otrBox.getIdentifier())
					{
						if(id.getScheme().equals(Version.OtrCastMp4BoxScheme))
						{
							return id;
						}
					}
				}
				catch (JsonParseException e) {throw new NoSuchFieldException(e.getMessage());}
				catch (JsonMappingException e) {throw new NoSuchFieldException(e.getMessage());}
				catch (IOException e) {throw new NoSuchFieldException(e.getMessage());}
			}
		}
		throw new NoSuchFieldException();
	}
}