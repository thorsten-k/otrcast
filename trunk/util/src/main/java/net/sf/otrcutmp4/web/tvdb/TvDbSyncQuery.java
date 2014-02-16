package net.sf.otrcutmp4.web.tvdb;

import java.util.Date;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TvDbSyncQuery extends AbstractTvDbQuery
{
	final static Logger logger = LoggerFactory.getLogger(TvDbSyncQuery.class);

	public TvDbSyncQuery(String apiKey)
	{
        super(apiKey);
	}
	
	public Date debug()
	{
        Document doc = fetch(url+"/Updates.php?type=none");

		XMLOutputter serializer= new XMLOutputter(Format.getPrettyFormat());
		logger.info(serializer.outputString(doc));

        XPathFactory xPathFactory = XPathFactory.instance();
        XPathExpression<Element> xPathExpression = xPathFactory.compile("/Items/Time", Filters.element());
        Element e = xPathExpression.evaluateFirst(doc);
        logger.info(e.getValue());

        Long secs = new Long(e.getValue());

        Date date = new Date(secs*1000);
        logger.info("Date "+date);
        return date;
	}
}