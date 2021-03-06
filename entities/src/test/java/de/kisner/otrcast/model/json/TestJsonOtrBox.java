package de.kisner.otrcast.model.json;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.kisner.otrcast.test.AbstractOtrJsonTest;
import de.kisner.otrcast.test.OtrXmlTestBootstrap;

public class TestJsonOtrBox extends AbstractOtrJsonTest<JsonOtrBox>
{
	final static Logger logger = LoggerFactory.getLogger(TestJsonOtrBox.class);
	
//    @Test
    public void test() throws JsonGenerationException, JsonMappingException, IOException
    {
    	JsonOtrBox json = create(true);
    	jom.writeValue(System.out, json);
    }
    
    public static JsonOtrBox create(boolean withChildren)
    {
    	JsonOtrBox json = new JsonOtrBox();
    	
    	if(withChildren)
    	{
    		json.setIdentifier(new ArrayList<JsonOtrIdentifier>());
    		json.getIdentifier().add(TestJsonOtrIdentifier.create(false));
    		json.getIdentifier().add(TestJsonOtrIdentifier.create(false));
    	}
    	return json;
    }
  
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException
    {
		OtrXmlTestBootstrap.init();
		TestJsonOtrBox test = new TestJsonOtrBox();
		TestJsonOtrBox.initJson();
		test.test();
    }
}