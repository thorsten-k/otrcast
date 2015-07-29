
package de.kisner.otrcast.model.xml.rss;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import de.kisner.otrcast.model.xml.itunes.Image;
import de.kisner.otrcast.model.xml.itunes.Summary;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://otrcutmp4.sf.net/rss}title"/>
 *         &lt;element ref="{http://otrcutmp4.sf.net/rss}description"/>
 *         &lt;element ref="{http://www.itunes.com/dtds/podcast-1.0.dtd}summary"/>
 *         &lt;element ref="{http://www.itunes.com/dtds/podcast-1.0.dtd}image"/>
 *         &lt;element ref="{http://otrcutmp4.sf.net/rss}pubDate"/>
 *         &lt;element ref="{http://otrcutmp4.sf.net/rss}enclosure"/>
 *         &lt;element ref="{http://otrcutmp4.sf.net/rss}guid"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "title",
    "description",
    "summary",
    "image",
    "pubDate",
    "enclosure",
    "guid"
})
@XmlRootElement(name = "item")
public class Item
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected Title title;
    @XmlElement(required = true)
    protected Description description;
    @XmlElement(namespace = "http://www.itunes.com/dtds/podcast-1.0.dtd", required = true)
    protected Summary summary;
    @XmlElement(namespace = "http://www.itunes.com/dtds/podcast-1.0.dtd", required = true)
    protected Image image;
    @XmlElement(required = true)
    protected PubDate pubDate;
    @XmlElement(required = true)
    protected Enclosure enclosure;
    @XmlElement(required = true)
    protected Guid guid;

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link Title }
     *     
     */
    public Title getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link Title }
     *     
     */
    public void setTitle(Title value) {
        this.title = value;
    }

    public boolean isSetTitle() {
        return (this.title!= null);
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link Description }
     *     
     */
    public Description getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link Description }
     *     
     */
    public void setDescription(Description value) {
        this.description = value;
    }

    public boolean isSetDescription() {
        return (this.description!= null);
    }

    /**
     * Gets the value of the summary property.
     * 
     * @return
     *     possible object is
     *     {@link Summary }
     *     
     */
    public Summary getSummary() {
        return summary;
    }

    /**
     * Sets the value of the summary property.
     * 
     * @param value
     *     allowed object is
     *     {@link Summary }
     *     
     */
    public void setSummary(Summary value) {
        this.summary = value;
    }

    public boolean isSetSummary() {
        return (this.summary!= null);
    }

    /**
     * Gets the value of the image property.
     * 
     * @return
     *     possible object is
     *     {@link Image }
     *     
     */
    public Image getImage() {
        return image;
    }

    /**
     * Sets the value of the image property.
     * 
     * @param value
     *     allowed object is
     *     {@link Image }
     *     
     */
    public void setImage(Image value) {
        this.image = value;
    }

    public boolean isSetImage() {
        return (this.image!= null);
    }

    /**
     * Gets the value of the pubDate property.
     * 
     * @return
     *     possible object is
     *     {@link PubDate }
     *     
     */
    public PubDate getPubDate() {
        return pubDate;
    }

    /**
     * Sets the value of the pubDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link PubDate }
     *     
     */
    public void setPubDate(PubDate value) {
        this.pubDate = value;
    }

    public boolean isSetPubDate() {
        return (this.pubDate!= null);
    }

    /**
     * Gets the value of the enclosure property.
     * 
     * @return
     *     possible object is
     *     {@link Enclosure }
     *     
     */
    public Enclosure getEnclosure() {
        return enclosure;
    }

    /**
     * Sets the value of the enclosure property.
     * 
     * @param value
     *     allowed object is
     *     {@link Enclosure }
     *     
     */
    public void setEnclosure(Enclosure value) {
        this.enclosure = value;
    }

    public boolean isSetEnclosure() {
        return (this.enclosure!= null);
    }

    /**
     * Gets the value of the guid property.
     * 
     * @return
     *     possible object is
     *     {@link Guid }
     *     
     */
    public Guid getGuid() {
        return guid;
    }

    /**
     * Sets the value of the guid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Guid }
     *     
     */
    public void setGuid(Guid value) {
        this.guid = value;
    }

    public boolean isSetGuid() {
        return (this.guid!= null);
    }

}
