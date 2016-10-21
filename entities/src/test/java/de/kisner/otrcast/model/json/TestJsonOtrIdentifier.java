package de.kisner.otrcast.model.json;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.kisner.otrcast.test.AbstractOtrJsonTest;
import de.kisner.otrcast.test.OtrXmlTstBootstrap;

public class TestJsonOtrIdentifier extends AbstractOtrJsonTest<JsonOtrIdentifier>
{
	final static Logger logger = LoggerFactory.getLogger(TestJsonOtrIdentifier.class);
	
//    @Test
    public void test() throws JsonGenerationException, JsonMappingException, IOException
    {
    	JsonOtrIdentifier json = create(true);
    	
    	jom.writeValue(System.out, json);
    }
    
    public static JsonOtrIdentifier create(boolean withChildren)
    {
    	JsonOtrIdentifier json = new JsonOtrIdentifier();
    	json.setScheme("myScheme");
    	json.setType("myType");
    	json.setId(123);
    	
    	if(withChildren)
    	{
    		json.setMovie(TestJsonOtrMovie.create(false));
    	}
    	return json;
    }
  
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException
    {
		OtrXmlTstBootstrap.init();
			
		TestJsonOtrIdentifier test = new TestJsonOtrIdentifier();
		TestJsonOtrIdentifier.initJson();
		test.test();
    }
}