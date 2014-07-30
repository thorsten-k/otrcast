package net.sf.otrcutmp4.web.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
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
    protected String url;
    
    protected String httpProtocol;
    protected String httpHost;
    protected String httpContext;
    
    public AbstractHttpXmlQuery(String httpProtocol, String httpHost, String httpContext)
	{
		this.httpProtocol=httpProtocol;
		this.httpHost=httpHost;
		this.httpContext=httpContext;
        debugPlainResponses=false;
	}
    
    @Deprecated
	public AbstractHttpXmlQuery(final String url)
	{
		this.url=url;
        debugPlainResponses=false;
	}

	protected Document fetch(URL queryUrl) throws UtilsProcessingException{return fetch(queryUrl.toExternalForm());}
    protected Document fetch(String queryUrl) throws UtilsProcessingException
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
                checkHttpErrors(result.getStatusLine());                
            }
            catch (ClientProtocolException e) {e.printStackTrace();}
            catch (IOException e) {e.printStackTrace();}

            logger.trace("Response status code: " + result.getStatusLine());
            
        }
        finally
        {
        }

        Document doc = null;
        
        try
        {

			StringWriter writer = new StringWriter();
	    	IOUtils.copy(result.getEntity().getContent(), writer, "UTF-8");
	    	String response = writer.toString();
	    	logger.trace(writer.toString());
	    	
	    	StringReader reader = new StringReader(response);
       
        	SAXBuilder builder = new SAXBuilder();
            doc = builder.build(reader);
        }
        catch (IllegalStateException e) {e.printStackTrace();}
        catch (JDOMException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
        return doc;
    }
    
	public boolean isDebugPlainResponses() {return debugPlainResponses;}
	public void setDebugPlainResponses(boolean debugPlainResponses) {this.debugPlainResponses = debugPlainResponses;}
	
	private void checkHttpErrors(StatusLine line) throws UtilsProcessingException
	{
        int code = line.getStatusCode();
        
        if(code == HttpStatus.SC_SERVICE_UNAVAILABLE)
        {
        	throw new UtilsProcessingException("HTTP Service not availabe. "+HttpStatus.SC_SERVICE_UNAVAILABLE);
        }
	}
}