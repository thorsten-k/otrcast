package net.sf.otrcutmp4.web.tvdb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.otrcutmp4.factory.xml.tvdb.XmlBannerFactory;
import net.sf.otrcutmp4.factory.xml.tvdb.XmlMetaFactory;
import net.sf.otrcutmp4.model.xml.container.Otr;
import net.sf.otrcutmp4.model.xml.series.Series;
import net.sf.otrcutmp4.model.xml.tvdb.Banners;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TvDbSeriesQuery extends AbstractTvDbQuery
{
	final static Logger logger = LoggerFactory.getLogger(TvDbSeriesQuery.class);

	public TvDbSeriesQuery(String apiKey)
	{
        super(apiKey);
	}
	
	public Otr findSeries(String name)
	{
        Otr otr = new Otr();

        Document doc = fetch(url+"/GetSeries.php?seriesname="+name);

        XMLOutputter serializer= new XMLOutputter(Format.getPrettyFormat());
//        logger.info(serializer.outputString(doc));

        XPathFactory xPathFactory = XPathFactory.instance();
        XPathExpression<Element> xPathExpression = xPathFactory.compile("/Data/Series", Filters.element());
        for(Element e : xPathExpression.evaluate(doc))
        {
            Series series = new Series();

            series.setName(e.getChildText("SeriesName"));
            series.setMeta(XmlMetaFactory.build(e.getChildText("id")));

            if(e.getChildText("banner")!=null)
            {
                series.getMeta().setBanners(new Banners());
                series.getMeta().getBanners().getBanner().add(XmlBannerFactory.build(e.getChildText("banner")));
            }

            otr.getSeries().add(series);
        }
        JaxbUtil.info(otr);
        return otr;
	}

    public void querySeries(long tvDbSeriesId) throws JDOMException
    {
        HttpGet get = new HttpGet(url+"/"+apiKey+"/series/"+tvDbSeriesId+"/all/de.zip");
        HttpResponse result = null;

        HttpClient httpclient = new DefaultHttpClient();
        try
        {
            result = httpclient.execute(get);
            logger.debug("Response status code: " + result.getStatusLine());
        }
        catch (ClientProtocolException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}

        try
        {
            InputStream is = result.getEntity().getContent();
            ZipInputStream zis = new ZipInputStream(is);
            ZipEntry ze;
            byte[] buff = new byte[1024];
            while((ze=zis.getNextEntry())!=null)
            {

                logger.info(ze.getName());
                try
                {
                    ByteArrayOutputStream output = new ByteArrayOutputStream();

                    int data = 0;
                    while( ( data = zis.read() ) != - 1 )
                    {
                        output.write( data );
                    }

                    // The ZipEntry is extracted in the output
                    output.close();

                    Document doc = null;
                    SAXBuilder builder = new SAXBuilder();

                    Reader reader = new InputStreamReader(new ByteArrayInputStream(output.toByteArray()));
                    doc = builder.build(reader);
                    XMLOutputter serializer= new XMLOutputter(Format.getPrettyFormat());
                    logger.info(serializer.outputString(doc));

                }
                catch (IllegalStateException e) {e.printStackTrace();}
                catch (IOException e) {e.printStackTrace();}

            }
            zis.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}