package net.sf.otrcutmp4.web.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractHttpXmlQuery
{
	final static Logger logger = LoggerFactory.getLogger(AbstractHttpXmlQuery.class);

    protected boolean debugPlainResponses;
    protected final String url;
    
    protected String httpProtocol;
    protected String httpHost;
    protected String httpContext;
    
	public AbstractHttpXmlQuery(final String url)
	{
		this.url=url;
        debugPlainResponses=false;
	}

	protected Document fetch(URL queryUrl){return fetch(queryUrl.toExternalForm());}
    protected Document fetch(String queryUrl)
    {
    	logger.info(queryUrl);
        HttpGet get = new HttpGet(queryUrl);
        //        get.setHeader("Content-type", "text/xml; charset=UTF-8");

        HttpResponse result = null;

        HttpClient httpclient = new DefaultHttpClient();
        try
        {
            try
            {
                result = httpclient.execute(get);
            }
            catch (ClientProtocolException e) {e.printStackTrace();}
            catch (IOException e) {e.printStackTrace();}

            logger.debug("Response status code: " + result.getStatusLine());
            
        }
        finally
        {
        }

        Document doc = null;
        
        try
        {
//        	StringWriter writer = new StringWriter();
//        	IOUtils.copy(result.getEntity().getContent(), writer, "UTF-8");
//        	logger.info(writer.toString());
        	SAXBuilder builder = new SAXBuilder();
            doc = builder.build(new InputStreamReader(result.getEntity().getContent()));
        }
        catch (IllegalStateException e) {e.printStackTrace();}
        catch (JDOMException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
        return doc;
    }
    
	public boolean isDebugPlainResponses() {return debugPlainResponses;}
	public void setDebugPlainResponses(boolean debugPlainResponses) {this.debugPlainResponses = debugPlainResponses;}
}