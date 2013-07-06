
package net.sf.otrcutmp4.model.xml.series;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import net.sf.otrcutmp4.model.xml.mc.Cover;


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
 *         &lt;element ref="{http://otrcutmp4.sf.net/mc}cover"/>
 *         &lt;element ref="{http://otrcutmp4.sf.net/series}series"/>
 *         &lt;element ref="{http://otrcutmp4.sf.net/series}episode" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="nr" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="showNr" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="showName" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cover",
    "series",
    "episode"
})
@XmlRootElement(name = "season")
public class Season
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "http://otrcutmp4.sf.net/mc", required = true)
    protected Cover cover;
    @XmlElement(required = true)
    protected Series series;
    @XmlElement(required = true)
    protected List<Episode> episode;
    @XmlAttribute(name = "id")
    protected Long id;
    @XmlAttribute(name = "nr")
    protected Integer nr;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "showNr")
    protected Boolean showNr;
    @XmlAttribute(name = "showName")
    protected Boolean showName;

    /**
     * Gets the value of the cover property.
     * 
     * @return
     *     possible object is
     *     {@link Cover }
     *     
     */
    public Cover getCover() {
        return cover;
    }

    /**
     * Sets the value of the cover property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cover }
     *     
     */
    public void setCover(Cover value) {
        this.cover = value;
    }

    public boolean isSetCover() {
        return (this.cover!= null);
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
     *     {@link Integer }
     *     
     */
    public int getNr() {
        return nr;
    }

    /**
     * Sets the value of the nr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNr(int value) {
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
