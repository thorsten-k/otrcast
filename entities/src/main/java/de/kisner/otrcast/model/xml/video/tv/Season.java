
package de.kisner.otrcast.model.xml.video.tv;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import de.kisner.otrcast.model.xml.mc.Image;
import de.kisner.otrcast.model.xml.tvdb.Banners;
import de.kisner.otrcast.model.xml.tvdb.Sync;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://otrcast.kisner.de/mc}image"/&gt;
 *         &lt;element ref="{http://otrcast.kisner.de/tv}series"/&gt;
 *         &lt;element ref="{http://otrcast.kisner.de/tv}episode" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://otrcutmp4.sf.net/tvdb}sync"/&gt;
 *         &lt;element ref="{http://otrcutmp4.sf.net/tvdb}banners"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *       &lt;attribute name="nr" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="showNr" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="showName" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "image",
    "series",
    "episode",
    "sync",
    "banners"
})
@XmlRootElement(name = "season")
public class Season
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "http://otrcast.kisner.de/mc", required = true)
    protected Image image;
    @XmlElement(required = true)
    protected Series series;
    @XmlElement(required = true)
    protected List<Episode> episode;
    @XmlElement(namespace = "http://otrcutmp4.sf.net/tvdb", required = true)
    protected Sync sync;
    @XmlElement(namespace = "http://otrcutmp4.sf.net/tvdb", required = true)
    protected Banners banners;
    @XmlAttribute(name = "id")
    protected Long id;
    @XmlAttribute(name = "nr")
    protected Long nr;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "showNr")
    protected Boolean showNr;
    @XmlAttribute(name = "showName")
    protected Boolean showName;

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
     * Gets the value of the series property.
     * 
     * @return
     *     possible object is
     *     {@link Series }
     *     
     */
    public Series getSeries() {
        return series;
    }

    /**
     * Sets the value of the series property.
     * 
     * @param value
     *     allowed object is
     *     {@link Series }
     *     
     */
    public void setSeries(Series value) {
        this.series = value;
    }

    public boolean isSetSeries() {
        return (this.series!= null);
    }

    /**
     * Gets the value of the episode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the episode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEpisode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Episode }
     * 
     * 
     */
    public List<Episode> getEpisode() {
        if (episode == null) {
            episode = new ArrayList<Episode>();
        }
        return this.episode;
    }

    public boolean isSetEpisode() {
        return ((this.episode!= null)&&(!this.episode.isEmpty()));
    }

    public void unsetEpisode() {
        this.episode = null;
    }

    /**
     * Gets the value of the sync property.
     * 
     * @return
     *     possible object is
     *     {@link Sync }
     *     
     */
    public Sync getSync() {
        return sync;
    }

    /**
     * Sets the value of the sync property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sync }
     *     
     */
    public void setSync(Sync value) {
        this.sync = value;
    }

    public boolean isSetSync() {
        return (this.sync!= null);
    }

    /**
     * Gets the value of the banners property.
     * 
     * @return
     *     possible object is
     *     {@link Banners }
     *     
     */
    public Banners getBanners() {
        return banners;
    }

    /**
     * Sets the value of the banners property.
     * 
     * @param value
     *     allowed object is
     *     {@link Banners }
     *     
     */
    public void setBanners(Banners value) {
        this.banners = value;
    }

    public boolean isSetBanners() {
        return (this.banners!= null);
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(long value) {
        this.id = value;
    }

    public boolean isSetId() {
        return (this.id!= null);
    }

    public void unsetId() {
        this.id = null;
    }

    /**
     * Gets the value of the nr property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getNr() {
        return nr;
    }

    /**
     * Sets the value of the nr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNr(long value) {
        this.nr = value;
    }

    public boolean isSetNr() {
        return (this.nr!= null);
    }

    public void unsetNr() {
        this.nr = null;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    public boolean isSetName() {
        return (this.name!= null);
    }

    /**
     * Gets the value of the showNr property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isShowNr() {
        return showNr;
    }

    /**
     * Sets the value of the showNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setShowNr(boolean value) {
        this.showNr = value;
    }

    public boolean isSetShowNr() {
        return (this.showNr!= null);
    }

    public void unsetShowNr() {
        this.showNr = null;
    }

    /**
     * Gets the value of the showName property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isShowName() {
        return showName;
    }

    /**
     * Sets the value of the showName property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setShowName(boolean value) {
        this.showName = value;
    }

    public boolean isSetShowName() {
        return (this.showName!= null);
    }

    public void unsetShowName() {
        this.showName = null;
    }

}
